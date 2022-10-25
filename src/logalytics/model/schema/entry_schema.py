from typing import List, Any

from logalytics.model.entry import Entry
from logalytics.model.types import GroupName, EntryItem


class EntrySchema:
    def __init__(self,
                 canonical: List[GroupName],
                 variable: List[GroupName],
                 thread: List[GroupName]):
        self._canonical = list(sorted(canonical))
        self._variable = list(sorted(variable))
        self._thread = list(sorted(thread))

    def canonical(self, entry: Entry) -> List[EntryItem]:
        return [entry[x] for x in self._canonical]

    def variable(self, entry: Entry) -> List[EntryItem]:
        return [entry[x] for x in self._variable]

    def thread(self, entry: Entry) -> List[EntryItem]:
        return [entry[x] for x in self._thread]
