from typing import List, AnyStr, Any, Callable

EntryItem = Any
LogGroups = List[AnyStr]
GroupName = AnyStr
EntryItemParser = Callable[[AnyStr], EntryItem]