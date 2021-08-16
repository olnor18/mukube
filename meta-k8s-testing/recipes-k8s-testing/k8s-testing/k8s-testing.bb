SUMMARY = "Write files needed for testing the mukube kubernetes setup"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://COPYING.MIT \
            file://sudoers"

FILES_${PN} += "sudoers"

do_install(){
    install -d ${D}/etc/
    install -m 0755 ${WORKDIR}/sudoers ${D}/etc/sudoers
}
