SUMMARY = "AMD Processor Microcode Datafile for Linux"
HOMEPAGE = "http://www.amd.com/"

LICENSE = "AMD-Microcode-License"
LIC_FILES_CHKSUM = "file://LICENSE.amd-ucode;md5=3c5399dc9148d7f0e1f41e34b69cf14f"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git;protocol=https"
SRCREV = "d526e044bddaa2c2ad855c7296147e49be0ab03c"

S = "${WORKDIR}/git"

COMPATIBLE_HOST = "(i.86|x86_64).*-linux"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy
do_deploy() {
	mkdir -p ${WORKDIR}/kernel/x86/microcode
	cat ${S}/amd-ucode/microcode_amd*.bin > ${WORKDIR}/kernel/x86/microcode/AuthenticAMD.bin
	echo kernel/x86/microcode/AuthenticAMD.bin | cpio -D ${WORKDIR} -H newc -o > ${WORKDIR}/amd-microcode_${PV}.cpio

	install ${WORKDIR}/amd-microcode_${PV}.cpio ${DEPLOYDIR}/
	ln -sf amd-microcode_${PV}.cpio ${DEPLOYDIR}/amd-microcode.cpio
}
addtask deploy after do_compile
