HOMEPAGE = "https://github.com/Foxboron/sbctl"
SUMMARY = "💻 Secure Boot key manager"
DESCRIPTION = "sbctl intends to be a user-friendly secure boot key \
manager capable of setting up secure boot, offer key management \
capabilities, and keep track of files that needs to be signed in the \
boot chain."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=90982035f2d0342d5bf8ac9dfceefa7a"

GO_IMPORT = "github.com/foxboron/sbctl"
GO_INSTALL = "${GO_IMPORT}/cmd/sbctl"

SRC_URI = "git://${GO_IMPORT}.git"
SRCREV = "0.6"

S = "${WORKDIR}/git"

inherit go
inherit go-mod

INSANE_SKIP:${PN}-dev += "file-rdeps"
FILES:${PN} = "${bindir}/sbctl"
