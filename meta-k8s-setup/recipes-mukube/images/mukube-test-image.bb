LICENSE = "MIT"

require recipes-mukube/images/mukube.bb

# Parameters parsed directly to qemu  
TEST_QEMUPARAMS = "-m 20000 -smp 4"

# Make qemu boot the iso to do testing
TEST_RUNQEMUPARAMS = "iso"

DESCRIPTION = "A Test image build on the mukube minimal image"

IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password ssh-server-dropbear"

#TESTIMAGE_AUTO = "1"
