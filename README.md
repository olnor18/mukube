# mukube

The aim of the project is to build a minimal Linux distribution capable of running Kubernetes and with support for needed security features. The project is based on [the Yocto project](https://www.yoctoproject.org/) and [OpenEmbedded](http://www.openembedded.org/wiki/Main_Page), which uses [BitBake](https://www.yoctoproject.org/docs/latest/bitbake-user-manual/bitbake-user-manual.html) as the task execution engine. On top of that we use [kas](https://kas.readthedocs.io/en/latest/userguide.html#usage) to manage the project dependencies and build the project.

### Quick build
* Create a folder for the build, e.g. `~/yocto`
* Clone the repository into this folder, e.g. `~/yocto/mukube`
* Install [`kas`](https://kas.readthedocs.io/en/latest/userguide.html#dependencies-installation) and create a `kas-local.yaml` file. A guide to configuring kas-local.yaml can be found in [local configurations for kas-local.yaml](#local-configurations-in-kas-localyaml).
* Make sure you can run yocto by installing the [required packages](https://docs.yoctoproject.org/ref-manual/system-requirements.html#required-packages-for-the-build-host)
* Build the project using kas by running `kas build --update mukube/kas-base.yaml`

## Documentation

* [Patches](docs/patches.md)
* [Proxmox](docs/proxmox.md)
* [Image (partitions/fs)](docs/image.md)

## Local configurations in `kas-local.yaml`
A local kas file is _required_ for the kas tool to run, since we include it in our `kas-base.yaml` file. A minimal local kas file, which in turn does not change is: 
```yaml
header:
  version: 10
```
Usually in the local kas file one wants to set the following variables:
* `DL_DIR` - Download directory for source code.
* `SSTATE_DIR` - [sstate cache](https://www.yoctoproject.org/docs/latest/mega-manual/mega-manual.html#setscene-tasks-and-shared-state) dir to share the sstate between builds.
* `SSTATE_MIRRORS` - sstate available remotely at eg. a nfs server 
* `CCACHE` - for using [Compiler cache](https://ccache.dev/)

An example local kas file which sets above is
```yaml
header:
  version: 10

local_conf_header:
  CCACHE: | 
    INHERIT += "ccache"
    CCACHE_TOP_DIR = "<path to ccache directory>"
    CCACHE_BASEDIR = "<path to the yocto directory>"

  DL_DIR: |
    DL_DIR = "<path to nfs download directory>"

  SSTATE_DIR: |
    SSTATE_DIR = "<path to local sstate directory>"

  SSTATE_MIRROR: | 
    SSTATE_MIRRORS ?= "\
    file://.* file://<path to nfs sstate-cache directory>/PATH \
    "
``` 

To use a NFS server, the exposed directories must be mounted on the machine you are working on. 
1. Install the package `nfs-common` using eg. `apt`
2. Add the following line to `/etc/fstab` in order to mount the NFS at boot `<ip_of_nfs-server>:/	/mnt	nfs	auto	0	0` 

## Building and testing using kas/bitbake 

The yocto project uses bitbake to schedule tasks and we use kas to manage project dependencies. 

`kas checkout --update mukube/kas-base.yaml` checks out and updates all dependencies.

`kas shell mukube/kas-base.yaml` checks out the dependencies and initialises the bitbake environment in a new shell. It is from this shell all the following `bitbake` and `runqemu` commands are exeduted. The different versions of our image: `dev`, `test`, and `base`, use different kas files, be sure to initialize the shell with the kas file for the versions of the image you are working on.

To run the image locally, start by building the `dev` image, i.e. use the `kas-dev.yaml` as the target for the `kas build` command. Then from the `kas shell` (again with the `kas-dev.yaml` as the target) the image kan be launched in qemu with  

`runqemu wic ovmf.secboot.code mukube nographic`

*Be sure your user has access to `/dev/kvm` for [KVM](https://en.wikipedia.org/wiki/Kernel-based_Virtual_Machine) acceleration, on Ubuntu this can be done by running: `sudo usermod -a -G kvm $USER`.*

### Tests 
We have a few tests we run with the image. They are implemented in Python and are located in [`meta-k8s-setup/lib/oeqa/runtime/cases`](meta-k8s-setup/lib/oeqa/runtime/cases). Our test image is configured for the tests to run automatically when the target is built. Thus to run the tests, simply build the test target, with either `kas build mukube/kas-testing.yaml` or from the kas shell run `bitbake mukube-test-image`.

## Launching with swtpm
The image builds swtpm as a tool and there is a simple script to launch. To run swtpm, launch another shell `kas shell mukube/<kas-file>` after building the image you want to test. Then launch the swtpm by running:

`start-swtpm`

And start the image using the runqemu command above with the following extra parameters: 

`qemuparams="-chardev socket,id=chrtpm,path=/tmp/mytpm1/swtpm-sock -tpmdev emulator,id=tpm0,chardev=chrtpm -device tpm-tis,tpmdev=tpm0"`

Notice that swtpm exits when the virtual machine turns off, and has to be relaunched before the next use.

## Building a cluster
We have a target for building multiple `wic` image files. The kas file to use is `kas-cluster.yaml`. 

## VMware
The image can be converted to a format compatible with VMware EXSi (tested with EXSi 7.0.2) with this command:
```sh
$ qemu-img convert -f raw -O vmdk -o adapter_type=lsilogic,subformat=monolithicFlat,compat6 mukube-dev-image-mukube.wic mukube-dev-image-mukube.vmdk
```
