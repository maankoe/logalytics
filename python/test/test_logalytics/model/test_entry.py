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
        with (self.assertRaisesRegex(TypeError, TIMESTAMP)):
            Entry(module=module)

    def test_entry_ordering(self):
        entry_a = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 54), module="module")
        entry_b = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 55), module="main")
        self.assertNotEqual(entry_a, entry_b)
        self.assertLess(entry_a, entry_b)
        self.assertGreater(entry_b, entry_a)

    def test_entry_equal(self):
        entry_a = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 54), module="module")
        entry_b = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 54), module="module")

    def test_entry_not_equal(self):
        entry_a = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 54), module="module")
        entry_b = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 54), module="main")
        entry_c = Entry(timestamp=datetime(2022, 10, 20, 21, 16, 56), module="module")
        self.assertNotEqual(entry_a, entry_b)
        self.assertNotEqual(entry_a, entry_c)
        self.assertNotEqual(entry_b, entry_c)