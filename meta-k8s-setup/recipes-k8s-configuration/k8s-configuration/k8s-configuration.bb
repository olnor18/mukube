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
            file://resolv.conf \
            file://k8s-configuration.service"

FILES_${PN} += " /proc/sys/net/ipv4/ip_forward \
                 crictl.yaml \
                 InitConfiguration.yaml \
                 images.tar \
                 /config \"

KUBERNETES_VERSION = "v1.20.7"

CONTAINER_IMAGES = "k8s.gcr.io/kube-apiserver:${KUBERNETES_VERSION} \
                    k8s.gcr.io/kube-controller-manager:${KUBERNETES_VERSION} \
                    k8s.gcr.io/kube-scheduler:${KUBERNETES_VERSION} \
                    k8s.gcr.io/kube-proxy:${KUBERNETES_VERSION} \
                    k8s.gcr.io/pause:3.4.1 \
                    k8s.gcr.io/etcd:3.4.13-0 \
                    k8s.gcr.io/coredns:1.7.0 \
                    k8s.gcr.io/pause:3.2"

inherit systemd

SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_SERVICE:${PN} = "k8s-configuration.service"

do_install(){
    install -d ${D}/etc/
    install -d ${D}/config/

    # Set nameservers
    install -m 0644 ${WORKDIR}/resolv.conf  ${D}/etc/resolv.conf

    # Set crictl config
    install -m 0644 ${WORKDIR}/crictl.yaml ${D}/etc/crictl.yaml

    # Setup kubeadm kubelet conf and kublet service
    install -d  ${D}/etc/systemd/system/kubelet.service.d/
    install -m 0644 ${WORKDIR}/10-kubeadm.conf ${D}/etc/systemd/system/kubelet.service.d/10-kubeadm.conf
    install -m 0644 ${WORKDIR}/kubelet.service ${D}/etc/systemd/system/kubelet.service

    # Autounpack config and enable service
    install -m 0644 ${WORKDIR}/k8s-configuration.service ${D}/etc/systemd/system/k8s-configuration.service

    # Set kubeconfig 
    install -d ${D}/var/lib/kubelet/

    # Enable ipv4 port forward
    install -d ${D}/etc/sysctl.d/
    install -m 0644 ${WORKDIR}/local.conf ${D}/etc/sysctl.d/local.conf

    # Install and enable cri-o container runtime
    install -d ${D}/etc/crio/crio.conf.d/
    install -m 0644 ${WORKDIR}/crio.conf ${D}/etc/crio/crio.conf.d/02-cgroup-manager.conf
    install -m 0644 ${WORKDIR}/crio.service ${D}/etc/systemd/system/crio.service

    # Install container images for control plane
    for image in ${CONTAINER_IMAGES}; do
        docker pull $image
    done

    docker save -o ${WORKDIR}/images.tar ${CONTAINER_IMAGES} 
    install -m 0644 ${WORKDIR}/images.tar ${D}/images.tar
}
