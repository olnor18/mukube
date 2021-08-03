SRC_URI += "file://defconfig"

KERNEL_DEFCONFIG_genericx86-64 = "${WORKDIR}/defconfig"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
