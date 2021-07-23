require recipes-core/images/core-image-base.bb

DESCRIPTION = "A small calculator."

IMAGE_INSTALL += "containerd-opencontainers kubernetes cri-tools conntrack-tools ca-certificates" 

