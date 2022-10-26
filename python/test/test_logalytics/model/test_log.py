import unittest
from pathlib import Path
from datetime import datetime

from logalytics.model.entry import Entry
from logalytics.model.schema.entry_schema import EntrySchema
from logalytics.model.schema.constants import MODULE, METHOD, THREAD, TIMESTAMP
from logalytics.model.log import Log

log_file = Path(__file__).parent.parent.parent.parent / "sim" / "data" / "primes.log"


class TestEntry(unittest.TestCase):
    def test_add_entry(self):
        entry = Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 5), METHOD: "method", THREAD: "thread"})
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        log = Log(schema)
        log.add_entry(entry)
        self.assertTrue(entry in log)
        self.assertEqual(len(log), 1)

    def test_add_entries(self):
        entries = [
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread_a"}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 2), MODULE: "module", THREAD: "thread_b"})
        ]
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        log = Log(schema)
        log.add_entries(entries)
        self.assertTrue(all(entry in log for entry in entries))
        self.assertEqual(len(log), 2)

    def test_get_latest_entry_by_timestamp(self):
        entries = [
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 3), MODULE: "module", THREAD: "thread_a"}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 5), MODULE: "module", THREAD: "thread_a"}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread_b"})
        ]
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        log = Log(schema)
        log.add_entries(entries)
        self.assertEqual(entries[1], log.latest())

    def test_get_latest_entry_on_thread(self):
        thread_a = "thread_a"
        thread_b = "thread_b"
        entries = [
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 5), MODULE: "module", THREAD: thread_a}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 3), MODULE: "module", THREAD: thread_b}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: thread_a}),
        ]
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        log = Log(schema)
        log.add_entries(entries)
        self.assertEqual(entries[0], log.latest_by_thread((thread_a,)))
        self.assertEqual(entries[1], log.latest_by_thread((thread_b,)))

    # def test_add_entries_with_same_thread(self):
    #     entries = [
    #         Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread"}),
    #         Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 2), MODULE: "module", THREAD: "thread"})
    #     ]
    #     schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
    #     log = LogGraph(schema)
    #     log.add_entries(entries)
    #     self.assertTrue(all(entry in log for entry in entries))
    #     self.assertEqual(len(log), 1)
    #     self.assertCountEqual(log.hits(entries[0]), entries)
    #     self.assertCountEqual(log.hits(entries[1]), entries)

    # def test_add_entries_get_edges(self):
    #     start = datetime(2022, 10, 20, 21, 16, 1)
    #     end = datetime(2022, 10, 20, 21, 16, 5)
    #     entries = [
    #         Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread"}),
    #         Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 2), MODULE: "module", THREAD: "thread"})
    #     ]
    #     schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
    #     log = LogGraph(schema)
    #     log.add_entries(entries)
    #     self.assertFalse(log.has_edge(entries[0], entries[1]))
    #     self.assertTrue(log.has_edge(entries[1], entries[0]))
    #     self.assertEqual(end-start, log.time_diff(entries[1], entries[0]))
    #     self.assertIsNone(log.time_diff(entries[0], entries[1]))
