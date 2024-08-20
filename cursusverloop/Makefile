help:
	@echo ""
	@echo "Options:"
	@echo ""
	@echo "- make help          Show this help"
	@echo ""
	@echo "CODE FORMATTING"
	@echo "- make format        Run all formatters"
	@echo "- make pyupgrade     Run pyupgrade        (upgrades outdated Python code)"
	@echo "- make absolufy      Run absolufy-imports (removes relative import headers)"
	@echo "- make isort         Run isort            (sorts and wraps import headers)"
	@echo "- make black         Run black            (formats code consistently)"
	@echo ""
	@echo "CODE CHECKS"
	@echo "- make checks        Run all code quality checks"
	@echo "- make flake8        Run flake8           (checks code quality)"
	@echo "- make mypy          Run mypy             (checks typing)"
	@echo ""
	@echo "UNIT TESTING"
	@echo "- make test          Run unit tests"
	@echo "- make watch         Run unit tests in a watch loop"
	@echo ""
	@echo "VARIA"
	@echo "- make alltests      Run all tests(unit + integration)"
	@echo ""

checks: flake8 mypy

format: pyupgrade absolufy isort black

test:
	@coverage run -m pytest tests/unit/
	@coverage xml
	@coverage report --show-missing --skip-covered

watch:
	@bin/run_watch_tests

alltests:
	coverage run -m pytest tests/unit tests/integration/
	coverage xml
	coverage report --show-missing --skip-covered

pyupgrade:
	find src/   -type f -name '*.py' -exec pyupgrade --py311-plus {} +
	find tests/ -type f -name '*.py' -exec pyupgrade --py311-plus {} +

absolufy:
	find src/   -type f -name '*.py' -exec absolufy-imports --application-directories src/ {} +
	find tests/ -type f -name '*.py' -exec absolufy-imports --application-directories tests/ {} +

isort:
	isort --profile black src/ tests/

black:
	black src/ tests/

flake8:
	flake8 src/ tests/

mypy:
	mypy src/ tests/

local:
	bin/run_local_deployment

itest:
	bin/run_all_tests_in_docker
