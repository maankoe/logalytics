from typing import AnyStr, Union, List, Callable, Dict, Any
from typing.re import Pattern
import re

from logalytics.model.types import GroupName, EntryItem


class LogSchema:
    def __init__(self,
                 pattern: Union[AnyStr, Pattern],
                 groups: List[GroupName],
                 parsers: Dict[GroupName, Callable[[AnyStr], EntryItem]]):
        self._pattern = re.compile(pattern)
        self._groups = groups
        self._parsers = parsers

    @property
    def pattern(self) -> Union[AnyStr, Pattern]:
        return self._pattern

    @property
    def groups(self) -> List[GroupName]:
        return self._groups

    @property
    def parsers(self) -> Dict[GroupName, Callable[[AnyStr], EntryItem]]:
        return self._parsers
