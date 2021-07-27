SUMMARY = "Append to the volatiles on the file system"
DESCRIPTION = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.MIT;md5=5750f3aa4ea2b00c2bf21b2b2a7b714d"

SRC_URI += " \
            file://COPYING.MIT"

VOLATILE_BINDS = "\ 
    /var/volatile/kubernetes /etc/kubernetes\n\
    /var/volatile/containerd /var/lib/containerd\n\
"

do_install_append(){
    install -d ${D}/var/lib/containerd
}
