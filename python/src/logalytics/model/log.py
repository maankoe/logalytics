from typing import Iterable, AnyStr, Dict, Tuple, Union
from sortedcontainers import SortedList

from logalytics.model.entry import Entry
from logalytics.model.schema.constants import THREAD
from logalytics.model.schema.entry_schema import EntrySchema
from logalytics.model.types import EntryItem


class Log:
    def __init__(self, entry_schema: EntrySchema):
        self._entry_schema = entry_schema
        self._entries = SortedList()
        self._entries_by_thread: Dict[AnyStr, SortedList] = {}

    def add_entry(self, entry: Entry):
        self._entries.add(entry)
        self._entries_by_thread.setdefault(self._entry_schema.thread(entry), SortedList()).add(entry)

    def add_entries(self, entries: Iterable[Entry]):
        for entry in entries:
            self.add_entry(entry)

    def latest(self):
        return self._entries[-1]

    def latest_by_thread(self, thread: Tuple[EntryItem]):
        return self._entries_by_thread[thread][-1]

    def __contains__(self, entry: Entry):
        return entry in self._entries

    def __len__(self):
        return len(self._entries)
