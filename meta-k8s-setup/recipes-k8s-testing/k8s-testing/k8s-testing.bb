
SUMMARY = "Writing files needed for testing kubernetes"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://InitConfiguration.yaml"

DEPENDS = "e2fsprogs-native"

do_install(){
    rm -f ${T}/config.ext4
    mkdir ${T}/config-partition/ -p
    cd ${WORKDIR} && tar -cvf ${T}/config-partition/config.tar.gz InitConfiguration.yaml 
    cd ${T} && mkfs.ext4 -d config-partition config.ext4 1G
}
