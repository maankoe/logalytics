import unittest
from pathlib import Path
from datetime import datetime

from logalytics.model.entry import Entry
from logalytics.model.entry_schema import EntrySchema
from logalytics.parsing.parse import TIMESTAMP, MODULE, METHOD

log_file = Path(__file__).parent.parent.parent.parent / "sim" / "data" / "primes.log"




class TestEntry(unittest.TestCase):
    def test_entry_schema(self):
        timestamp = datetime(2022, 10, 20, 21, 16, 54, 524000)
        module = "main"
        method = "compute"
        entry = Entry(**{TIMESTAMP: timestamp, MODULE: module, METHOD: method})
        schema = EntrySchema(static=[MODULE, METHOD], variable=[TIMESTAMP])
        self.assertEqual(schema.canonical(entry), [entry[METHOD], entry[MODULE]])
        self.assertEqual(schema.canonical(entry), entry.canonical(schema))