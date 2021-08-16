LICENSE = "MIT"

require recipes-extended/images/kvm-image-minimal.bb

PACKAGE_INSTALL += ""

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

DESCRIPTION = "A minimal k8s image."

IMAGE_INSTALL = "\ 
kubernetes \
cri-tools \
conntrack-tools \
k8s-configuration \
cri-o \
libhugetlbfs \
packagegroup-core-boot \
tar \
libseccomp \
tzdata \
podman \
libseccomp \
" 

APPEND += "random.trust_cpu=on hw_rng_model=virtio systemd.legacy_systemd_cgroup_controller=yes debug init=/init"
LABELS_LIVE := "boot"

IMAGE_FEATURES = ""
