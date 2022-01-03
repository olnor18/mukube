include runc.inc

SRCREV = "4144b63817ebcc5b358fc2c8ef95f7cddd709aa7"
SRC_URI = " \
    git://github.com/opencontainers/runc;branch=release-1.0 \
    file://0001-Doing-patch-of-build.patch \
    "
RUNC_VERSION = "1.0.1"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
