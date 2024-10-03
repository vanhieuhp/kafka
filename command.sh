KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties
bin/kafka-server-start.sh config/kraft/server.properties

Step 3: Create a topic to store your events
bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092

bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic msql.history

Step 4: Write some events into the topic
bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092

Step 5: Read the events
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092

Step 6: Import/export your data as streams of events with Kafka Connect
echo "plugin.path=libs/connect-file-3.8.0.jar" >> config/connect-standalone.properties

bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties

## Kafka connect
curl -H "Accept:application/json" localhost:8083/connectors/

bin/connect-standalone.sh config/connect-standalone.properties config/connect-debezium-mysql.properties

docker run -it --rm --name watcher --link zookeeper:zookeeper --link kafka:kafka quay.io/debezium/kafka:2.7 watch-topic -a -k dbserver1.inventory.customers

bin/kafka-console-consumer.sh --topic mongodb.test.users --bootstrap-server localhost:9092