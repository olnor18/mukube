# mukube

Use [kas](https://kas.readthedocs.io/en/latest/userguide.html#usage) to build a yocto project.

* Create a folder for the build, e.g. `~/yocto`
* Clone the repository into this folder, e.g. `~/yocto/mukube`
* Invoke the script `kas checkout --update mukube/kas.yaml` from the `~/yocto` folder to setup the build environment.
* Build the project using kas by running `kas build --update mukube/kas.yaml`.

### Using the NFS server for sstate mirror and downloads
To use the nfs server it must be mounted on the machine you are working on. 
1. Install the package `nsf-common` using `apt`
2. add the following line to `/etc/fstab` in order to mount the nfs at boot `<ip_of_nfs-server>:/	/mnt	nfs	auto	0	0` 

An example local kas file which sets the downloads directory and sstate cache mirror is
```
header:
  version: 10

local_conf_header:
  DL_DIR: |
    DL_DIR = "<path to nfs download folder>"

  SSTATE_DIR: |
    SSTATE_DIR = "<path to local sstate dir>"

  SSTATE_MIRROR: | 
    SSTATE_MIRRORS ?= "\
    file://.* file://<path to nfs sstate-cache folder>/PATH \
    "
``` 

To use the local kas file you have to chain them by `kas <command> mukube/kas.yaml:mukube/kas-local.yaml`.

As kas needs the local file to be in the same directory as the original kas file we require this file to be named `kas-local.yaml`

If your kas configuration outputs an iso file it will be located in the ./build/tmp/deploy/images/ folder.

To run the generated OS in qemu use:

`runqemu wic ovmf genericx86-64 nographic`
