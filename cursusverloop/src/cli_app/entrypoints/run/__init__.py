import click

__all__ = ["run"]

from cli_app.entrypoints.run import webserver


@click.group
def run() -> None:
    """Start a process"""


@run.command(name="webserver")
def run_webserver() -> None:
    """Starts the webserver process"""
    webserver.execute()
