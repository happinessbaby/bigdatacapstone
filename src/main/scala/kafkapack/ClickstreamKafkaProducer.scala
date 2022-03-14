package kafkapack
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig, RecordMetadata}
import scala.util.Random
import java.util.Date
import java.util.concurrent.Future
import java.util.Properties
import scala.collection.mutable.HashMap
import contextpack._


object ClickstreamKafkaProducer extends App{

  def producerKafka(args: Array[String]): Unit = {
  
  val topic = args(0)
  val brokers = args(1)
  
  // val rnd = new Random()
  // val props = new Properties()
  // props.put("metadata.broker.list", brokers)
  // //props.put("serializer.class", "kafka.serializer.StringEncoder")
  //  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  //  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  // props.put("producer.type", "async")
 

  // Create a DStream that will connect to hostname:port, like localhost:9999
  val ssc = MainContext.getStreamingContext()
  //Producer team will stream their line by line stream data to socketTextStream("ec2-3-81-9-55.compute-1.amazonaws.com", 9092)
  val dstream = ssc.socketTextStream("3.81.9.55", 9092)

   val props = new HashMap[String, Object]()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put("producer.type", "async")
  props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, "30000")
  props.put(ProducerConfig.BATCH_SIZE_CONFIG, "49152")


  //create an instance of broadcast Kafka producer
  val kafkasink = ssc.sparkContext.broadcast(KafkaSink(props))
  val now = System.currentTimeMillis()
  println(s"(Producer) Current unix time is: $now")


  //send the producer message with respect to a particular topic 
  dstream.foreachRDD ({ rdd =>
    println("(Producer) inside rdd is running")
    rdd.foreachPartition ({ records =>
      println("(Producer) inside record partition is running")
      // val metadata: List[Future[RecordMetadata]] = records.map { record => {
      //   kafkasink.value.send(topic, record)
      // }.toList
      //metadata.foreach(metadata=>println(metadata.value())
      

      records.foreach({message => 
      println("(Producer) inside partitioned record is running")
        val metadata = kafkasink.value.testsend(topic, message)
        println(metadata.topic())
        kafkasink.value.send(topic, message)
        println(message)
        //System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - now));
    })
    })
  })
  //val config = new ProducerConfig(props)
  // val producer = new KafkaProducer[String, String](props)
  // val t = System.currentTimeMillis()
  // for (nEvents <- Range(0, events)) {
  //   val runtime = new Date().getTime();
  //   val ip = "192.168.2." + rnd.nextInt(255);
  //   val url = "www.amazon" + rnd.nextInt(255) + ".com";
  //   val msg = runtime + "," + nEvents + "," + url + "," + ip;
  //   val data = new ProducerRecord[String, String](topic, ip, msg);
  //   producer.send(data);
  // }

  // System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - t));
  // producer.close();
}
  
}
