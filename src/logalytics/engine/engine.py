from typing import Iterable
from pathlib import Path

from logalytics.model.entry import Entry
from logalytics.model.schema.log_schema import LogSchema


def load_log(file: Path, log_format: LogSchema) -> Iterable[Entry]:
    with open(file) as logF:
        return [Entry.from_log(line, log_format) for line in logF]
