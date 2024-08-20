import os
from typing import Any

import pika
from pika.channel import Channel

from domains.event_bus.ports.abstract_queue import AbstractQueue


class RabbitMQClient(AbstractQueue):
    def _create_connection(self) -> pika.BlockingConnection:
        if os.path.exists('/.dockerenv'):
            connection = pika.BlockingConnection(pika.ConnectionParameters("rabbitmq"))
        else:
            connection = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
        return connection

    def declare_fanout_exchange(self, *, queue: str) -> None:
        connection = self._create_connection()
        channel = connection.channel()
        channel.exchange_declare(exchange=queue, exchange_type='fanout')
        connection.close()

    def enqueue_data(self, *, data: str, queue: str) -> None:
        connection = self._create_connection()
        channel = connection.channel()
        channel.exchange_declare(exchange=queue, exchange_type='fanout')
        channel.basic_publish(exchange=queue, routing_key="", body=str(data))
        connection.close()

    def start_queue_handeling(self, queue: str = "default") -> None:
        connection = self._create_connection()
        channel = connection.channel()
        channel.queue_declare(queue=queue)
        channel.basic_consume(
            queue=queue,
            auto_ack=True,
            on_message_callback=self.callback,
            consumer_tag="default",
        )
        channel.start_consuming()

    def callback(self, ch: Channel, method: Any, properties: Any, body: Any) -> None:
        print(f" [x] Recieved {body}")
