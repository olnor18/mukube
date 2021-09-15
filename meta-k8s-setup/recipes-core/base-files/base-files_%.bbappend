FILESEXTRAPATHS:append := "${THISDIR}/${PN}:"

SRC_URI += "file://50-volatile.conf \
            file://40-config.conf \
            file://crypttab \
            file://systemd-repart.service.d \
            file://state-tpm-pcr-enrollment.service"

inherit systemd
SYSTEMD_SERVICE:${PN} += "state-tpm-pcr-enrollment.service"
do_install:append() {
	install -d ${D}${datadir}/state
	install -d ${D}${libdir}/repart.d
	install -m 0644 ${WORKDIR}/50-volatile.conf ${D}${libdir}/repart.d/
	install -m 0644 ${WORKDIR}/40-config.conf ${D}${libdir}/repart.d/

	install -d ${D}${sysconfdir}/
	install -m 0644 ${WORKDIR}/crypttab ${D}${sysconfdir}/

	ln -s /var/state/secureboot ${D}${datadir}/secureboot

	install -d ${D}${systemd_system_unitdir}/systemd-repart.service.d/
	install -m 0644 ${WORKDIR}/systemd-repart.service.d ${D}${systemd_system_unitdir}/systemd-repart.service.d/override.conf
	install -m 0644 ${WORKDIR}/state-tpm-pcr-enrollment.service ${D}${systemd_system_unitdir}/
}
