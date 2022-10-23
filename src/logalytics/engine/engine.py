from logalytics.model.entry import Entry
from logalytics.parsing.parse import parse_entry, parse_groups


def load_log(file, log_format, groups, parsers=None):
    if parsers is None:
        parsers = {}
    with open(file) as logF:
        entries = [parse_entry(line, log_format, groups) for line in logF]
        return {Entry(**parse_groups(e, parsers)) for e in entries}
