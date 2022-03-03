package kafkapack

import contextpack._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata, ProducerConfig}
import java.util.concurrent.Future
import java.util.Properties

object TestProducer {


var ssc = MainContext.getStreamingContext()


def setProducer(): Unit = {
  val props = new Properties();
  props.put("bootstrap.servers", "localhost:9092");
  props.put("acks", "all");
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
 
  val producer = new KafkaProducer[String, String](props);
  val dstream = ssc.textFileStream("file:///home/bryanat/bigdatacapstone/dataset-online/dstreams")

dstream.foreachRDD(rdd => {
    rdd.foreachPartition {partitions =>
        val producer: KafkaProducer[String, String] = new KafkaProducer[String, String](props)
        partitions.foreach((line: String) => {
        try {
        producer.send(new ProducerRecord[String, String]("testtopic", line))
        println("inside producer send")
        } catch {
        case ex: Exception => {
            println("didn't suceed")
        }
    }
})
producer.close()
    }
})
}

// def send(topic: String, key: K, value: V): Future[RecordMetadata] =
//     producer.send(new ProducerRecord[K, V](topic, key, value))
//   for(int i = 0; i < 100; i++)
//       producer.send(new ProducerRecord<String, String>("my-topic", Integer.toString(i), Integer.toString(i)));
 
//    producer.close();

//     private val props = new Properties()

//   props.put("compression.codec", DefaultCompressionCodec.codec.toString)
//   props.put("producer.type", "sync")
//   props.put("metadata.broker.list", brokerList)
//   props.put("message.send.max.retries", "5")
//   props.put("request.required.acks", "-1")
//   props.put("serializer.class", "kafka.serializer.StringEncoder")
//   props.put("client.id", UUID.randomUUID().toString())

  
}
