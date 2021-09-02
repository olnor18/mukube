SUMMARY = "Write configuration files needed for Kubernetes and CRI-O"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://COPYING.MIT \
            file://local.conf \
            file://crictl.yaml \
            file://10-kubeadm.conf \
            file://InitConfiguration.yaml \
            file://kubelet.service \
            file://crio.conf \
            file://crio.service \
            file://fstab"

FILES_${PN} += " /proc/sys/net/ipv4/ip_forward \
                 crictl.yaml \
                 InitConfiguration.yaml \
                 images.tar \
                 /config \
                 /etc/fstab"

KUBERNETES_VERSION = "v1.20.7"

CONTAINER_IMAGES = "k8s.gcr.io/kube-apiserver:${KUBERNETES_VERSION} \
                    k8s.gcr.io/kube-controller-manager:${KUBERNETES_VERSION} \
                    k8s.gcr.io/kube-scheduler:${KUBERNETES_VERSION} \
                    k8s.gcr.io/kube-proxy:${KUBERNETES_VERSION} \
                    k8s.gcr.io/pause:3.4.1 \
                    k8s.gcr.io/etcd:3.4.13-0 \
                    k8s.gcr.io/coredns/coredns:v1.8.0"

do_install(){
    install -d ${D}/etc/
    install -d ${D}/config/

    install -m 0755 ${WORKDIR}/fstab ${D}/etc/fstab

    # Set crictl config
    install -m 0755 ${WORKDIR}/crictl.yaml ${D}/etc/crictl.yaml

    # Setup kubeadm kubelet conf and kublet service
    install -d  ${D}/etc/systemd/system/kubelet.service.d/
    install -m 0755 ${WORKDIR}/10-kubeadm.conf ${D}/etc/systemd/system/kubelet.service.d/10-kubeadm.conf
    install -m 0755 ${WORKDIR}/kubelet.service ${D}/etc/systemd/system/kubelet.service

    # Set kubeconfig 
    install -d ${D}/var/lib/kubelet/
    install -m 0755 ${WORKDIR}/InitConfiguration.yaml ${D}/InitConfiguration.yaml

    # Enable ipv4 port forward
    install -d ${D}/etc/sysctl.d/
    install -m 0755 ${WORKDIR}/local.conf ${D}/etc/sysctl.d/local.conf

    # Install and enable cri-o container runtime
    install -d ${D}/etc/crio/crio.conf.d/
    install -m 0755 ${WORKDIR}/crio.conf ${D}/etc/crio/crio.conf.d/02-cgroup-manager.conf
    install -m 0755 ${WORKDIR}/crio.service ${D}/etc/systemd/system/crio.service

    # Install container images for control plane
    for image in ${CONTAINER_IMAGES}; do
        docker pull $image
    done

    docker save -o ${WORKDIR}/images.tar ${CONTAINER_IMAGES} 
    install -m 0755 ${WORKDIR}/images.tar ${D}/images.tar
}
