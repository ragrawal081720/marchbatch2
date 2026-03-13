# Kubernetes Application with Persistent Database

## Architecture:
```
┌─────────────────┐
│  LoadBalancer   │ (Port 80 / NodePort 30200)
└────────┬────────┘
         │
┌────────▼─────────┐
│  Web Application │ (WordPress - 2 replicas)
│    Deployment    │
└────────┬─────────┘
         │
┌────────▼─────────┐
│  MySQL Service   │ (ClusterIP)
└────────┬─────────┘
         │
┌────────▼─────────┐
│ MySQL Deployment │ (1 replica)
└────────┬─────────┘
         │
┌────────▼─────────┐
│      PVC         │ (5Gi Persistent Volume)
│  (mysql-pvc)     │
└──────────────────┘
```

## Components:

1. **Namespace**: `webapp` - Isolates all resources
2. **Secret**: Stores database credentials (base64 encoded)
3. **ConfigMap**: Stores database configuration
4. **PersistentVolumeClaim**: 5Gi storage for MySQL data
5. **MySQL Deployment**: Single replica with persistent storage
6. **MySQL Service**: ClusterIP for internal communication
7. **Web App Deployment**: 2 replicas of WordPress
8. **Web App Service**: LoadBalancer for external access

## Deploy:

```bash
# Create all resources
kubectl apply -f docker/kube-persistent-app.yaml

# Watch pods starting
kubectl get pods -n webapp -w

# Check PVC status
kubectl get pvc -n webapp

# Get service details
kubectl get svc -n webapp
```

## Access:

### Docker Desktop:
```bash
# LoadBalancer will work on localhost
http://localhost
# Or via NodePort
http://localhost:30200
```

### Minikube:
```bash
# Get the URL
minikube service webapp-service -n webapp --url
```

## Verify Persistent Storage:

### Test 1: Create some data
```bash
# Access the app and create posts/data
http://localhost:30200
```

### Test 2: Delete deployment
```bash
kubectl delete deployment mysql-deployment -n webapp
```

### Test 3: Recreate deployment
```bash
kubectl apply -f docker/kube-persistent-app.yaml
```

**Result**: Your data will still be there! 🎉

## Check Persistent Volume:

```bash
# List PVCs
kubectl get pvc -n webapp

# Describe PVC
kubectl describe pvc mysql-pvc -n webapp

# List PVs (automatically created)
kubectl get pv
```

## Database Credentials:

- **Root Password**: `mypass123`
- **Database Name**: `webapp_db`
- **Username**: `webapp_user`
- **Password**: `mypass123`

## Connect to MySQL directly:

```bash
# Port forward MySQL service
kubectl port-forward -n webapp service/mysql-service 3306:3306

# Connect with MySQL client
mysql -h 127.0.0.1 -u webapp_user -pmypass123 webapp_db
```

## Clean Up:

```bash
# Delete everything EXCEPT PVC
kubectl delete -f docker/kube-persistent-app.yaml

# Data persists! Check:
kubectl get pvc -n webapp

# To delete data too:
kubectl delete pvc mysql-pvc -n webapp
kubectl delete namespace webapp
```

## For Custom Application:

Replace the WordPress deployment with your own:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webapp-deployment
  namespace: webapp
spec:
  replicas: 2
  selector:
    matchLabels:
      app: webapp
  template:
    metadata:
      labels:
        app: webapp
    spec:
      containers:
      - name: webapp
        image: your-image:tag
        ports:
        - containerPort: 8080
        env:
        - name: DB_HOST
          value: mysql-service:3306
        - name: DB_NAME
          valueFrom:
            configMapKeyRef:
              name: mysql-config
              key: mysql-database
        - name: DB_USER
          valueFrom:
            configMapKeyRef:
              name: mysql-config
              key: mysql-user
        - name: DB_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: mysql-password
```

## Storage Classes:

For different environments, adjust `storageClassName`:
- **Docker Desktop**: `standard` or `hostpath`
- **Minikube**: `standard`
- **AWS EKS**: `gp2` or `gp3`
- **Azure AKS**: `managed-premium`
- **GCP GKE**: `standard` or `ssd`

## Check available storage classes:
```bash
kubectl get storageclass
```
