from domains.canvas.ports.abstract_announcement_repository import AbstractAnnouncementRepository
from src.domains.canvas.models.announcement import Announcement


def retrieve_announcement(
        announcement_repository: AbstractAnnouncementRepository, announcement_id: int
        ) -> Announcement | None:
    return announcement_repository.get(announcement_id=announcement_id)


def retrieve_announcement_by_cursus(
    announcement_repository: AbstractAnnouncementRepository, cursus_id: int
        ) -> Announcement | None:
    return announcement_repository.get_by_cursus_id(cursus_id=cursus_id)
