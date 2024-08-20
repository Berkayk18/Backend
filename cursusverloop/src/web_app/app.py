import os

from dotenv import load_dotenv
from flask import Flask

from web_app.blueprints import announcement
from web_app.extensions import rabbitmq


def create_app() -> Flask:
    app = Flask(__name__)
    load_dotenv()
    app.config.update(
        {
            "SECRET_KEY": os.environ.get("SECRET_KEY")
        }
    )
    rabbitmq.declare_fanout_exchange(queue="announcements")
    app.register_blueprint(announcement.bp, url_prefix="/announcement")
    return app
