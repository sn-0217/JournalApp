#!/bin/bash

kill -9 $(pgrep -f journal)

mvn spring-boot:run >> log 2>&1 &
