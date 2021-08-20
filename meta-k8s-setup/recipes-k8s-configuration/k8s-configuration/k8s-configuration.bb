SUMMARY = "Write configuration files needed for containerd and k8s"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://COPYING.MIT \
            file://local.conf \
            file://crictl.yaml \
            file://10-kubeadm.conf \
            file://InitConfiguration.yaml \
            file://init \
            file://kubelet.service \
            file://crio.conf \
            file://crio.service \
            file://fstab"

FILES_${PN} += " /proc/sys/net/ipv4/ip_forward \
                 crictl.yaml \
                 InitConfiguration.yaml \
                 init \
                 ${CONTAINER_IMAGE_FOLDER} \
                 ${CONTAINER_IMAGE_FOLDER}api.tar \
                 ${CONTAINER_IMAGE_FOLDER}ctr.tar \
                 fstab"


CONTAINER_IMAGE_FOLDER = "/var/ctr-images/"

do_install(){
    install -d ${D}/etc/
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

    # Switch to tempfs
    install -m 0755 ${WORKDIR}/init ${D}/init

    # Install and enable cri-o container runtime
    install -d ${D}/etc/crio/crio.conf.d/
    install -m 0755 ${WORKDIR}/crio.conf ${D}/etc/crio/crio.conf.d/02-cgroup-manager.conf
    install -m 0755 ${WORKDIR}/crio.service ${D}/etc/systemd/system/crio.service

    # Install container images for control plane
    install -d ${D}${CONTAINER_IMAGE_FOLDER}

    docker pull k8s.gcr.io/kube-apiserver:v1.20.7 
    docker save -o ${WORKDIR}/api.tar k8s.gcr.io/kube-apiserver:v1.20.7 
    install -m 0755 ${WORKDIR}/api.tar ${D}${CONTAINER_IMAGE_FOLDER}api.tar


    docker pull k8s.gcr.io/kube-controller-manager:v1.20.7
    docker save -o ${WORKDIR}/ctr.tar k8s.gcr.io/kube-controller-manager:v1.20.7
    install -m 0755 ${WORKDIR}/ctr.tar ${D}${CONTAINER_IMAGE_FOLDER}ctr.tar
}
