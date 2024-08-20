from typing import Any

from web_app.extensions import rabbitmq


def queue_data(data: dict[Any, Any], queue_target: str) -> None:
    rabbitmq.enqueue_data(data=str(data), queue=queue_target)
