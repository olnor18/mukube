
SUMMARY = "Writing files needed for testing kubernetes"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://COPYING.MIT \
            file://InitConfiguration.yaml"


do_install(){
    mkdir ${T}/config-partition/ -p
    tar -zcvf ${T}/config-partition/config.tar.gz ${WORKDIR}/InitConfiguration.yaml
    mkfs.ext4 -d ${T}/config-partition ${T}/config.ext4 1G
}
