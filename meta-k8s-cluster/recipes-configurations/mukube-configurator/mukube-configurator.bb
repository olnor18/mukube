SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV = "bb7d4af2c0efa943f96d497cc19baa01f09a3f0c"
SRC_URI = "git://github.com/distributed-technologies/mukube-configurator.git;branch=main \
           file://config"
S = "${WORKDIR}/git"

DEPENDS = "e2fsprogs-native"

inherit deploy

do_compile(){
	CONFIG=${WORKDIR}/config make
}

do_deploy(){
	install -d  ${DEPLOYDIR}/configs

	# Remove any old configuration
	rm -rf ${T}/config_roots

	for configfile in ${B}/artifacts/*
	do
		root_dir=${T}/config_roots/$(basename $configfile .tar)-root
		mkdir -p $root_dir
		cp $configfile $root_dir
		# Create the ext4 partition containing the rootdir
		mkfs.ext4 -d $root_dir ${DEPLOYDIR}/configs/$(basename $configfile .tar).ext4 1G
	done
}

addtask deploy after do_compile
