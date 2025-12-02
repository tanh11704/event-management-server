# Hướng Dẫn Deploy Ứng Dụng BoPhieu Lên Google Cloud Kubernetes Engine (GKE)

Tài liệu này hướng dẫn chi tiết cách deploy ứng dụng Spring Boot BoPhieu lên Google Cloud Kubernetes Engine với CI/CD và Auto-scaling.

## Mục Lục

1. [Yêu Cầu Tiên Quyết](#yêu-cầu-tiên-quyết)
2. [Thiết Lập GKE Cluster](#thiết-lập-gke-cluster)
3. [Cấu Hình Database](#cấu-hình-database)
4. [Cấu Hình Container Registry](#cấu-hình-container-registry)
5. [Deploy Ứng Dụng](#deploy-ứng-dụng)
6. [Cấu Hình CI/CD](#cấu-hình-cicd)
7. [Cấu Hình Auto-Scaling](#cấu-hình-auto-scaling)
8. [Monitoring và Logging](#monitoring-và-logging)
9. [Troubleshooting](#troubleshooting)

---

## Yêu Cầu Tiên Quyết

### 1. Cài Đặt Công Cụ Cần Thiết

```bash
# Cài đặt Google Cloud SDK
curl https://sdk.cloud.google.com | bash
exec -l $SHELL
gcloud init

# Cài đặt kubectl
gcloud components install kubectl

# Cài đặt Docker (nếu chưa có)
# macOS
brew install docker

# Hoặc tải từ https://www.docker.com/products/docker-desktop
```

### 2. Xác Thực Google Cloud

```bash
# Đăng nhập vào Google Cloud
gcloud auth login

# Thiết lập project
gcloud config set project YOUR_PROJECT_ID

# Kích hoạt các API cần thiết
gcloud services enable container.googleapis.com
gcloud services enable containerregistry.googleapis.com
gcloud services enable cloudbuild.googleapis.com
gcloud services enable sqladmin.googleapis.com
```

### 3. Cấu Hình Quyền Truy Cập

```bash
# Cấp quyền cho Cloud Build
gcloud projects add-iam-policy-binding event-management-478313 \
    --member=serviceAccount:1091683012310@cloudbuild.gserviceaccount.com \
    --role=roles/container.developer
```

---

## Thiết Lập GKE Cluster

### 1. Tạo GKE Cluster

```bash
# Tạo cluster với auto-scaling và node pool
gcloud container clusters create event-management-cluster \
    --zone=asia-southeast1-a \
    --machine-type=e2-medium \
    --num-nodes=2 \
    --min-nodes=2 \
    --max-nodes=5 \
    --enable-autoscaling \
    --enable-autorepair \
    --enable-autoupgrade \
    --enable-ip-alias \
    --enable-network-policy \
    --addons=HorizontalPodAutoscaling,HttpLoadBalancing \
    --release-channel=regular

# Kết nối với cluster
gcloud container clusters get-credentials event-management-cluster --zone=asia-southeast1-a

# Cài đặt Plugin xác thực
gcloud components install gke-gcloud-auth-plugin
```

### 2. Xác Minh Cluster

```bash
# Kiểm tra nodes
kubectl get nodes

# Kiểm tra cluster info
kubectl cluster-info
```

---

## Cấu Hình Database

### Tùy Chọn 1: Cloud SQL (Khuyến Nghị)

```bash
# Tạo Cloud SQL instance
gcloud sql instances create event-management-mysql \
    --database-version=MYSQL_8_0 \
    --tier=db-f1-micro \
    --region=asia-southeast1 \
    --root-password=CloudCompute@12345

# Tạo database
gcloud sql databases create event_management --instance=event-management-mysql

# Tạo user
gcloud sql users create dbuser \
    --instance=event-management-mysql \
    --password=YOUR_DB_PASSWORD
```

## Cấu Hình Container Registry

### 1. Cấu Hình Docker để Push Image

```bash
# Cấu hình Docker authentication
gcloud auth configure-docker
```

### 2. Build và Push Image Thủ Công (Test)

```bash
# Build image
docker build --platform linux/amd64 -t gcr.io/event-management-478313/bophieu-api:latest .

# Push image
docker push gcr.io/event-management-478313/bophieu-api:latest
```

---

## Deploy Ứng Dụng

### 1. Tạo Namespace

```bash
kubectl create namespace eventmanagement
kubectl config set-context --current --namespace=eventmanagement
```

### 2. Tạo Secrets

**Lưu ý:** Thay thế các giá trị placeholder bằng giá trị thực tế của bạn.

```bash
# Tạo secret cho database
kubectl create secret generic db-secret \
    --from-literal=username=dbuser \
    --from-literal=password=Abcd1234@ \
    --from-literal=url=jdbc:mysql://34.158.50.243:3306/event_management \
    -n eventmanagement

# Tạo secret cho JWT
kubectl create secret generic jwt-secret \
    --from-literal=secret=daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb \
    -n eventmanagement

# Tạo secret cho email
kubectl create secret generic mail-secret \
    --from-literal=username=tuanpham.5417@gmail.com \
    --from-literal=password="fdbx epzc lutv mkcz" \
    -n eventmanagement

# Tạo secret cho Cloudinary
kubectl create secret generic cloudinary-secret \
    --from-literal=cloud-name=dobtgdvzk \
    --from-literal=api-key=464153871671396 \
    --from-literal=api-secret=KRJlRPBureLOOq2L2fXCeKfwJEY \
    -n eventmanagement

# Tạo secret cho Gemini API
kubectl create secret generic gemini-secret \
    --from-literal=key=AIzaSyDSR-BEcj9XzUNxclgAq8gNfJg0WcwufzQ \
    -n eventmanagement
```

### 3. Tạo ConfigMap

```bash
# Áp dụng ConfigMap
kubectl apply -f k8s/configmap.yaml -n eventmanagement
```

### 4. Deploy Ứng Dụng

```bash
# Deploy application
kubectl apply -f k8s/deployment.yaml -n eventmanagement

# Deploy service
kubectl apply -f k8s/service.yaml -n eventmanagement

# Deploy HPA (Horizontal Pod Autoscaler)
kubectl apply -f k8s/hpa.yaml -n eventmanagement
```

### 5. Kiểm Tra Deployment

```bash
# Kiểm tra pods
kubectl get pods -n eventmanagement

# Kiểm tra services
kubectl get svc -n eventmanagement

# Kiểm tra HPA
kubectl get hpa -n eventmanagement

# Xem logs
kubectl logs -f deployment/eventmanagement-api -n eventmanagement
```

### 6. Expose Ứng Dụng (Tùy Chọn)

```bash
# Sử dụng Ingress (khuyến nghị)
kubectl apply -f k8s/ingress.yaml -n eventmanagement
```

---

## Cấu Hình CI/CD

### Tùy Chọn 1: Google Cloud Build (Khuyến Nghị)

#### 1. Tạo Cloud Build Trigger

```bash
# Tạo trigger từ GitHub
gcloud builds triggers create github \
    --name="eventmanagement-deploy" \
    --repo-name="YOUR_REPO_NAME" \
    --repo-owner="YOUR_GITHUB_USERNAME" \
    --branch-pattern="^main$" \
    --build-config="cloudbuild.yaml"
```

#### 2. Cấu Hình cloudbuild.yaml

File `cloudbuild.yaml` đã được tạo sẵn trong project. Nó sẽ:

- Build Docker image
- Push image lên Container Registry
- Deploy lên GKE cluster

#### 3. Manual Trigger

```bash
# Trigger build thủ công
gcloud builds submit --config cloudbuild.yaml
```

## Cấu Hình Auto-Scaling

### 1. Horizontal Pod Autoscaler (HPA)

HPA đã được cấu hình trong file `k8s/hpa.yaml` với các thông số:

- Min replicas: 2
- Max replicas: 10
- Target CPU: 70%
- Target Memory: 80%

### 2. Cluster Autoscaler

Cluster Autoscaler đã được kích hoạt khi tạo cluster với `--enable-autoscaling`.

### 3. Kiểm Tra Auto-Scaling

```bash
# Xem HPA status
kubectl get hpa -n bophieu -w

# Xem pods
kubectl get pods -n bophieu

# Tạo load test để kiểm tra scaling
kubectl run -i --tty load-generator --rm \
    --image=busybox --restart=Never -- \
    /bin/sh -c "while true; do wget -q -O- http://bophieu-api:8080/actuator/health; done"
```

---

## Monitoring và Logging

### 1. Cloud Monitoring

```bash
# Kích hoạt Cloud Monitoring
gcloud services enable monitoring.googleapis.com
```

### 2. Xem Logs

```bash
# Logs từ pods
kubectl logs -f deployment/bophieu-api -n bophieu

# Logs từ Cloud Logging
gcloud logging read "resource.type=k8s_container" --limit 50
```

### 3. Metrics

```bash
# Xem metrics từ pods
kubectl top pods -n bophieu

# Xem metrics từ nodes
kubectl top nodes
```

---

## Troubleshooting

### 1. Pod Không Khởi Động

```bash
# Kiểm tra pod status
kubectl describe pod POD_NAME -n bophieu

# Xem logs
kubectl logs POD_NAME -n bophieu

# Kiểm tra events
kubectl get events -n bophieu --sort-by='.lastTimestamp'
```

### 2. Lỗi Kết Nối Database

```bash
# Kiểm tra secret
kubectl get secret db-secret -n bophieu -o yaml

# Test kết nối từ pod
kubectl exec -it POD_NAME -n bophieu -- sh
# Trong pod, test kết nối database
```

### 3. Image Pull Error

```bash
# Kiểm tra image
gcloud container images list

# Kiểm tra quyền
gcloud projects get-iam-policy YOUR_PROJECT_ID
```

### 4. HPA Không Hoạt Động

```bash
# Kiểm tra metrics server
kubectl get deployment metrics-server -n kube-system

# Kiểm tra HPA events
kubectl describe hpa bophieu-api-hpa -n bophieu
```

### 5. Rollback Deployment

```bash
# Xem deployment history
kubectl rollout history deployment/bophieu-api -n bophieu

# Rollback về version trước
kubectl rollout undo deployment/bophieu-api -n bophieu

# Rollback về version cụ thể
kubectl rollout undo deployment/bophieu-api --to-revision=2 -n bophieu
```

---

## Best Practices

### 1. Security

- ✅ Sử dụng Secrets cho thông tin nhạy cảm
- ✅ Enable network policies
- ✅ Sử dụng least privilege cho service accounts
- ✅ Enable Pod Security Standards
- ✅ Scan container images cho vulnerabilities

### 2. Performance

- ✅ Sử dụng resource requests và limits
- ✅ Cấu hình readiness và liveness probes
- ✅ Sử dụng PersistentVolume cho data cần lưu trữ
- ✅ Tối ưu Docker image size

### 3. Reliability

- ✅ Sử dụng multiple replicas
- ✅ Cấu hình health checks
- ✅ Sử dụng rolling updates
- ✅ Backup database định kỳ

### 4. Cost Optimization

- ✅ Sử dụng preemptible nodes cho dev/test
- ✅ Right-size resource requests
- ✅ Sử dụng cluster autoscaler
- ✅ Monitor và optimize costs

---

## Tài Liệu Tham Khảo

- [GKE Documentation](https://cloud.google.com/kubernetes-engine/docs)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Cloud Build Documentation](https://cloud.google.com/build/docs)
- [Spring Boot on Kubernetes](https://spring.io/guides/gs/spring-boot-kubernetes/)

---

## Hỗ Trợ

Nếu gặp vấn đề, vui lòng:

1. Kiểm tra logs và events
2. Xem tài liệu troubleshooting ở trên
3. Tham khảo tài liệu chính thức của Google Cloud

---

**Lưu ý:** Đảm bảo thay thế tất cả các placeholder (YOUR_PROJECT_ID, YOUR_DB_PASSWORD, etc.) bằng giá trị thực tế của bạn trước khi deploy.
