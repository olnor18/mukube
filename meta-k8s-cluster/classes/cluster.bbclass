
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
