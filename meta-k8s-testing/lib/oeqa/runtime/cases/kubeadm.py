import unittest
import os
from oeqa.runtime.case import OERuntimeTestCase
from oeqa.utils.decorators import *
from os import listdir
from os.path import isfile, join
    

class KubeadmInitTest(OERuntimeTestCase):

    def test_kubeadm_init_preflight(self):
        myPath = "/var/ctr-images/"
        (status, output) = self.target.run(f"ls {myPath}")
        container_files = output.split("\n")
        (status, output) = self.target.run(f"df -h")
        self.assertEqual(status, 0, msg=output)
        for ctr in container_files:
            (status, output) = self.target.run(f"podman load -i {myPath}{ctr}")
            self.assertEqual(status, 0, msg=output)
            (status, output) = self.target.run(f"df -h")
            self.assertEqual(status, 0, msg=output)


        (status, output) = self.target.run('kubeadm init phase preflight --config /InitConfiguration.yaml')
        self.assertEqual(status, 0, msg=output)


