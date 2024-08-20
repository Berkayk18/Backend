from os import environ

import requests


def check_if_poster_exists(poster_id: int) -> bool:
    params = {"userId": poster_id}
    response = requests.get(f"http://{environ.get("COURSE_PLANNNING_ROUTE")}/user/", params=params)
    if response.status_code == 404:
        return False
    else:
        return True
