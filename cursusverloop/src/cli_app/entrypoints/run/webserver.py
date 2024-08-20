from web_app.app import create_app


def execute() -> None:
    app = create_app()
    app.run(
        host="0.0.0.0",
        port=8083,
        debug=True,
        load_dotenv=True,
    )
