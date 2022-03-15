package kafkapack
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig, RecordMetadata}
import scala.util.Random
import java.util.Date
import java.util.concurrent.Future
import java.util.Properties
import scala.collection.mutable.HashMap
import contextpack._




object ClickstreamKafkaProducer {

  def producerKafka(args: Array[String]): Unit = {

  ///////if producer streaming does not register data, check dstream path and make sure the files are newly modified/////////////////////
  
  val topic = args(0)
  val brokers = args(1)
  val ip = args(2)
  val port = args(3).toInt

  val ssc = MainContext.getStreamingContext()
  // val dstream = ssc.textFileStream("/home/bryanat/gitclonecleanbigdata/bigdatacapstone/spark-warehouse")
  //Producer team will stream their line by line stream data to socketTextStream("44.195.89.83", 9092)
  val dstream = ssc.socketTextStream(ip, port)
  
  val props = new HashMap[String, Object]()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put("producer.type", "async")
  props.put(ProducerConfig.RETRIES_CONFIG, "3")
  props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")
  props.put(ProducerConfig.ACKS_CONFIG, "all")
  // props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, "60000")
  // props.put(ProducerConfig.BATCH_SIZE_CONFIG, "49152")

  //create an instance of broadcast Kafka producer
  val kafkasink = ssc.sparkContext.broadcast(KafkaSink(props))
  val now = System.currentTimeMillis()
  println(s"(Producer) Current unix time is: $now")
  
  //send the producer message with respect to a particular topic 
  dstream.foreachRDD ({ rdd =>

    rdd.foreachPartition ({ records =>
      
      records.foreach({message => 
        kafkasink.value.send(topic, message)
        println(message)
        // val metadata = kafkasink.value.testsend(topic, message)
        // println(metadata.topic())
        // println(s"Sent to topic $topic: $message")
        //System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - now));
    })
    })
  })
  ssc.start()             // Start the computation
  ssc.awaitTermination()  // Wait for the computation to terminate 
}  
}
