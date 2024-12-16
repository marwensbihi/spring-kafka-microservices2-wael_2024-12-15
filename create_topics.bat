@echo off
REM Set Kafka broker addresses (use the correct ports)
set KAFKA_BROKER_1=localhost:29092
set KAFKA_BROKER_2=localhost:39092

REM Set the topic names
set TOPIC_2=payment-events
set TOPIC_3=stock-events

REM Set the correct path to kafka-topics.sh inside the container
set KAFKA_TOPICS_SCRIPT=/usr/bin/kafka-topics

REM Create topics on Kafka Broker 1
echo Creating topics on Kafka Broker 1...
docker exec -it kafka-1 %KAFKA_TOPICS_SCRIPT% --create --topic %TOPIC_2% --bootstrap-server %KAFKA_BROKER_1% --partitions 1 --replication-factor 1
docker exec -it kafka-1 %KAFKA_TOPICS_SCRIPT% --create --topic %TOPIC_3% --bootstrap-server %KAFKA_BROKER_1% --partitions 1 --replication-factor 1

REM Create topics on Kafka Broker 2
echo Creating topics on Kafka Broker 2...
docker exec -it kafka-2 %KAFKA_TOPICS_SCRIPT% --create --topic %TOPIC_2% --bootstrap-server %KAFKA_BROKER_2% --partitions 1 --replication-factor 1
docker exec -it kafka-2 %KAFKA_TOPICS_SCRIPT% --create --topic %TOPIC_3% --bootstrap-server %KAFKA_BROKER_2% --partitions 1 --replication-factor 1

echo Topics created successfully on both Kafka brokers.
pause
