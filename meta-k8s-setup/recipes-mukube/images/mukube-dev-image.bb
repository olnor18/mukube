LICENSE = "MIT"

require recipes-mukube/images/mukube.bb

DESCRIPTION = "A development image build on the mukube image"


IMAGE_FEATURES += "debug-tweaks allow-empty-password empty-root-password"
