#!/bin/bash

APP_NAME="journal"
LOG_FILE="log"

start() {
    echo "Starting the application..."
    nohup mvn spring-boot:run > "$LOG_FILE" 2>&1 &
    echo "Application started with PID $!"
}

stop() {
    echo "Stopping the application..."
    pkill -f "$APP_NAME"
    echo "Application stopped."
}

restart() {
    echo "Restarting the application..."
    stop
    sleep 2
    start
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    *)
        echo "Usage: $0 {start|stop|restart}"
        exit 1
        ;;
esac
