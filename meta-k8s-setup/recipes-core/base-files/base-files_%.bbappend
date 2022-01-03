FILESEXTRAPATHS:append := "${THISDIR}/${PN}:"

SRC_URI += "file://50-volatile.conf \
            file://50-volatile-tpm.conf \
            file://crypttab \
            file://crypttab-tpm \
            file://systemd-repart.service.d \
            file://state-tpm-pcr-enrollment.service"

inherit systemd
SYSTEMD_SERVICE:${PN} += "${@bb.utils.contains('MACHINE_FEATURES', 'tpm2', 'state-tpm-pcr-enrollment.service', '', d)}"
do_install:append() {
	install -d ${D}${datadir}/state
	install -d ${D}${libdir}/repart.d

	ln -s /var/state/secureboot ${D}${datadir}/secureboot

	install -d ${D}${systemd_system_unitdir}/systemd-repart.service.d/
	install -m 0644 ${WORKDIR}/systemd-repart.service.d ${D}${systemd_system_unitdir}/systemd-repart.service.d/override.conf

	install -d ${D}${sysconfdir}/
	if ${@bb.utils.contains('MACHINE_FEATURES', 'tpm2', 'true', 'false', d)}; then
		install -m 0644 ${WORKDIR}/50-volatile-tpm.conf ${D}${libdir}/repart.d/50-volatile.conf
		install -m 0644 ${WORKDIR}/crypttab-tpm ${D}${sysconfdir}/crypttab
		install -m 0644 ${WORKDIR}/state-tpm-pcr-enrollment.service ${D}${systemd_system_unitdir}/
	else
		install -m 0644 ${WORKDIR}/50-volatile.conf ${D}${libdir}/repart.d/
		install -m 0644 ${WORKDIR}/crypttab ${D}${sysconfdir}/
	fi
}
