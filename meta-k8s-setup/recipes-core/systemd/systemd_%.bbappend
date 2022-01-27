FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-gpt-auto-generator-Use-volatile-root-by-default-and-.patch \
            file://0001-repart-don-t-prefix-sysroot-twice.patch \
            file://0001-repart-Support-volatile-root-for-finding-the-root-pa.patch"

PACKAGECONFIG = "efi openssl cryptsetup repart networkd resolved tpm2 cgroupv2"
do_install:append() {
	install -d ${D}/efi

	sed -i -e "s%^L! /etc/resolv.conf.*$%L! /etc/resolv.conf - - - - ../run/systemd/resolve/stub/resolv.conf%g" ${D}${exec_prefix}/lib/tmpfiles.d/etc.conf
	ln -fs ../run/systemd/resolve/stub-resolv.conf ${D}${sysconfdir}/resolv-conf.systemd
}
FILES:${PN} += "/efi"
RDEPENDS:${PN} += "packagegroup-base-vfat"

RRECOMMENDS:${PN}:remove = "systemd-conf"
