
SUMMARY = "Writing files needed for testing kubernetes"
DESCRIPTION = "Generates a config partition for tests"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://InitConfiguration.yaml \
            file://boot.sh \
            file://20-wired.network"

DEPENDS = "e2fsprogs-native"

FILES:${PN} += "/boot.sh"

RDEPENDS:${PN} += "bash"

inherit deploy

do_compile(){
	mkdir -p config-partition
	tar -cvf config-partition/config.tar --transform 's,\(20-wired.network\),/etc/systemd/network/\1,' ../InitConfiguration.yaml ../20-wired.network
	mkfs.ext4 -L config -d config-partition test_config.ext4 1M
}

do_deploy(){
	mv test_config.ext4 ${DEPLOYDIR}
}

do_install(){
	install -m 0744 ${WORKDIR}/boot.sh ${D}/boot.sh
}

addtask do_deploy after do_compile
