LICENSE = "MIT"

require recipes-mukube-minimal/images/mukube-minimal.bb

TEST_QEMUPARAMS = "-m 6000 -smp 2"

TEST_RUNQEMUPARAMS = "bootparams="init=/init""

DESCRIPTION = "A Test image build on the mukube minimal image"

IMAGE_INSTALL += "k8s-testing"

IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password ssh-server-dropbear"
