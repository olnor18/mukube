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

If your kas configuration outputs an iso file it will be located in the ./build/tmp/deploy/images/ folder.
