SUMMARY = "Write configuration files needed for containerd and k8s"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://config.toml \
            file://COPYING.MIT \
            file://local.conf \
            file://crictl.yaml \
            file://10-kubeadm.conf \
            file://InitConfiguration.yaml"

FILES_${PN} += " config.toml \
                 /proc/sys/net/ipv4/ip_forward \
                 crictl.yaml \
                 InitConfiguration.yaml"

do_install(){
    # Configure containerd
    install -d ${D}/etc/containerd/
    install -m 0755 ${WORKDIR}/config.toml ${D}/etc/containerd/config.toml
    
    # Set crictl config
    install -m 0755 ${WORKDIR}/crictl.yaml ${D}/etc/crictl.yaml

    # Setup kubeadm kubelet conf
    install -d  ${D}/etc/systemd/system/kubelet.service.d/
    install -m 0755 ${WORKDIR}/10-kubeadm.conf ${D}/etc/systemd/system/kubelet.service.d/10-kubeadm.conf

    # Set kubeconfig 
    install -d ${D}/var/lib/kubelet/
    install -m 0755 ${WORKDIR}/InitConfiguration.yaml ${D}/InitConfiguration.yaml

    # Enable ipv4 port forward
    install -d ${D}/etc/sysctl.d/
    install -m 0755 ${WORKDIR}/local.conf ${D}/etc/sysctl.d/local.conf
}
