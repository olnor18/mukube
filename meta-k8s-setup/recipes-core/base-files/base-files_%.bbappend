FILESEXTRAPATHS:append := "${THISDIR}/${PN}:"

SRC_URI += "file://50-volatile.conf \
            file://40-config.conf \
            file://crypttab"

do_install:append() {
	install -d ${D}${libdir}/repart.d
	install -m 0644 ${WORKDIR}/50-volatile.conf ${D}${libdir}/repart.d/
	install -m 0644 ${WORKDIR}/40-config.conf ${D}${libdir}/repart.d/

	install -d ${D}${sysconfdir}/
	install -m 0644 ${WORKDIR}/crypttab ${D}${sysconfdir}/

	ln -s /var/volatile/secureboot ${D}${datadir}/secureboot
}
