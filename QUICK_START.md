# Quick Start Guide - Deploy BoPhieu lên GKE

Hướng dẫn nhanh để deploy ứng dụng BoPhieu lên Google Cloud Kubernetes Engine.

## Bước 1: Chuẩn Bị

```bash
# 1. Cài đặt Google Cloud SDK và kubectl
gcloud init
gcloud components install kubectl

# 2. Thiết lập project
export GCP_PROJECT_ID="your-project-id"
gcloud config set project $GCP_PROJECT_ID

# 3. Kích hoạt các API cần thiết
gcloud services enable container.googleapis.com
gcloud services enable containerregistry.googleapis.com
gcloud services enable cloudbuild.googleapis.com
```

## Bước 2: Tạo GKE Cluster

```bash
gcloud container clusters create bophieu-cluster \
    --zone=asia-southeast1-a \
    --machine-type=e2-medium \
    --num-nodes=2 \
    --min-nodes=2 \
    --max-nodes=5 \
    --enable-autoscaling \
    --enable-autorepair \
    --enable-autoupgrade \
    --addons=HorizontalPodAutoscaling,HttpLoadBalancing

gcloud container clusters get-credentials bophieu-cluster --zone=asia-southeast1-a
```

## Bước 3: Tạo Cloud SQL Database (Khuyến Nghị)

```bash
# Tạo Cloud SQL instance
gcloud sql instances create bophieu-mysql \
    --database-version=MYSQL_8_0 \
    --tier=db-f1-micro \
    --region=asia-southeast1 \
    --root-password=YOUR_ROOT_PASSWORD

# Tạo database
gcloud sql databases create event_management --instance=bophieu-mysql

# Tạo user
gcloud sql users create dbuser \
    --instance=bophieu-mysql \
    --password=YOUR_DB_PASSWORD
```

## Bước 4: Tạo Secrets

```bash
# Tạo namespace
kubectl create namespace bophieu

# Database secret
kubectl create secret generic db-secret \
    --from-literal=username=dbuser \
    --from-literal=password=YOUR_DB_PASSWORD \
    --from-literal=url="jdbc:mysql://YOUR_DB_IP:3306/event_management" \
    -n bophieu

# JWT secret
kubectl create secret generic jwt-secret \
    --from-literal=secret=YOUR_JWT_SECRET_KEY \
    -n bophieu

# Mail secret
kubectl create secret generic mail-secret \
    --from-literal=username=YOUR_EMAIL \
    --from-literal=password=YOUR_APP_PASSWORD \
    -n bophieu

# Cloudinary secret
kubectl create secret generic cloudinary-secret \
    --from-literal=cloud-name=YOUR_CLOUD_NAME \
    --from-literal=api-key=YOUR_API_KEY \
    --from-literal=api-secret=YOUR_API_SECRET \
    -n bophieu

# Gemini secret
kubectl create secret generic gemini-secret \
    --from-literal=key=YOUR_GEMINI_API_KEY \
    -n bophieu
```

## Bước 5: Cập Nhật Cấu Hình

### 5.1. Cập nhật deployment.yaml

Thay thế `PROJECT_ID` trong file `k8s/deployment.yaml`:

```yaml
image: gcr.io/YOUR_PROJECT_ID/bophieu-api:latest
```

### 5.2. Cập nhật configmap.yaml

Chỉnh sửa `k8s/configmap.yaml` với domain frontend của bạn:

```yaml
data:
  cors.allowed.origins: "https://your-frontend-domain.com"
  app.frontend.url: "https://your-frontend-domain.com"
```

## Bước 6: Build và Push Docker Image

```bash
# Cấu hình Docker
gcloud auth configure-docker

# Build image
docker build -t gcr.io/$GCP_PROJECT_ID/bophieu-api:latest .

# Push image
docker push gcr.io/$GCP_PROJECT_ID/bophieu-api:latest
```

## Bước 7: Deploy

### Cách 1: Sử dụng Script (Khuyến Nghị)

```bash
cd k8s
export GCP_PROJECT_ID="your-project-id"
./deploy.sh bophieu
```

### Cách 2: Deploy Thủ Công

```bash
cd k8s

# Deploy ConfigMap
kubectl apply -f configmap.yaml -n bophieu

# Deploy Application
kubectl apply -f deployment.yaml -n bophieu

# Deploy Service
kubectl apply -f service.yaml -n bophieu

# Deploy HPA
kubectl apply -f hpa.yaml -n bophieu
```

## Bước 8: Kiểm Tra

```bash
# Kiểm tra pods
kubectl get pods -n bophieu

# Kiểm tra services
kubectl get svc -n bophieu

# Kiểm tra HPA
kubectl get hpa -n bophieu

# Xem logs
kubectl logs -f deployment/bophieu-api -n bophieu
```

## Bước 9: Expose Ứng Dụng

### Tùy Chọn 1: LoadBalancer (Nhanh)

```bash
kubectl expose deployment bophieu-api \
    --type=LoadBalancer \
    --port=80 \
    --target-port=8080 \
    -n bophieu

# Lấy external IP
kubectl get svc bophieu-api -n bophieu
```

### Tùy Chọn 2: Ingress (Khuyến Nghị cho Production)

1. Cập nhật `k8s/ingress.yaml` với domain của bạn
2. Tạo static IP:

```bash
gcloud compute addresses create bophieu-ip --global
```

3. Deploy ingress:

```bash
kubectl apply -f k8s/ingress.yaml -n bophieu
```

## Cấu Hình CI/CD

### Cloud Build

1. Cập nhật `cloudbuild.yaml` với thông tin cluster của bạn
2. Tạo trigger:

```bash
gcloud builds triggers create github \
    --name="bophieu-deploy" \
    --repo-name="YOUR_REPO" \
    --repo-owner="YOUR_USERNAME" \
    --branch-pattern="^main$" \
    --build-config="cloudbuild.yaml"
```

### GitHub Actions

1. Tạo Service Account và key (xem GKE_DEPLOYMENT_GUIDE.md)
2. Thêm secrets vào GitHub:
   - `GCP_PROJECT_ID`
   - `GCP_SA_KEY`
   - `GKE_CLUSTER`
   - `GKE_ZONE`

## Troubleshooting

### Pod không khởi động

```bash
kubectl describe pod POD_NAME -n bophieu
kubectl logs POD_NAME -n bophieu
```

### Lỗi kết nối database

Kiểm tra:

- Secret `db-secret` đã được tạo đúng chưa
- Database IP/URL có đúng không
- Firewall rules cho phép kết nối từ GKE

### Image pull error

```bash
# Kiểm tra image
gcloud container images list

# Kiểm tra quyền
gcloud projects get-iam-policy $GCP_PROJECT_ID
```

## Tài Liệu Chi Tiết

Xem file `GKE_DEPLOYMENT_GUIDE.md` để biết thêm chi tiết.
