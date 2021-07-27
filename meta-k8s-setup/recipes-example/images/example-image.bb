require recipes-core/images/core-image-base.bb

DESCRIPTION = "A small calculator."

IMAGE_INSTALL += "\
containerd-opencontainers \
kubernetes \
systemd-network \ 
cri-tools \ 
conntrack-tools \
ca-certificates \
k8s-configuration \
" 

