
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


* Helm commands
   -  ```Helm history app-name```
   -  ```helm dependency list```

* Add Ingress Controller in minikube: ```minikube addons enable ingress```



