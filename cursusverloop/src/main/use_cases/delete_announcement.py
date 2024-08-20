from domains.event_bus.services.commands.queue_object import queue_data
from web_app.extensions import announcement_repository


def remove_announcement(
        id: int
        ) -> None:
    announcement = announcement_repository.get(id)
    announcement_repository.delete(id)
    queue_data(
        {
            "announcement": {
                "status": "deleted",
                "id": announcement.id,
                "cursus_id": announcement.cursus_id
            }
        },
        "announcements"
    )
