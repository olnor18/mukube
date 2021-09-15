LICENSE = "MIT"
DESCRIPTION = "Recipe for building images for an entire cluster"

require recipes-mukube/images/mukube-image.bb

IMAGE_INSTALL += "mukube-configurator"

inherit cluster

# Cluster code: 

python do_create_wks() {
    partitions_dir = os.path.join(d.getVar("DEPLOY_DIR_IMAGE"),"configs")
    config_partitions = [f for f in os.listdir(partitions_dir)]
    wks_in_file = d.getVar("WKS_FULL_PATH")
    
    with open(wks_in_file, 'r') as f:
        template = f.read()
    
    # Extract the path string that points to the current file system partition
    # The wks file reads:  
    # part /config --source rawcopy --sourceparams="file=<path-matched>" 
    replace_string = template.split("part /config")[1].split("file=")[1].split("\"")[0]
    
    for config in config_partitions:
        file_name, _ = os.path.splitext(config)
        wks_out_file = os.path.join(d.getVar('WORKDIR'), "cluster-wks", file_name + ".wks")
        with open(wks_out_file, "w") as f:
            f.write(template.replace(replace_string, os.path.join(partitions_dir, config)))
}

# Cluster code: 

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
  
    # Extract the path string that points to the current file system partition
    replace_string = template.split("part /config")[1].split("file=")[1].split("\"")[0]

    for config in configs:
        file_name = config.split(".ext4")[0]
        with open(f"{deploy_dir_image}/{file_name}.wks", "w") as wks_file:
            wks_file.write(template.replace(replace_string,f"{deploy_dir_image}/config/{config}"))
}

addtask create_wks after do_image_complete before do_populate_lic_deploy
