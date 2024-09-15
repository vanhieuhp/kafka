package hieu.nv.kafkacrash;

import lombok.Getter;
import org.apache.kafka.clients.consumer.CooperativeStickyAssignor;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Getter
public class Producer {

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName());
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("partition.assignment.strategy", CooperativeStickyAssignor.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

//        for (int i = 0; i < 5; i++) {
            int i = 12;
            for (int j = 0; j < 10; j++) {
                String topic = "demo_topic";
                final String key = "idss_" + i;
                final String message = "Hello Kafka " + j;
                final int butchNumber = i;
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);

                // Send data
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        // Execute every time a record is successfully sent or an exception is thrown
                        if (e == null) {
                            // The record was successfully sent
                            System.out.println("Received new medata.");
                            System.out.println("Butch number: " + butchNumber);
                            System.out.println("Partition: " + recordMetadata.partition());
                            System.out.println("Offset: " + recordMetadata.offset());
                        } else {
                            System.out.println("Error while producing" + e);
                        }
                    }
                });
            }
            Thread.sleep(5000);
//        }


        // Tell producer to send all data
        // bBlock until complete - synchronous
        producer.flush();

        // Close the producer
        producer.close();

    }
}
