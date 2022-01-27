do_install:append() {
    sed -E ':pause_image: s:^(pause_image = ).*$:\1"k8s.gcr.io/pause\:3.6":' -i ${D}/${sysconfdir}/crio/crio.conf
}

RDEPENDS:${PN}:remove = "virtual-containerd"
