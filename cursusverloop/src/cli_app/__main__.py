""""This module implements the CLI for this application using Click"""

import importlib
import pkgutil
from collections.abc import Iterator
from pathlib import Path

import click


@click.group
@click.version_option("0.0.1", prog_name="H2B portal 2")
def cli() -> None:  # pragma: no cover
    """Start CLI command"""
    pass


entrypoints_path: str = str(Path(__file__).parent.joinpath("entrypoints"))
modules: Iterator[pkgutil.ModuleInfo] = pkgutil.iter_modules([entrypoints_path])
for module_info in modules:
    module = importlib.import_module(f"{__package__}.entrypoints.{module_info.name}")
    attributes = module.__all__ if hasattr(module, "__all__") else dir(module)
    for attribute in attributes:
        obj = getattr(module, attribute)
        if isinstance(obj, click.Group) or isinstance(obj, click.Command):
            cli.add_command(obj)

if __name__ == "__main__":  # pragma: no cover
    cli()
