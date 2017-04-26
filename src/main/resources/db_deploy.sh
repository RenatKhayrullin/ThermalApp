#!/bin/bash
cd "$(dirname "$0")"
dropdb ont
createdb ont
psql -d ont -f db_create.sql;
psql -d ont -f db_fill.sql 2> gg.txt
