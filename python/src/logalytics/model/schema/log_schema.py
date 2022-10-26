from typing import Union, List, Dict
from typing.re import Pattern
import re

from logalytics.model.types import GroupName, EntryItemParser


class LogSchema:
    def __init__(self,
                 pattern: Union[GroupName, Pattern],
                 groups: List[GroupName],
                 parsers: Dict[GroupName, EntryItemParser]):
        self._pattern = re.compile(pattern)
        self._groups = groups
        self._parsers = parsers

    @property
    def pattern(self) -> Union[GroupName, Pattern]:
        return self._pattern

    @property
    def groups(self) -> List[GroupName]:
        return self._groups

    @property
    def parsers(self) -> Dict[GroupName, EntryItemParser]:
        return self._parsers
