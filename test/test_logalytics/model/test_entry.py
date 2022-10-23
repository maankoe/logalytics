import unittest
from datetime import datetime

from logalytics.model.entry import Entry, TIMESTAMP


class TestEntry(unittest.TestCase):
    def test_entry_creation(self):
        timestamp = datetime(2022, 10, 20, 21, 16, 54, 524000)
        module = "module"
        entry = Entry(timestamp=timestamp, module=module)
        self.assertEqual(entry.timestamp, timestamp)
        self.assertEqual(entry["module"], module)

    def test_entry_requires_timestamp(self):
        module = "module"
        with (self.assertRaisesRegex(ValueError, f"{TIMESTAMP} required")):
            entry = Entry(module=module)