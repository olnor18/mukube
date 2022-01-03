HOMEPAGE = "https://helm.sh"
SUMMARY = "A package manager for Kubernetes"
DESCRIPTION = "Helm helps manage Kubernetes applications through Helm Charts. \
               Helm Charts define, install, and upgrade complex Kubernetes applications."

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=6a18660411af65e17f370bdbb50e6957"

SRC_URI = "git://github.com/helm/helm;protocol=https;branch=release-3.7"
SRCREV = "eeac83883cb4014fe60267ec6373570374ce770b"

S = "${WORKDIR}/git"

GO_IMPORT = "github.com/helm/helm"
GO_INSTALL = "${GO_IMPORT}/cmd/helm"
GO_WORKDIR = "${GO_INSTALL}"

inherit go-mod

