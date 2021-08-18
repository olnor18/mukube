import unittest
import os
from oeqa.runtime.case import OERuntimeTestCase
from oeqa.utils.decorators import *


def setUpModule():
    print("Setting up kubeadm test")

class KubeadmInitTest(OERuntimeTestCase):


    def test_kubeadm_init(self):
        (status, output) = self.target.run('kubeadm init --config /InitConfiguration.yaml')
        self.assertEqual(status, 0, msg=output)


