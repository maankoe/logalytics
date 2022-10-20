import re
from datetime import datetime


TIMESTAMP = "timestamp"
MESSAGE = "message"


def parse_entry(line, log_format, log_groups, date_format):
    match = re.match(log_format, line)
    bits = {g: x for g, x in zip(log_groups, match.groups())}
    bits["timestamp"] = datetime.strptime(bits["timestamp"], date_format)
    return {"raw": line, **bits}