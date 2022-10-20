import unittest

from datetime import datetime

from logalytics.parsing.parse import parse_entry, TIMESTAMP, MESSAGE


class TestParsing(unittest.TestCase):
    def test_parse_line(self):
        date = datetime(2022, 10, 20, 21, 16, 54, 524000)
        message = "Testing: 521295"
        line = "{date}/ {message}".format(date=date, message=message)
        log_format = "(.*)/ (.*)"
        log_groups = [TIMESTAMP, MESSAGE]
        date_format = "%Y-%m-%d %H:%M:%S.%f"
        entry = parse_entry(line, log_format, log_groups, date_format)
        self.assertEqual(entry, {"raw": line, TIMESTAMP: date, MESSAGE: message})


