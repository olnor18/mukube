FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-gpt-auto-generator-Use-volatile-root-by-default-and-.patch"

PACKAGECONFIG += "efi"
do_install:append() {
	install -d ${D}/efi
}
FILES:${PN} += "/efi"
