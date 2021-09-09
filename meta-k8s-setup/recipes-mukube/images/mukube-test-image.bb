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

### TODO extract this code to a bbclass or something generic ###
python do_create_wks() {
  from os import listdir
  deploy_dir_image = d.getVar("DEPLOY_DIR_IMAGE")
  config_partitions_path = f"{deploy_dir_image}/configs"
  configs = [f for f in listdir(config_partitions_path)]
  machine = d.getVar("MACHINE")
  base_name = d.getVar("IMAGE_BASENAME")
  wks_file = f"{deploy_dir_image}/{base_name}-{machine}.wks"

  with open(wks_file, 'r') as f:
        template = f.read()
  f.close()
  
  # Extract the path string that points to the current file system partition
  replace_string = template.split("part /config")[1].split("file=")[1].split("\"")[0]

  for config in configs:
    file_name = config.split(".ext4")[0]
    with open(f"{deploy_dir_image}/wks/{file_name}.wks", "w") as wks_file:
        wks_file.write(template.replace(replace_string,f"{deploy_dir_image}/config/{config}"))
    wks_file.close()
 
}

addtask create_wks after do_image_complete before do_populate_lic_deploy
