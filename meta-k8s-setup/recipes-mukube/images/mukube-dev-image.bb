LICENSE = "MIT"

require recipes-mukube/images/mukube-image.bb

DESCRIPTION = "A development image build on the mukube image"


IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password"

#MK_CONFIG_FILE_PATH = "/home/azureuser/config.ext4"
