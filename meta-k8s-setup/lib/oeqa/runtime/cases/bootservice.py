import unittest
from oeqa.runtime.case import OERuntimeTestCase
from oeqa.utils.decorators import *

class BootServiceTest(OERuntimeTestCase):
    def test_boot_service(self):
        (status, output) = self.target.run(f"systemctl status boot | grep \"(code=exited, status=0/SUCCESS)\" -c")
        self.assertEqual(status, 0, msg=output)
        self.assertEqual(output, "2", msg=output)
