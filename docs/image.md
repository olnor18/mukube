# Image

A [partitioned image](https://docs.yoctoproject.org/dev-manual/common-tasks.html#creating-partitioned-images-using-wic) is created using `wic` (OpenEmbedded image creator). The partitions are described in [our kickstart file (`.wks`)](../meta-k8s-setup/wic/mukube.wks.in). 

## Encrypted partition

On first boot [`systemd-repart`](https://www.freedesktop.org/software/systemd/man/systemd-repart.html) creates a encrypted partition for the Secure Boot keys (see [`50-volatile.conf`](../meta-k8s-setup/recipes-core/base-files/base-files/50-volatile.conf) and [man:repart.d(5)](https://www.freedesktop.org/software/systemd/man/repart.d.html)).

## Secure Boot

The image boots with UEFI Secure Boot. In a virtual environment UEFI is supported through OVMF ([Open Virtual Machine Firmware](https://www.linux-kvm.org/page/OVMF)). 

## Unified kernel image, systemd-boot bootloader

In order to simplify the Secure Boot process and have everything signed, we package the kernel, initrd, and the kernel command line in a [Unified Kernel Image](https://systemd.io/BOOT_LOADER_SPECIFICATION#type-2-efi-unified-kernel-images). Booting this is supported with the bootloader [systemd-boot](https://www.freedesktop.org/software/systemd/man/sd-boot.html).

## [ramrootfs](../meta-k8s-setup/recipes-core/initrdscripts/initramfs-framework/ramrootfs)

The rootfs is copied to memory (a ext4 fs backed by [zram](https://www.kernel.org/doc/html/latest/admin-guide/blockdev/zram.html) (`Compressed RAM-based block devices`) in the initramfs) for resilience and speed reasons.

It also symlinks `/run/systemd/volatile-root` to the original root partition so [`systemd-gpt-auto-generator`](https://www.freedesktop.org/software/systemd/man/systemd-gpt-auto-generator.html) and [`systemd-repart`](https://www.freedesktop.org/software/systemd/man/systemd-repart.html) can find it.
