from logalytics.model.entry_schema import TIMESTAMP


class Entry:
    # it's important to keep the number of reserved names (i.e., TIMESTAMP) small
    def __init__(self, **kwargs):
        self._entry = kwargs
        if TIMESTAMP not in self._entry:
            raise ValueError(f"{TIMESTAMP} required for creation of Entity")

    @property
    def timestamp(self):
        return self[TIMESTAMP]

    def __getitem__(self, item):
        return self._entry[item]

    def canonical(self, schema):
        return schema.canonical(self)
