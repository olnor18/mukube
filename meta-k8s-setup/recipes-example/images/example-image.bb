require recipes-core/images/core-image-minimal.bb

DESCRIPTION = "A small calculator."

IMAGE_INSTALL += "\
containerd-opencontainers \
kubernetes \
systemd-network \ 
cri-tools \ 
conntrack-tools \
ca-certificates \
k8s-configuration \
runc-opencontainers \
cgroup-lite \
aufs-util \
openssh \
libhugetlbfs \
" 

