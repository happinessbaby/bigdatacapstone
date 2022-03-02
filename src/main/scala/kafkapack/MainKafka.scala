package kafkapack

// Kafka deps
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
// Spark deps
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.sql._

import contextpack._

object MainKafka {
    
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
    _Orderall

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

  val topics = Array("topicA", "topicB")
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