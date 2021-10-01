import unittest
import os
from oeqa.runtime.case import OERuntimeTestCase
from oeqa.utils.decorators import *
from os import listdir
from os.path import isfile, join
    
class ConfigPartitionTest(OERuntimeTestCase):

    def test_config_present(self):
        (status, output) = self.target.run(f"ls /var/state/config | grep config.tar -c")
        self.assertEqual(status, 0, msg=output)
        self.assertEqual(output, "1", msg=output)
