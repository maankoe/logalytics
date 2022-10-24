TIMESTAMP = "timestamp"
MODULE = "module"
THREAD = "thread"
METHOD = "method"
MESSAGE = "message"


class EntrySchema:
    def __init__(self, canonical, variable, thread):
        self._canonical = list(sorted(canonical))
        self._variable = list(sorted(variable))
        self._thread = list(sorted(thread))

    def canonical(self, entry):
        return [entry[x] for x in self._canonical]

    def variable(self, entry):
        return [entry[x] for x in self._variable]

    def thread(self, entry):
        return [entry[x] for x in self._thread]
