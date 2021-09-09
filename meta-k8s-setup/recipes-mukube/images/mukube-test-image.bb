LICENSE = "MIT"

require recipes-mukube/images/mukube-image.bb

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

python do_create_wks() {
  from os import listdir
  from os.path import isfile, join
  deploy_dir_image = d.getVar("DEPLOY_DIR_IMAGE")
  mypath = f"{deploy_dir_image}/configs"
  onlyfiles = [f for f in listdir(mypath)]
  machine = d.getVar("MACHINE")
  base_name = d.getVar("IMAGE_BASENAME")
  wks_file = f"{deploy_dir_image}/{base_name}-{machine}.wks"

  with open(wks_file, 'r') as f:
        template = f.read()
  f.close()
  
  replace_string = template.split("part /config")[1].split("file=")[1].split("\"")[0]
  for f in onlyfiles:
    file_name = f.split(".tar")[0]
    with open(f"{deploy_dir_image}/wks/{file_name}.wks", "w") as wks_file:
        wks_file.write(template.replace(replace_string,f"{deploy_dir_image}/config/{f}"))
    wks_file.close()
 
}

addtask create_wks after do_image_complete before do_populate_lic_deploy
