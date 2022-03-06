package kafkapack

// Kafka deps
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


// Spark deps
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql._

import contextpack._

object MainTopics {
    
  def startMainKafka(): Unit = {
    println("Main Kafka started...")

    val sconf = new SparkConf().setMaster("local[*]").setAppName("P3").setSparkHome("C:\\Spark")
    val sc = new SparkContext(sconf)

    val ssc  = new StreamingContext(sc, Seconds(2))

    val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092,anotherhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "use_a_separate_group_id_for_each_stream",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  /*
    ==================================
    topic hierarchy
    ==================================

    Super Topic:
    _orderall

    3 Main topics:
    Customer
    Product
    Payment

    Customer
      order_id
      customer_id
      customer_name
      country
      city
      datetime

    Product
      order_id
      product_id
      product_name
      product_category
      datetime
    
    Payment
      order_id
      payment_type
      payment_txn_id
      payment_txn_success
      price
      qty
      failure_reason
      datetime
  */

  /*
    ==================================
    Topic Creation
    (Transposition of above)
    ==================================
    ----------------------------------
    Example Syntax in console:
    kafka.topics.sh --bootstrap-server 127.0.0.1:9092 --topic _orderall --create

    Breakdown of steps:
    1. calls topics .sh (equivelent to .bat in Windows)
    2. connects to bootstrap server ip 127.0.0.1 at port 9092
    3. defines topic '_orderall'
    4. creates topic by calling '--create' command
    ----------------------------------
    //lists all the topics - handy for debugging
    kafka.topics.sh --bootstrap-server 127.0.0.1:9092 --list
    ----------------------------------
    !Mock topic creation:
    
    //Can there be subtopics and are they partitions?
    //source of question https://hevodata.com/learn/kafka-topic/#42
    Main topics:

    bin/kafka-topics.sh --create --zookeeper localhost:9092 \
      --replication-factor 1 --partitions 1 \
      --topic _orderall

    bin/kafka-topics.sh --create --zookeeper localhost:9092 \
      --replication-factor 1 --partitions 1 \
      --topic Consumer

    bin/kafka-topics.sh --create --zookeeper localhost:9092 \
      --replication-factor 1 --partitions 1 \
      --topic Product

    bin/kafka-topics.sh --create --zookeeper localhost:9092 \
      --replication-factor 1 --partitions 1 \
      --topic Payment
    ----------------------------------
  */
  
  //Declare Main topics
  val topics = Array("_orderall", "Consumer", "Product", "Payment")

  //two approaches to subtopics: create additional array or integrate the data into partitions of main topics
  //below is what the arrray approach may look like
  //List for regex - has all of the data columns for regex to refer to
  //purpose for this is to ennsure match of columns

  val regexParseColumns = List("order_id", "customer_id",
      "customer_name", "country", "city",
      "datetime", "order_id", "product_id", 
      "product_name", "product_category", "datetime", 
      "order_id", "payment_type", "payment_txn_id", 
      "payment_txn_success", "price", "qty", 
      "failure_reason", "datetime")
  
  //addition of columns to filter out bad data
  //Regex for Uppercase-Lowercase Letters ([A-Z][a-z]+) Capital Case
  // chop up line get every character between column - every 7th column
  // assume start from string
  // line -> trim 
  // val captialCaseReg = "([A-Z][a-z]+)".r

  //  start regex - working template
  /*
  var filename = "dataset-online\\citylist.csv" 

  for (line <- Source.fromFile(filename).getLines) { 
        /*
         * EACH LOOP IS A LINE IN THE IMPORTED FILE
         */
        val regexComma = "(?!,)[^,]*".r // split by comma

        /*
         * ListOps in constant O(1) time, .prepend (set) is constant, head and tail (get) is constant 
         */
        val listOfValues = regexComma.findAllMatchIn(line).toList
        
        // if (!= "([A-Z][a-z]+)".r){

        // }
      }
  */

  val stream = KafkaUtils.createDirectStream[String, String](
    // StreamingContext below, get current running StreamingContext imported from context package
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  stream.foreachRDD { rdd =>
  val offsetRanges = rdd.collect().mkString(",")
  println(offsetRanges)

  // begin your transaction

  // update results
  // update offsets where the end of existing offsets matches the beginning of this batch of offsets
  // assert that offsets were updated correctly

  // end your transaction
  }

  ssc.start()             // Start the computation
  ssc.awaitTermination()  // Wait for the computation to terminate




  //stream.map(record => (record.key, record.value))
  
  }
}


//consumer?
//val stream = KafkaUtils.createStream(ssc, "localhost:9092", "spark-streaming-consumer-group", Map("test" -> 1))