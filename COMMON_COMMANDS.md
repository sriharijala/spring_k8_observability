
* minikube
  - Start minikube : ```minikube start --driver=hyperv```
  - Add ingress controller add on ```minikube addons enable ingress```
  - Add ingress controller DNS add on ```minikube addons enable ingress-dns```
  - Metric add on ```minikube addons enable metrics-server```
  - Wait to get the Ingress IP address
    ```
    kubectl get ingress --watch
    NAME           CLASS   HOSTS                 ADDRESS        PORTS   AGE
    myapp-my-app   nginx   minikube.mshome.net   192.168.49.2   80      74s
    ```


* Kubernetes
  - Cluster info : ```kubectl cluster-info```
  - Get pod logs ```kubectl logs config-server-7cf5496d4-nvf7d -n reviews-app```  
  - Kube version: ```kubectl version --client```

* Kustomize installation
  - # pull the image
    ```docker pull registry.k8s.io/kustomize/kustomize:v5.0.0``

# run 'kustomize version'
    ```docker run registry.k8s.io/kustomize/kustomize:v5.0.0 version```   


* Helm commands
   -  ```Helm history app-name```
   -  ```helm dependency list```

* Docker Commends
   - Docker version    : ```docker version```
   - Test docker installation: ```docker run --rm -it hello-world```
   - Remove the docker image: ```docker rmi hello-world```
   - Docker image list : ```docker image ls```
   - Pull an image     : ```docker pull alpine:3.10``` 
   - Run docker with memory limit: ```docker run -d --memory 4m --name test2 nicolaka/netshoot:latest sleep 50000```
   - Memory limits can be verified in folder: ```cat /sys/fs/cgroup/<dockerid>/memory.limit_in_bytes```
   - Local Docker registry listing: ```docker container ls```


