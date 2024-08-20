from dataclasses import dataclass


@dataclass(frozen=True, kw_only=True, slots=True)
class Comment:
    id: int
    name: str
    content: str
