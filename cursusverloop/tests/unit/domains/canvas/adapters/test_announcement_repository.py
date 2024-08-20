from domains.canvas.adapters.fake_announcement_repository import FakeAnnouncementRepository
from domains.canvas.models.announcement import Announcement
from domains.canvas.ports.abstract_announcement_repository import AbstractAnnouncementRepository


class TestAnnouncementRepository:
    repo = FakeAnnouncementRepository()
    announcement = Announcement(id=21315454, name="test", content="hello world", comments=[None])

    def test_organization_repo_creation(self) -> None:
        assert isinstance(self.repo, AbstractAnnouncementRepository)

    def test_add_organization_to_repo(self) -> None:
        self.repo.add(self.announcement)
        assert self.repo.get(self.announcement.id) == self.announcement

    def test_get_organization_from_repo(self) -> None:
        assert self.repo.get(self.announcement.id) == self.announcement
        assert isinstance(self.repo.get(self.announcement.id), Announcement)

    def test_delete_organization_from_repo(self) -> None:
        self.repo.delete(self.announcement.id)
        assert self.repo.get(self.announcement.id) is None

    def test_update_organization_in_repo(self) -> None:
        self.repo.add(self.announcement)
        assert self.repo.get(self.announcement.id) == self.announcement
        organization_updated = Announcement(id=self.announcement.id, name="test_update", content="hello world", comments=[None])
        self.repo.add(organization_updated)
        returned_organization = self.repo.get(self.announcement.id)
        assert returned_organization is not None
        assert returned_organization.name == "test_update"

    def test_add_existing_organization_to_repo(self) -> None:
        self.repo.add(self.announcement)
        assert self.repo.get(self.announcement.id) == self.announcement
        organization_to_add = Announcement(id=self.announcement.id, name="test_update_on_add", content="hello world", comments=[None])
        self.repo.add(organization_to_add)
        returned_organization = self.repo.get(self.announcement.id)
        assert returned_organization is not None
        assert returned_organization.name == "test_update_on_add"
