# make the CNI plugins available in /opt/cni/bin, this way they can be mounted
do_install:append () {
	rm ${D}/opt/cni/bin
	mv ${D}/${localbindir} ${D}/opt/cni/bin
	rmdir ${D}/${libexecdir}
}

FILES_${PN}:remove = "${libexecdir}/cni/*"
FILES_${PN} += "/opt/cni/bin/*"
