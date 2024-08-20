from typing import Any

from pytest import fixture

from web_app.app import create_app


@fixture
def _app() -> Any:
    app = create_app()
    with app.test_request_context():
        yield app


@fixture
def client(_app: Any) -> Any:
    """Create a test client for the Flask application."""
    return _app.test_client()
