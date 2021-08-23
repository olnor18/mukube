import unittest
import os
from oeqa.runtime.case import OERuntimeTestCase
from oeqa.utils.decorators import *
from os import listdir
from os.path import isfile, join
    

class KubeadmInitTest(OERuntimeTestCase):

    def test_kubeadm_init_preflight(self):

        (status, output) = self.target.run(f"podman load -i /images.tar")
        self.assertEqual(status, 0, msg=output)

        (status, output) = self.target.run('kubeadm init --config /InitConfiguration.yaml')
        self.assertEqual(status, 0, msg=output)


