import re

TIMESTAMP = "timestamp"
MODULE = "model"
MESSAGE = "message"


def _identity_parsers(log_groups):
    return {g: lambda x: x for g in log_groups}


def parse_entry(line, log_format, log_groups):
    match = re.match(log_format, line)
    return {group_name: group for group_name, group in zip(log_groups, match.groups())}


def parse_groups(entry, parsers):
    parsers = {**_identity_parsers(entry.keys()), **parsers}
    return {group_name: parser(entry[group_name]) for group_name, parser in parsers.items()}
