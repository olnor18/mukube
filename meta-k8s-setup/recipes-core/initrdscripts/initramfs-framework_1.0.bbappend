FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://ramrootfs"

do_install:append() {
    install ${WORKDIR}/ramrootfs ${D}/init.d/91-ramrootfs
}

PACKAGES += "initramfs-module-ramrootfs"

SUMMARY:initramfs-module-ramrootfs = "initramfs dm-verity rootfs support"
RDEPENDS:initramfs-module-ramrootfs = "${PN}-base util-linux-zramctl e2fsprogs-mke2fs"
FILES:initramfs-module-ramrootfs = "/init.d/91-ramrootfs"
