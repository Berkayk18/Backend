from flask import Blueprint, Response, jsonify, request

from main.use_cases.check_cursus import check_if_cursus_exists
from main.use_cases.check_poster import check_if_poster_exists
from main.use_cases.create_announcement import create_announcement
from main.use_cases.delete_announcement import remove_announcement
from main.use_cases.get_announcement import retrieve_announcement, retrieve_announcement_by_cursus
from web_app.extensions import announcement_repository

bp = Blueprint("announcement", __name__)


@bp.get("/")
def get_announcement() -> tuple[Response, int]:
    announcement_id = request.args.get('id')
    if not announcement_id:
        return jsonify("Missing announcement id"), 422
    announcement = retrieve_announcement(
        announcement_repository=announcement_repository,
        announcement_id=int(announcement_id)
    )
    return jsonify(announcement), 200

@bp.get("/all")
def get_announcement_by_cursus_id() -> tuple[Response, int]:
    cursus_id = request.args.get('id')
    if not cursus_id:
        return jsonify("Missing announcement id"), 422
    announcement = retrieve_announcement_by_cursus(
        announcement_repository=announcement_repository,
        cursus_id=cursus_id
    )
    return jsonify(announcement), 200


@bp.post("/")
def post_announcement() -> tuple[Response, int]:
    data = request.json
    content = data['content']
    content_title = data['content_title']
    poster = data['poster']
    cursus_id = data['cursus_id']
    if not content:
        return jsonify("Missing content string"), 422
    if not poster:
        return jsonify("Missing poster id"), 422
    if not cursus_id:
        return jsonify("Missing cursus_id"), 422
    if not content_title:
        return jsonify("Missing content_tile"), 422
    # if not check_if_poster_exists(poster):
    #     return jsonify("Unkown poster id"), 404
    # if not check_if_cursus_exists(cursus_id):
    #     return jsonify("Unkown cursus id"), 404
    create_announcement(
        name=content_title,
        content=content,
        cursus_id=cursus_id,
        poster=poster)
    return jsonify("Created announcement"), 200

@bp.delete("/")
def delete_announcement() -> int | tuple[Response, int]:
    announcement_id = request.args.get('id')
    if not announcement_id:
        return jsonify("Missing announcement id"), 422
    remove_announcement(announcement_id)
    return 200
