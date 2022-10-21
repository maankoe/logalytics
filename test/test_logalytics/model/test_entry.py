import unittest
from datetime import datetime

from logalytics.model.entry import Entry


class TestEntry(unittest.TestCase):
    def test_entry_creation(self):
        timestamp = datetime(2022, 10, 20, 21, 16, 54, 524000)
        entry = Entry(timestamp=timestamp, module="model", message="message")
        self.assertEqual(entry.timestamp, timestamp)