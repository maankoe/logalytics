import unittest

from datetime import datetime

from logalytics.parsing.parse import parse_entry, parse_groups, TIMESTAMP, MESSAGE, MODULE


class TestParsing(unittest.TestCase):
    def test_parse_line(self):
        timestamp = datetime(2022, 10, 20, 21, 16, 54, 524000)
        module = "model"
        message = "Testing: 521295"
        line = "{timestamp}/{module}/{message}".format(timestamp=timestamp, module=module, message=message)
        log_format = "(.*)/(.*)/(.*)"
        log_groups = [TIMESTAMP, MODULE, MESSAGE]
        entry = parse_entry(line, log_format, log_groups)
        self.assertEqual(entry, {TIMESTAMP: str(timestamp), MESSAGE: message, MODULE: module})

    def test_parse_entry_items(self):
        timestamp = datetime(2022, 10, 20, 21, 16, 54, 524000)
        module = "model"
        message = "Testing: 521295"
        date_format = "%Y-%m-%d %H:%M:%S.%f"
        parsers = {TIMESTAMP: lambda x: datetime.strptime(x, date_format)}
        entry = parse_groups({TIMESTAMP: str(timestamp), MESSAGE: message, MODULE: module}, parsers)
        self.assertEqual(entry, {TIMESTAMP: timestamp, MESSAGE: message, MODULE: module})
