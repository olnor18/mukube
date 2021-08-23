LICENSE = "MIT"

require recipes-mukube-minimal/images/mukube-minimal.bb

TEST_QEMUPARAMS = "-m 10000 -smp 2"

DESCRIPTION = "A Test image build on the mukube minimal image"

IMAGE_INSTALL += ""

IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password ssh-server-dropbear"
