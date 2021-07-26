SUMMARY = "ip setup dhcp with systemd"
DESCRIPTION = "A recipe for setting up an ip for a machine using systemd and dhcp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://10-dhcp-systemd-network.network \
            file://COPYING.MIT"

FILES_${PN} += " 10-dhcp-systemd-network.network \"

do_install(){
    install -d ${D}/etc/systemd/network/
    install -m 0755 ${WORKDIR}/10-dhcp-systemd-network.network ${D}/etc/systemd/network/10-dhcp-systemd-network.network
}
