# mukube

Use [kas](https://kas.readthedocs.io/en/latest/userguide.html#usage) to build a yocto project.

* Create a folder for the build, e.g. `~/yocto`
* Clone the repository into this folder, e.g. `~/yocto/mukube`
* Invoke the script `kas checkout --update mukube/kas.yaml` from the `~/yocto` folder to setup the build environment.
* Build the project using kas by running `kas build --update mukube/kas.yaml`.

If your kas configuration outputs an iso file it will be located in the ./build/tmp/deploy/images/ folder.

To run the generated OS in qemu use:

´ runqemu genericx86-64 nographic qemuparams="-m 6000 -smp 2" bootparams="init=/init" ´
