from domains.event_bus.ports.abstract_queue import AbstractQueue


def start_queue_processing(queue: str, queue_client: AbstractQueue) -> None:
    queue_client.start_queue_handeling(queue=queue)
