import json
from typing import Any


def test_get_announcement(client: Any) -> None:
    response = client.get("/announcement")
    assert response.status_code == 200
