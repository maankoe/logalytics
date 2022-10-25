import unittest
from pathlib import Path
from datetime import datetime

from logalytics.engine.engine import load_log
from logalytics.model.entry import Entry
from logalytics.parsing.parse import TIMESTAMP, MODULE, MESSAGE
from logalytics.model.schema.log_schema import LogSchema

log_file = Path(__file__).parent.parent.parent.parent / "sim" / "data" / "primes.log"


class TestEntry(unittest.TestCase):
    def test_load_log(self):
        log_format = LogSchema(
            pattern="(....-..-.. ..:..:..,...): (.*): (.*)",
            groups=[TIMESTAMP, MODULE, MESSAGE],
            parsers={TIMESTAMP: lambda x: datetime.strptime(x, "%Y-%m-%d %H:%M:%S,%f")}
        )
        entries = load_log(log_file, log_format)
        self.assertTrue(all(isinstance(x, Entry) for x in entries))
        self.assertTrue(all(isinstance(x.timestamp, datetime) for x in entries))