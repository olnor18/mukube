LICENSE = "MIT"
DESCRIPTION = "A Test image build on the mukube minimal image"

require mukube-image.bb

# Parameters passed directly to qemu and runqemu
TEST_QEMUPARAMS = "-m 20000 -smp 4"
TEST_RUNQEMUPARAMS = "wic ovmf kvm"

IMAGE_FEATURES += "\
    debug-tweaks \
    allow-empty-password \
    empty-root-password \
    ssh-server-dropbear \
    "

IMAGE_INSTALL += "k8s-testing"

# test_config.ext4 partition generated by the k8s-testing recipe
MUKUBE_CONFIG_PARTITION = "${DEPLOY_DIR_IMAGE}/test_config.ext4"
