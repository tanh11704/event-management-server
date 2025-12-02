#!/bin/bash

# Script hỗ trợ deploy ứng dụng BoPhieu lên GKE
# Sử dụng: ./deploy.sh [namespace]

set -e

NAMESPACE=${1:-bophieu}
PROJECT_ID=${GCP_PROJECT_ID:-"YOUR_PROJECT_ID"}
CLUSTER_NAME=${GKE_CLUSTER:-"bophieu-cluster"}
ZONE=${GKE_ZONE:-"asia-southeast1-a"}

echo "🚀 Bắt đầu deploy ứng dụng BoPhieu..."
echo "Namespace: $NAMESPACE"
echo "Project ID: $PROJECT_ID"
echo "Cluster: $CLUSTER_NAME"
echo "Zone: $ZONE"
echo ""

# Kiểm tra kubectl
if ! command -v kubectl &> /dev/null; then
    echo "❌ kubectl chưa được cài đặt. Vui lòng cài đặt kubectl."
    exit 1
fi

# Kiểm tra kết nối cluster
echo "📡 Kiểm tra kết nối với cluster..."
if ! kubectl cluster-info &> /dev/null; then
    echo "❌ Không thể kết nối với cluster. Vui lòng chạy:"
    echo "   gcloud container clusters get-credentials $CLUSTER_NAME --zone $ZONE"
    exit 1
fi

# Tạo namespace nếu chưa tồn tại
echo "📦 Tạo namespace nếu chưa tồn tại..."
kubectl create namespace $NAMESPACE --dry-run=client -o yaml | kubectl apply -f -

# Kiểm tra secrets
echo "🔐 Kiểm tra secrets..."
REQUIRED_SECRETS=("db-secret" "jwt-secret" "mail-secret" "cloudinary-secret" "gemini-secret")
MISSING_SECRETS=()

for secret in "${REQUIRED_SECRETS[@]}"; do
    if ! kubectl get secret $secret -n $NAMESPACE &> /dev/null; then
        MISSING_SECRETS+=($secret)
    fi
done

if [ ${#MISSING_SECRETS[@]} -gt 0 ]; then
    echo "⚠️  Các secrets sau chưa được tạo:"
    for secret in "${MISSING_SECRETS[@]}"; do
        echo "   - $secret"
    done
    echo ""
    echo "Vui lòng tạo các secrets trước khi deploy. Xem hướng dẫn trong GKE_DEPLOYMENT_GUIDE.md"
    read -p "Bạn có muốn tiếp tục không? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Cập nhật PROJECT_ID trong deployment.yaml
echo "🔧 Cập nhật PROJECT_ID trong deployment.yaml..."
sed -i.bak "s|gcr.io/PROJECT_ID|gcr.io/$PROJECT_ID|g" deployment.yaml

# Deploy ConfigMap
echo "📝 Deploy ConfigMap..."
kubectl apply -f configmap.yaml -n $NAMESPACE

# Deploy Deployment
echo "🚀 Deploy Application..."
kubectl apply -f deployment.yaml -n $NAMESPACE

# Deploy Service
echo "🌐 Deploy Service..."
kubectl apply -f service.yaml -n $NAMESPACE

# Deploy HPA
echo "📈 Deploy Horizontal Pod Autoscaler..."
kubectl apply -f hpa.yaml -n $NAMESPACE

# Khôi phục deployment.yaml
if [ -f deployment.yaml.bak ]; then
    mv deployment.yaml.bak deployment.yaml
fi

# Chờ deployment sẵn sàng
echo "⏳ Chờ deployment sẵn sàng..."
kubectl rollout status deployment/bophieu-api -n $NAMESPACE --timeout=5m

# Hiển thị thông tin
echo ""
echo "✅ Deploy thành công!"
echo ""
echo "📊 Trạng thái:"
kubectl get pods -n $NAMESPACE
echo ""
kubectl get svc -n $NAMESPACE
echo ""
kubectl get hpa -n $NAMESPACE
echo ""
echo "📋 Để xem logs:"
echo "   kubectl logs -f deployment/bophieu-api -n $NAMESPACE"
echo ""
echo "📋 Để xem chi tiết pods:"
echo "   kubectl describe pod -l app=bophieu-api -n $NAMESPACE"

