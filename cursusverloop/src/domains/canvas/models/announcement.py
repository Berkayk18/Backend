from dataclasses import dataclass

from dataclasses_json import dataclass_json

from domains.canvas.models.comment import Comment


@dataclass_json
@dataclass(frozen=True, kw_only=True, slots=True)
class Announcement:
    id: int
    cursus_id: int
    name: str
    content: str
    comments: list[Comment] | list[None]
    poster: int
