SUMMARY = "Write configuration files needed for containerd and k8s"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://config.toml \
            file://COPYING.MIT \
            file://ip_forward"

FILES_${PN} += " config.toml \
                 /proc/sys/net/ipv4/ip_forward "

do_install(){
    # Configure containerd
    install -d ${D}/etc/containerd/
    install -m 0755 ${WORKDIR}/config.toml ${D}/etc/containerd/config.toml
    # Set ipv4 ip forward to true (k8s dependency)
    install -d ${D}/proc/sys/net/ipv4/
    install -m 0755 ${WORKDIR}/ip_forward ${D}/proc/sys/net/ipv4/ip_forward
}
