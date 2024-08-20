from domains.canvas.models.announcement import Announcement
from domains.event_bus.services.commands.queue_object import queue_data
from web_app.extensions import announcement_repository, get_uniek_id


def create_announcement(
        name: str, content: str, poster: int, cursus_id: int
        ) -> None:
    announcement = Announcement(
        id=get_uniek_id(),
        name=name,
        content=content,
        comments=[None],
        poster=poster,
        cursus_id=cursus_id
    )
    announcement_repository.add(announcement)
    queue_data(
        {"announcement": {
            "status": "created",
            "id": announcement.id,
            "cursus_id": cursus_id,
            "content": announcement.content,
            }
        }, "announcements"
    )
