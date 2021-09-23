LICENSE = "MIT"
DESCRIPTION = "A Test image build on the mukube minimal image"

require mukube-dev-image.bb

# Parameters passed directly to qemu and runqemu
TEST_QEMUPARAMS = "-m 20000 -smp 4"
TEST_RUNQEMUPARAMS = "wic ovmf kvm"

IMAGE_FEATURES += "\
    ssh-server-dropbear \
    "
