SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV = "bb7d4af2c0efa943f96d497cc19baa01f09a3f0c"
SRC_URI = "git://github.com/distributed-technologies/mukube-configurator.git;branch=main \
    file://config"

inherit deploy

do_configure(){
  mkdir ${DL_DIR}/mukube-configurator/config -p 
  cat ${WORKDIR}/config > ${DL_DIR}/mukube-configurator/config/config 
  cd ${DL_DIR}/mukube-configurator && make
}

do_deploy(){
  install -d  ${DEPLOYDIR}/configs
  install -d  ${DEPLOYDIR}/wks
  
  install -m 644 ${DL_DIR}/mukube-configurator/artifacts/* ${DEPLOYDIR}/configs
}

addtask deploy after do_compile

do_deploy[nostamp] = "1"
