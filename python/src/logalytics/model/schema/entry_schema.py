from typing import List, Any, Tuple

from logalytics.model.entry import Entry
from logalytics.model.types import GroupName, EntryItem


class EntrySchema:
    def __init__(self,
                 canonical: List[GroupName],
                 variable: List[GroupName],
                 thread: List[GroupName]):
        self._canonical = tuple(sorted(canonical))
        self._variable = tuple(sorted(variable))
        self._thread = tuple(sorted(thread))

    def canonical(self, entry: Entry) -> Tuple[EntryItem]:
        return tuple(entry[x] for x in self._canonical)

    def variable(self, entry: Entry) -> Tuple[EntryItem]:
        return tuple(entry[x] for x in self._variable)

    def thread(self, entry: Entry) -> Tuple[EntryItem]:
        return tuple(entry[x] for x in self._thread)
