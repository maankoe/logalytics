from typing import AnyStr
from datetime import datetime

from logalytics.model.schema.constants import TIMESTAMP
from logalytics.model.types import GroupName, EntryItem
from logalytics.parsing.parse import parse_line, parse_groups
from logalytics.model.schema.log_schema import LogSchema


class  Entry:
    # it's important to keep the number of required names (i.e., TIMESTAMP) small
    def __init__(self, timestamp: datetime, **kwargs: EntryItem):
        self._timestamp = timestamp
        self._entry = {TIMESTAMP: timestamp, **kwargs}

    @classmethod
    def from_log(cls, line: AnyStr, log_format: LogSchema) -> "Entry":
        entry = parse_line(line, log_format.pattern, log_format.groups)
        return Entry(**parse_groups(entry, log_format.parsers))

    @property
    def timestamp(self) -> datetime:
        return self._timestamp

    def __getitem__(self, item: GroupName) -> EntryItem:
        return self._entry[item]

    def __lt__(self, other: "Entry"):
        if not isinstance(other, Entry):
            raise TypeError(f"{type(self)} < {type(other)} is not possible")
        return self.timestamp.__lt__(other.timestamp)

    def __eq__(self, other: "Entry"):
        return isinstance(other, Entry) and self._entry == other._entry