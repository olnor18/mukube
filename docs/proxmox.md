# Proxmox

The image can be run in Proxmox by following these steps:

1. Transfer the Wic image to the Proxmox server
1. Create a new VM:
    ```sh
    $ qm create 10000 \
      --name vm10000 \
      --cores 2 \
      --memory 4096 \
      --bios ovmf \
      --net0 virtio,bridge=vmbr0 \
      --serial0 socket \
      --vga none \
      --scsihw virtio-scsi-pci \
      --boot order=scsi0 \
      --ostype l26
    ```
1. Import the disk:
    ```sh
    $ qm importdisk 10000 mukube-test-image-mukube-20210921121134.rootfs.wic local-lvm
    ```
1. Attach the disk as `scsi0`:
    ```sh
    $ qm set 10000 --scsi0 local-lvm:vm-10000-disk-0
    ```
1. Start the VM:
    ```sh
    $ qm start 10000
    ```
