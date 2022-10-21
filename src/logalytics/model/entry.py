class Entry():
    def __init__(
            self,
            *,
            timestamp,
            **kwargs
    ):
        self._timestamp = timestamp
        self._entry = kwargs

    @property
    def timestamp(self):
        return self._timestamp
