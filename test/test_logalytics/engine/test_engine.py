import unittest
from pathlib import Path

from logalytics.engine.engine import load_log
from logalytics.model.entry import Entry
from logalytics.parsing.parse import TIMESTAMP, MODULE, MESSAGE

log_file = Path(__file__).parent.parent.parent.parent / "sim" / "data" / "primes.log"


class TestEntry(unittest.TestCase):
    def test_load_log(self):
        # 2022-10-20 21:16:54,057: Testing: 933559
        log_format = "(....-..-.. ..:..:..,...): (.*): (.*)"
        log_groups = [TIMESTAMP, MODULE, MESSAGE]
        entries = load_log(log_file, log_format, log_groups)
        self.assertTrue(all(isinstance(x, Entry) for x in entries))