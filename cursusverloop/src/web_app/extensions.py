from domains.canvas.adapters.mongodb_announcement_repository import MongodbAnnouncementRepository
from domains.event_bus.adapters.rabbitmq_client import RabbitMQClient

announcement_repository = MongodbAnnouncementRepository()
id: int = 0
rabbitmq = RabbitMQClient()

def get_uniek_id() -> int:
    global id
    id = id + 1
    return id + 1
