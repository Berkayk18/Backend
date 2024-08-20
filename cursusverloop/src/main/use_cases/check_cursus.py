from os import environ

import requests


def check_if_cursus_exists(cursus_id: int) -> bool:
    response = requests.get(f"http://{environ.get("CURSUS_ROUTE")}/course/{cursus_id}/")
    if response.status_code == 404:
        return False
    else:
        return True
