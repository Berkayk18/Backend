from pymongo import MongoClient
from pymongo.collection import Collection

from domains.canvas.models.announcement import Announcement
from domains.canvas.ports.abstract_announcement_repository import AbstractAnnouncementRepository
from domains.mongodb.mongodb import get_database


class MongodbAnnouncementRepository(AbstractAnnouncementRepository):
    mongodb_client: MongoClient = get_database()

    def add(self, announcement: Announcement) -> None:
        collection: Collection = self.mongodb_client["course_announcements"]
        collection.create_index("id", unique=True)
        collection.create_index("cursus_id")
        data = announcement.to_dict()
        collection.insert_one(data)

    def get(self, announcement_id: int) -> Announcement | None:
        collection: Collection = self.mongodb_client["course_announcements"]
        data = collection.find_one({'id': int(announcement_id)})
        return Announcement.from_dict(data)

    def delete(self, announcement_id: int) -> None:
        collection: Collection = self.mongodb_client["course_announcements"]
        collection.find_one_and_delete({'id': int(announcement_id)})

    def get_by_cursus_id(self, cursus_id: int) -> list[Announcement] | list[None]:
        collection: Collection = self.mongodb_client["course_announcements"]
        data = []
        for x in collection.find({'cursus_id': int(cursus_id)}, {"_id": 0}):
            announcement = Announcement.from_dict(x)
            data.append(announcement)
        return data
