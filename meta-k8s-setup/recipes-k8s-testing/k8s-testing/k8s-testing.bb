
SUMMARY = "Writing files needed for testing kubernetes"
DESCRIPTION = "Generates a config partition for tests"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://InitConfiguration.yaml"

DEPENDS = "e2fsprogs-native"

inherit deploy

do_compile(){
	mkdir -p config-partition
	tar -cvf config-partition/config.tar.gz ../InitConfiguration.yaml 
	mkfs.ext4 -d config-partition test_config.ext4 1G
}

do_deploy(){
	mv test_config.ext4 ${DEPLOYDIR}
}

addtask do_deploy after do_compile
