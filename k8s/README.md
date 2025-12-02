# Kubernetes Manifests

Thư mục này chứa các file cấu hình Kubernetes để deploy ứng dụng BoPhieu lên GKE.

## Cấu Trúc Files

- `deployment.yaml`: Deployment configuration cho ứng dụng Spring Boot
- `service.yaml`: Service để expose ứng dụng trong cluster
- `hpa.yaml`: Horizontal Pod Autoscaler cho auto-scaling
- `configmap.yaml`: ConfigMap cho các cấu hình không nhạy cảm
- `ingress.yaml`: Ingress để expose ứng dụng ra ngoài với SSL
- `mysql-deployment.yaml`: Tùy chọn MySQL trong Kubernetes (không khuyến nghị cho production)

## Cách Sử Dụng

### 1. Tạo Namespace

```bash
kubectl create namespace bophieu
```

### 2. Tạo Secrets

Trước khi deploy, cần tạo các secrets:

```bash
# Database secret
kubectl create secret generic db-secret \
    --from-literal=username=dbuser \
    --from-literal=password=YOUR_DB_PASSWORD \
    --from-literal=url=jdbc:mysql://YOUR_DB_HOST:3306/event_management \
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

### 3. Cập Nhật ConfigMap

Chỉnh sửa `configmap.yaml` với các giá trị phù hợp:

- `cors.allowed.origins`: Domain frontend của bạn
- `app.frontend.url`: URL frontend

### 4. Cập Nhật Image trong deployment.yaml

Thay thế `PROJECT_ID` trong `deployment.yaml` bằng Project ID thực tế của bạn:

```yaml
image: gcr.io/YOUR_PROJECT_ID/bophieu-api:latest
```

### 5. Deploy

```bash
# Deploy ConfigMap
kubectl apply -f configmap.yaml -n bophieu

# Deploy Application
kubectl apply -f deployment.yaml -n bophieu

# Deploy Service
kubectl apply -f service.yaml -n bophieu

# Deploy HPA
kubectl apply -f hpa.yaml -n bophieu

# Deploy Ingress (tùy chọn)
kubectl apply -f ingress.yaml -n bophieu
```

### 6. Kiểm Tra

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

## Lưu Ý

1. **Image Pull Policy**: Đã set `imagePullPolicy: Always` để đảm bảo luôn pull image mới nhất
2. **Resource Limits**: Điều chỉnh `resources.requests` và `resources.limits` dựa trên nhu cầu thực tế
3. **Health Checks**: Ứng dụng cần expose `/actuator/health` endpoints
4. **Storage**: Hiện tại sử dụng `emptyDir` cho uploads. Nên sử dụng PersistentVolume cho production
5. **Secrets**: Không commit secrets vào git. Sử dụng Secret Management hoặc external secret operators

## Tùy Chỉnh

### Thay Đổi Số Replicas

Chỉnh sửa `replicas` trong `deployment.yaml` hoặc sử dụng HPA để tự động scale.

### Thay Đổi Resource Limits

Chỉnh sửa `resources` trong `deployment.yaml`:

```yaml
resources:
  requests:
    cpu: "500m"
    memory: "512Mi"
  limits:
    cpu: "2000m"
    memory: "2Gi"
```

### Thay Đổi HPA Thresholds

Chỉnh sửa `hpa.yaml`:

```yaml
metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70 # Thay đổi giá trị này
```
