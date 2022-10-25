import unittest
from pathlib import Path
from datetime import datetime

from logalytics.model.entry import Entry
from logalytics.model.schema.entry_schema import EntrySchema
from logalytics.model.schema.constants import MODULE, METHOD, THREAD, TIMESTAMP
from logalytics.model.log_graph import LogGraph

log_file = Path(__file__).parent.parent.parent.parent / "sim" / "data" / "primes.log"


class TestEntry(unittest.TestCase):
    def test_add_entry(self):
        entry = Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 5), METHOD: "method", THREAD: "thread"})
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        graph = LogGraph(schema)
        graph.add_entry(entry)
        self.assertTrue(entry in graph)
        self.assertEqual(len(graph), 1)
        self.assertEqual(graph.hits(entry), [entry])

    def test_add_entries(self):
        entries = [
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread_a"}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 2), MODULE: "module", THREAD: "thread_b"})
        ]
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        graph = LogGraph(schema)
        graph.add_entries(entries)
        self.assertTrue(all(entry in graph for entry in entries))
        self.assertEqual(len(graph), 2)

    def test_add_entries_with_same_thread(self):
        entries = [
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread"}),
            Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 2), MODULE: "module", THREAD: "thread"})
        ]
        schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
        graph = LogGraph(schema)
        graph.add_entries(entries)
        self.assertTrue(all(entry in graph for entry in entries))
        self.assertEqual(len(graph), 1)
        self.assertCountEqual(graph.hits(entries[0]), entries)
        self.assertCountEqual(graph.hits(entries[1]), entries)

    # def test_add_entries_get_edges(self):
    #     start = datetime(2022, 10, 20, 21, 16, 1)
    #     end = datetime(2022, 10, 20, 21, 16, 5)
    #     entries = [
    #         Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 1), MODULE: "module", THREAD: "thread"}),
    #         Entry(**{TIMESTAMP: datetime(2022, 10, 20, 21, 16, 2), MODULE: "module", THREAD: "thread"})
    #     ]
    #     schema = EntrySchema(canonical=[MODULE], variable=[TIMESTAMP], thread=[THREAD])
    #     graph = LogGraph(schema)
    #     graph.add_entries(entries)
    #     self.assertFalse(graph.has_edge(entries[0], entries[1]))
    #     self.assertTrue(graph.has_edge(entries[1], entries[0]))
    #     self.assertEqual(end-start, graph.time_diff(entries[1], entries[0]))
    #     self.assertIsNone(graph.time_diff(entries[0], entries[1]))
