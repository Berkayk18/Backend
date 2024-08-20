import uuid

import pytest

from domains.canvas.models.announcement import Announcement


def test_announcement_creation() -> None:
    announcement = Announcement(id=123546548, name="hello", content="hello world!", comments=[None])
    assert announcement.id == 123546548
    assert announcement.name == "hello"
    assert announcement.content == "hello world!"
    assert announcement.comments == [None]
