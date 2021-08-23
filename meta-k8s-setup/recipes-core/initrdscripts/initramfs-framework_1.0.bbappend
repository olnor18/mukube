FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://ramrootfs"

do_install:append() {
    install ${WORKDIR}/ramrootfs ${D}/init.d/91-ramrootfs
}

PACKAGES += "initramfs-module-ramrootfs"

SUMMARY_initramfs-module-ramrootfs = "initramfs dm-verity rootfs support"
RDEPENDS_initramfs-module-ramrootfs = "${PN}-base"
FILES_initramfs-module-ramrootfs = "/init.d/91-ramrootfs"
