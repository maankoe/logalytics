TIMESTAMP = "timestamp"


class Entry:
    def __init__(
            self,
            **kwargs
    ):
        self._entry = kwargs
        if TIMESTAMP not in self._entry:
            raise ValueError(f"{TIMESTAMP} required for creation of Entity")

    @property
    def timestamp(self):
        return self[TIMESTAMP]

    def __getitem__(self, item):
        return self._entry[item]
