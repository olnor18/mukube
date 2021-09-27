# mukube

Use [kas](https://kas.readthedocs.io/en/latest/userguide.html#usage) to build a yocto project.

* Create a folder for the build, e.g. `~/yocto`
* Clone the repository into this folder, e.g. `~/yocto/mukube`
* Invoke the script `kas checkout --update mukube/kas.yaml` from the `~/yocto` folder to setup the build environment.
* Build the project using kas by running `kas build --update mukube/kas.yaml`.

## Documentation

* [Patches](docs/patches.md)
* [Proxmox](docs/proxmox.md)

## Using the NFS server for sstate mirror and downloads
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

`runqemu wic ovmf.secboot.code mukube nographic`

*Be sure your user has access to `/dev/kvm` for [KVM](https://en.wikipedia.org/wiki/Kernel-based_Virtual_Machine) acceleration, on Ubuntu this can be done by running: `sudo usermod -a -G kvm $USER`.*

### Launching the swtpm
The image builds swtpm and there is a simple script to launch it. To run swtpm, launch another shell `kas shell mukube/<kas-file>` after building the image you want to test. Then launch the swtpm by running:

`start-swtpm`

And start the image using the runqemu command above with the following extra parameters: 

`qemuparams="-chardev socket,id=chrtpm,path=/tmp/mytpm1/swtpm-sock -tpmdev emulator,id=tpm0,chardev=chrtpm -device tpm-tis,tpmdev=tpm0"`

## The test image
To generate a testable image run: 

`kas build mukube/kas-testing.yaml --update`

To run tests launch shell with:

`kas shell mukube/kas-testing.yaml --update`

and run the `testimage` task with:

`bitbake mukube-test-image -c testimage ` 

To clean the test build run:

`bitbake -c clean mukube-test-image ` 

For a full command that cleans, builds and runs the tests:

`bitbake -c clean mukube-test-image && bitbake mukube-test-image && bitbake mukube-test-image -c testimage ` 

## Building a cluster
We have a target for building multiple `wic` image files. The kas file to use is `kas-cluster.yaml`. 
