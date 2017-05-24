#!/bin/bash
cd "$(dirname "$0")"
dropdb -U Reist -w oivt_trml
createdb -U Reist -w oivt_trml
psql -U Reist -w -d oivt_trml -f db_create.sql;
psql -U Reist -w -d oivt_trml -f db_fill.sql 2> deploy_log.txt
