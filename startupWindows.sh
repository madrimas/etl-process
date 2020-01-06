#!/bin/bash
 
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
 
mongo-express -u dbuser -p dbpass -d dbname -H host -P port &
java -jar "$DIR/etl-extract.jar" &
java -jar "$DIR/etl-process-transform.jar" &
java -jar "$DIR/etl-load.jar"