#!/bin/bash

# Kafka containers
KAFKA_CONTAINERS=("kafka-1" "kafka-2")

# Topics to create
TOPICS=("payment-events" "stock-events")

# Kafka CLI location
KAFKA_CLI="/bin/kafka-topics"

# Function to create topics if they don't exist
create_topic_if_not_exists() {
    local topic=$1
    for container in "${KAFKA_CONTAINERS[@]}"; do
        # Check if topic exists; if not, create it
        if ! docker exec -u root $container $KAFKA_CLI --list --bootstrap-server localhost:29092 | grep -q "^$topic$"; then
            echo "Creating topic: $topic on $container"
            docker exec -u root $container $KAFKA_CLI --create --topic $topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:29092
        else
            echo "Topic $topic already exists on $container"
        fi
    done
}

# Loop through the topics and create them if they don't exist
for topic in "${TOPICS[@]}"; do
    create_topic_if_not_exists $topic
done

# Optionally: List topics to verify creation on both Kafka containers
for container in "${KAFKA_CONTAINERS[@]}"; do
    echo "Listing topics on $container:"
    docker exec -u root $container $KAFKA_CLI --list --bootstrap-server localhost:29092
done

