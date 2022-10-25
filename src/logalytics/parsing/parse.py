from typing import AnyStr, Union, List, Dict, Callable
import re

from logalytics.model.types import GroupName, EntryItem

TIMESTAMP = "timestamp"
MODULE = "module"
METHOD = "method"
THREAD = "thread"
MESSAGE = "message"


def _identity_parsers(log_groups):
    return {g: lambda x: x for g in log_groups}


def parse_line(line: AnyStr, log_pattern: Union[AnyStr, re.Pattern], log_groups: List[AnyStr]) -> Dict[GroupName, AnyStr]:
    match = re.match(log_pattern, line)
    return {group_name: group for group_name, group in zip(log_groups, match.groups())}


def parse_groups(entry: Dict[GroupName, AnyStr], parsers: Dict[GroupName, Callable[[AnyStr], EntryItem]]):
    parsers = {**_identity_parsers(entry.keys()), **parsers}
    return {group_name: parser(entry[group_name]) for group_name, parser in parsers.items()}
