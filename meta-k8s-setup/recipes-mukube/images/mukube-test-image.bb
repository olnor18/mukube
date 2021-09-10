LICENSE = "MIT"

require recipes-mukube/images/mukube-image.bb

inherit cluster

# Parameters passed directly to qemu  
TEST_QEMUPARAMS = "-m 20000 -smp 4"

# Parameters passed directly to runqemu  
TEST_RUNQEMUPARAMS = "wic ovmf kvm"

DESCRIPTION = "A Test image build on the mukube minimal image"

IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password ssh-server-dropbear"

# Setup ip for test image
APPEND += " ip=192.168.7.2::192.168.7.1:255.255.255.0"

IMAGE_INSTALL += "k8s-testing k8s-cluster"

MK_CONFIG_FILE_PATH = "${BASE_WORKDIR}/core2-64-poky-linux/k8s-testing/1.0-r0/temp/config.ext4"
#TESTIMAGE_AUTO = "1"

IMAGE_CMD_wic () {
	out="${IMGDEPLOYDIR}/${IMAGE_NAME}"
	build_wic="${WORKDIR}/build-wic"
	tmp_wic="${WORKDIR}/tmp-wic"
	wks="${WKS_FULL_PATH}"
	if [ -e "$tmp_wic" ]; then
		# Ensure we don't have any junk leftover from a previously interrupted
		# do_image_wic execution
		rm -rf "$tmp_wic"
	fi
	if [ -z "$wks" ]; then
		bbfatal "No kickstart files from WKS_FILES were found: ${WKS_FILES}. Please set WKS_FILE or WKS_FILES appropriately."
	fi
	bbplain "USING wks = ${wks}"
	BUILDDIR="${TOPDIR}" PSEUDO_UNLOAD=1 wic create "$wks" --vars "${STAGING_DIR}/${MACHINE}/imgdata/" -e "${IMAGE_BASENAME}" -o "$build_wic/" -w "$tmp_wic" ${WIC_CREATE_EXTRA_ARGS}
	mv "$build_wic/$(basename "${wks%.wks}")"*.direct "$out${IMAGE_NAME_SUFFIX}.wic"

	bbplain "EXECUTING MODIFIED METHOD"
	bbplain "DEPLOY_DIR is ${DEPLOY_DIR}"
}

