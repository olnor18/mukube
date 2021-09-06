
SUMMARY = "Writing files needed for testing kubernetes"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI += "file://COPYING.MIT \
            file://InitConfiguration.yaml"

do_install(){
    rm ${T}/config.ext4
    mkdir ${T}/config-partition/ -p
    cd ${WORKDIR} && tar -cvf ${T}/config-partition/config.tar.gz InitConfiguration.yaml 
    cd ${T} && mkfs.ext4 -d config-partition config.ext4 1G
}
