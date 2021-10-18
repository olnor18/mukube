include linux-yocto-mukube.inc

KERNEL_FEATURES:append = "cgl/cfg/net/ip_vs.scc cfg/net/bridge.scc"
