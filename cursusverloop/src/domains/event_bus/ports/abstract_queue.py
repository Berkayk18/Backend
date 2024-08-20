from abc import ABC, abstractmethod


class AbstractQueue(ABC):
    @abstractmethod
    def enqueue_data(self, *, data: str, queue: str) -> None:
        """Put data into a queue"""

    @abstractmethod
    def start_queue_handeling(self, queue: str) -> None:
        """Starts the handeling of any queue entriess"""
