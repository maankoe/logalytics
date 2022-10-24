from logalytics.model.entry_schema import TIMESTAMP


class Entry:
    # it's important to keep the number of required names (i.e., TIMESTAMP) small
    def __init__(self, timestamp, **kwargs):
        self._timestamp = timestamp
        self._entry = {TIMESTAMP: timestamp, **kwargs}

    @property
    def timestamp(self):
        return self[TIMESTAMP]

    def __getitem__(self, item):
        return self._entry[item]
