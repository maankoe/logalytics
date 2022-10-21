from logalytics.model.entry import Entry
from logalytics.parsing.parse import parse_entry


def load_log(file, format, groups):
    with open(file) as logF:
        return {Entry(**parse_entry(line, format, groups)) for line in logF}
