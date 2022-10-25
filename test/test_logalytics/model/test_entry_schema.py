import unittest
from datetime import datetime

from logalytics.model.entry import Entry
from logalytics.model.schema.entry_schema import EntrySchema
from logalytics.parsing.parse import TIMESTAMP, MODULE, METHOD, THREAD


class TestEntry(unittest.TestCase):
    def test_entry_schema(self):
        timestamp = datetime(2022, 10, 20, 21, 16, 54, 524000)
        module = "main"
        method = "compute"
        thread = "thread"
        entry = Entry(**{TIMESTAMP: timestamp, MODULE: module, METHOD: method, THREAD: thread})
        schema = EntrySchema(canonical=[MODULE, METHOD], variable=[TIMESTAMP], thread=[THREAD])
        self.assertEqual(schema.canonical(entry), [entry[METHOD], entry[MODULE]])
        self.assertEqual(schema.thread(entry), [entry[THREAD]])