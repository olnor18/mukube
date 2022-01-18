LICENSE = "MIT"

require recipes-extended/images/kvm-image-minimal.bb

PACKAGE_INSTALL += ""

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

DESCRIPTION = "A minimal k8s image."

IMAGE_INSTALL = "\ 
	kubernetes \
	cri-tools \
	conntrack-tools \
	k8s-configuration \
	cri-o \
	packagegroup-core-boot \
	tar \
	libseccomp \
	tzdata \
	secure-boot-setup \
	intel-microcode \
	amd-microcode \
	helm \
	gptfdisk \
	kernel-modules \
	openssh-ssh \
	openssh-sftp \
	"

# systemd only writes to the last console: https://github.com/systemd/systemd/issues/9899
APPEND += "random.trust_cpu=on hw_rng_model=virtio console=tty0 console=ttyS0,115200"

IMAGE_FEATURES = ""
