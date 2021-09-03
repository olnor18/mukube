LICENSE = "MIT"

require recipes-mukube/images/mukube-image.bb

# Parameters passed directly to qemu  
TEST_QEMUPARAMS = "-m 20000 -smp 4"

# Parameters passed directly to runqemu  
TEST_RUNQEMUPARAMS = "wic ovmf kvm"

DESCRIPTION = "A Test image build on the mukube minimal image"

IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password ssh-server-dropbear"

# Setup ip for test image
APPEND += " ip=192.168.7.2::192.168.7.1:255.255.255.0"

#TESTIMAGE_AUTO = "1"
