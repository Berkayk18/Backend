from abc import ABC, abstractmethod

from domains.canvas.models.announcement import Announcement


class AbstractAnnouncementRepository(ABC):
    @abstractmethod
    def add(self, announcement: Announcement) -> None:
        raise NotImplementedError

    @abstractmethod
    def get(self, announcement_id: int) -> Announcement | None:
        raise NotImplementedError

    @abstractmethod
    def delete(self, announcement_id: int) -> None:
        raise NotImplementedError

    @abstractmethod
    def get_by_cursus_id(self, cursus_id: int) -> list[Announcement] | list[None]:
        raise NotImplementedError
