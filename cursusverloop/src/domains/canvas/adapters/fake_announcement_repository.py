from collections.abc import MutableMapping

from domains.canvas.models.announcement import Announcement
from domains.canvas.ports.abstract_announcement_repository import AbstractAnnouncementRepository


class FakeAnnouncementRepository(AbstractAnnouncementRepository):
    def __init__(self, announcement_store: MutableMapping[int, Announcement] = {}) -> None:
        super().__init__()
        self.announcement_store: MutableMapping[int, Announcement] = announcement_store

    def add(self, announcement: Announcement) -> None:
        if self.announcement_store.get(announcement.id):
            self.announcement_store.update({announcement.id: announcement})
        else:
            self.announcement_store[announcement.id] = announcement

    def get(self, announcement_id: int) -> Announcement | None:
        return self.announcement_store.get(announcement_id, None)

    def delete(self, announcement_id: int) -> None:
        self.announcement_store.pop(announcement_id)

    def get_by_cursus_id(self, cursus_id: int) -> list[Announcement] | list[None]:
        announcements = []
        for i in self.announcement_store.keys():
            announcement = self.announcement_store.get(i)
            if announcement and announcement.cursus_id == cursus_id:
                announcements.append(announcement)
        return announcements
