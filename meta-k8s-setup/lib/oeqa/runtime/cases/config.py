import unittest
import os
from oeqa.runtime.case import OERuntimeTestCase
from oeqa.utils.decorators import *
from os import listdir
from os.path import isfile, join
    
class ConfigPartitionTest(OERuntimeTestCase):

    def test_config_present(self):
        # Check to see that the config tar is correctly mounted
        self.assertTrue(os.path.exists("/config/config.tar.gz"))
        # Count from grep should be 1
        self.assertEqual(output, "1", msg=output)
