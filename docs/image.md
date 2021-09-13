# Image

A [partitioned image](https://docs.yoctoproject.org/dev-manual/common-tasks.html#creating-partitioned-images-using-wic) is created with `wic` and [our kickstart file (`.wks`)](../meta-k8s-setup/wic/mukube.wks.in). On first boot [`systemd-repart`](https://www.freedesktop.org/software/systemd/man/systemd-repart.html) creates a encrypted partition for the Secure Boot keys (see [`50-volatile.conf`](../meta-k8s-setup/recipes-core/base-files/base-files/50-volatile.conf) and [man:repart.d(5)](https://www.freedesktop.org/software/systemd/man/repart.d.html)).

## [ramrootfs](../meta-k8s-setup/recipes-core/initrdscripts/initramfs-framework/ramrootfs)

The rootfs is copied to memory (a ext4 fs backed by [zram](https://www.kernel.org/doc/html/latest/admin-guide/blockdev/zram.html) (`Compressed RAM-based block devices`) in the initramfs) for resilience and speed reasons.

It also symlinks `/run/systemd/volatile-root` to the original root partition so [`systemd-gpt-auto-generator`](https://www.freedesktop.org/software/systemd/man/systemd-gpt-auto-generator.html) and [`systemd-repart`](https://www.freedesktop.org/software/systemd/man/systemd-repart.html) can find it.