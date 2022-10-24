from abc import ABC, abstractmethod


class EntrySummary(ABC):
    @property
    @abstractmethod
    def timestamp(self):
        pass


class EntrySummarizer(ABC):
    @abstractmethod
    def _summarize(self, entry):
        pass

    def __call__(self, entry):
        return self._summarize(entry)
