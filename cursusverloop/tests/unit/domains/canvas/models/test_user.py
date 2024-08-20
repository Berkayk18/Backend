from domains.canvas.models.announcement import Announcement
from domains.canvas.models.comment import Comment


def test_announcement_creation() -> None:
    comment = Comment(id=123, name="new comment", content="Hello world 2")
    announcement = Announcement(id=84876768, name="H2B", content="Hello world", comments=[comment])
    assert announcement.id == 84876768
    assert announcement.comments == [comment]
    assert announcement.content == "Hello world"
