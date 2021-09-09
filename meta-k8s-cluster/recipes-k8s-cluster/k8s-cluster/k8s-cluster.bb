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
  install -d  ${DEPLOYDIR}/wks 

  mkdir ${B}/tmp/config-partition/ -p
  
  for tar_config in ${B}/artifacts/*
  do
    mv $tar_config ${B}/tmp/config-partition/
    # Keep basename but postfix file type
    file_name=$(basename $tar_config .tar).ext4
    # Create the ext4 partition from the folder containing the tar
    cd ${B}/tmp/ && mkfs.ext4 -d config-partition $file_name 1G
    install -m 644 ${B}/tmp/$file_name ${DEPLOYDIR}/configs/$filename
    rm ${B}/tmp/config-partition/*
  done
}

addtask deploy after do_compile

# The tasks will always run
do_deploy[nostamp] = "1"
do_compile[nostamp] = "1"

