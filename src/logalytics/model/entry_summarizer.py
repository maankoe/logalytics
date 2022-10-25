from abc import ABC, abstractmethod
from datetime import datetime

from logalytics.model.entry import Entry


class EntrySummary(ABC):
    @property
    @abstractmethod
    def timestamp(self) -> datetime:
        pass


class EntrySummarizer(ABC):
    @abstractmethod
    def _summarize(self, entry: Entry) -> EntrySummary:
        pass

    def __call__(self, entry: Entry) -> EntrySummary:
        return self._summarize(entry)
