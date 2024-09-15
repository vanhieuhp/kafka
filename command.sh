#!/bin/bash

# Variables
KAFKA_BIN_DIR="/opt/kafka/bin"
TOPIC_NAME="demo-topic"
PARTITIONS=1
REPLICATION_FACTOR=1

# Create Kafka topic
$KAFKA_BIN_DIR/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor $REPLICATION_FACTOR --partitions $PARTITIONS --topic $TOPIC_NAME

echo "Topic '$TOPIC_NAME' created successfully."

# Configuration
kafka-topics --bootstrap-server localhost:9092 --topic my-topic --describe

kafka-configs --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic --describe

kafka-configs --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic --alter --add-config min.insync.replicas=2

kafka-configs --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic --alter --delete-config min.insync.replicas

# Producer
kafka-console-producer --bootstrap-server localhost:9092 --topic my-topic
kafka-console-producer --bootstrap-server localhost:9092 --topic my-topic --producer-property acks=all

kafka-console-producer --bootstrap-server localhost:9092 --topic my-topic --property parse.key=true --property key.separator=:

# Topics
kafka-topics --create --bootstrap-server localhost:9092 --topic my-topic --replication-factor 1 --partitions 3

kafka-topics --create --bootstrap-server localhost:9092 --topic my-topic --replication-factor 1 --partitions 1

kafka-topics --create --bootstrap-server localhost:9092 --topic my-topic --replication-factor 1 --partition 3

kafka-topics --delete --bootstrap-server localhost:9092 --topic my-topic

kafka-topics --list --bootstrap-server localhost:9092

# Consumer
kafka-console-consumer --bootstrap-server localhost:9092 --topic my-topic
kafka-console-consumer --bootstrap-server localhost:9092 --topic my-topic --from-beginning
kafka-console-consumer --bootstrap-server localhost:9092 --topic my-topic --from-beginning --group my-group
kafka-console-consumer --bootstrap-server localhost:9092 --topic my-topic \
--formatter kafka.tools.DefaultMessageFormatter \
--property print.timestamp=true \
--property print.key=true \
--property print.value=true \
--property print.partition=true \
--from-beginning

# Consumer Groups
kafka-consumer-groups --bootstrap-server localhost:9092 --list
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group group-name
kafka-consumer-groups --bootstrap-server localhost:9092 --delete --group group-name
kafka-consumer-groups --bootstrap-server localhost:9092 --group group-name --reset-offsets --to-earliest --topic my-topic --dry-run
kafka-consumer-groups --bootstrap-server localhost:9092 --group group-name --reset-offsets --to-earliest --topic my-topic --execute