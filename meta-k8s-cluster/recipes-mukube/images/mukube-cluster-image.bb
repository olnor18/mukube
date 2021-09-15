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
