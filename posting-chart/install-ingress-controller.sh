helm install ingress-ctrl oci://ghcr.io/nginxinc/charts/nginx-ingress \
  --namespace k8s-program \
  --set controller.replicaCount=2
