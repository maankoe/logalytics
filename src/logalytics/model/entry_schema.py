TIMESTAMP = "timestamp"
MODULE = "module"
METHOD = "method"
MESSAGE = "message"


class EntrySchema:
    def __init__(self, static, variable):
        self._static = list(sorted(static))
        self._variable = list(sorted(variable))

    def canonical(self, entry):
        return [entry[x] for x in self._static]
