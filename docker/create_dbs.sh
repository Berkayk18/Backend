#!/bin/bash
set -e

POSTGRES="psql --username ${POSTGRES_USER}"

# Read the base names from the environment variable, splitting by comma
IFS=',' read -r -a base_names <<< "${DB_BASE_NAMES}"

# Initialize an array to hold all database names
databases=()

# Populate the array with both regular and test database names
for base in "${base_names[@]}"; do
    databases+=("${DB_APP_USER}_${base}")
    databases+=("${DB_APP_USER}_${base}-test")
done

echo "Creating databases..."

# Create each database
for db in "${databases[@]}"
do
    $POSTGRES <<EOSQL
CREATE DATABASE "${db}" OWNER "${DB_APP_USER}";
EOSQL
done

echo "databases created."
