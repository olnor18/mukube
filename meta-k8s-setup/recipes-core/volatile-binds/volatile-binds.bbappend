SUMMARY = "Append to the volatiles on the file system"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=5750f3aa4ea2b00c2bf21b2b2a7b714d"

SRC_URI += " \
            file://COPYING.MIT"

VOLATILE_BINDS = "\ 
    /var/volatile/kubernetes /etc/kubernetes\n\
    /var/volatile/containerd /var/lib/containerd\n\
    /var/volatile/kubelet /var/lib/kubelet\n\
    /var/volatile/etcd /var/lib/etcd\n\
    /var/volatile/etcdctl /var/lib/etcdctl\n\
    /var/volatile/weave /var/lib/weave\n\
    /var/volatile/usr/libexec/kubernetes /usr/libexec/kubernetes\n\
    /var/volatile/cni /var/lib/cni\n\
    /var/volatile/opt/containerd /opt/containerd\n\
"

FILES_${PN} += " /opt/containerd/"

do_install_append(){
    install -d ${D}/var/lib/containerd
    install -d ${D}/var/lib/kubelet
    install -d ${D}/var/lib/etcd
    install -d ${D}/var/lib/etcdctl
    install -d ${D}/var/lib/weave
    install -d ${D}/usr/libexec/kubernetes
    install -d ${D}/var/lib/cni
    install -d ${D}/opt/containerd
}   
