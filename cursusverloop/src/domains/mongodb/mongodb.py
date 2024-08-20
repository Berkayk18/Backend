import os

from pymongo import MongoClient


def get_database() -> MongoClient:
    if os.path.exists('/.dockerenv'):
        CONNECTION_STRING = "mongodb://host.docker.internal:27017"
    else:
        CONNECTION_STRING = "mongodb://localhost:27017"

    client = MongoClient(CONNECTION_STRING)
    return client['commentable']
