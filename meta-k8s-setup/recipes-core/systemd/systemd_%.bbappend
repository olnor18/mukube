FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-gpt-auto-generator-Use-volatile-root-by-default-and-.patch \
            file://0001-repart-don-t-prefix-sysroot-twice.patch \
            file://0001-repart-Support-volatile-root-for-finding-the-root-pa.patch"

PACKAGECONFIG = "efi openssl cryptsetup repart networkd resolved"
do_install:append() {
	install -d ${D}/efi
}
FILES:${PN} += "/efi"
RDEPENDS:${PN} += "packagegroup-base-vfat"
