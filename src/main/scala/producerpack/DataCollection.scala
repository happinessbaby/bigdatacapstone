package producerpack

import contextpack.MainContext
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

object DataCollection {

//  System.setProperty("hadoop.home.dir", "c:/winutils")
//  val spark = SparkSession
//    .builder()
//    .appName("project1")
//    .config("spark.master","local")
//    .enableHiveSupport()
//    .getOrCreate()
//  spark.sparkContext.setLogLevel("ERROR")

  //getProductDataList will return the following Rows in a Vector: [product_id, product_name, product_category, price]
  def getProductDataList(spark: SparkSession): Vector[Row] ={
    val df = spark.read.csv("dataset-online/productdata.csv")
    val productList = df.collectAsList()
    val productListScala = productList.asScala.toList
    val returnVector = productListScala.toVector
    returnVector
  }
  //getCityCountryList will return the following Rows in a Vector: [city, country]
  def getCityCountryList(spark: SparkSession): Vector[Row] ={
    val df = spark.read.csv("dataset-online/citylist.csv")
    val productList = df.collectAsList()
    val productListScala = productList.asScala.toList
    val returnVector = productListScala.toVector
    returnVector
  }

  //getWebsiteList will return the following Rows in a Vector: [rank, root domain, linking root domains, domain authority]
  def getWebsiteList(spark: SparkSession): Vector[Row] ={
    val df = spark.read.csv("dataset-online/websites(500).csv")
    val productList = df.collectAsList()
    val productListScala = productList.asScala.toList
    val returnVector = productListScala.toVector
    returnVector
  }

  //getfailReasonsList will return the following Rows in a Vector: [failCode]

  def getfailReasonsList(spark: SparkSession): Vector[Row] ={
    val df = spark.read.csv("dataset-online/failReasons.csv")
    val productList = df.collectAsList()
    val productListScala = productList.asScala.toList
    val returnVector = productListScala.toVector
    returnVector
  }

  //getCustomersList will return the following Rows in a Vector: [customer_id, customer_name]
  def getCustomersList(spark: SparkSession): Vector[Row] ={
    val df = spark.read.csv("dataset-online/custIdNames.csv")
    val productList = df.collectAsList()
    val productListScala = productList.asScala.toList
    val returnVector = productListScala.toVector
    returnVector
  }

  //getComputersList will return a vector with ONLY the rows that contain data about computer sales.
  def getComputersList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Computers"){
          productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getClothingList will return a vector with ONLY the rows that contain data about clothing sales.
  def getClothingList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Clothing"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getHomeGardenList will return a vector with ONLY the rows that contain data about Home & Garden sales.
  def getHomeGardenList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Home & Garden"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getGroceryList will return a vector with ONLY the rows that contain data about Grocery sales.
  def getGroceryList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Grocery"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getSportsList will return a vector with ONLY the rows that contain data about Sports sales.
  def getSportsList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Sports"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getAutomotiveList will return a vector with ONLY the rows that contain data about Automotive sales.
  def getAutomotiveList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Automotive"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getElectronicsList will return a vector with ONLY the rows that contain data about electronics sales.
  def getElectronicsList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Electronics"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getShoesList will return a vector with ONLY the rows that contain data about shoe sales.
  def getShoesList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Shoes"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

  //getBooksList will return a vector with ONLY the rows that contain data about Book sales.
  def getBooksList(spark: SparkSession): Vector[Row] ={
    val productVector = DataCollection.getProductDataList(spark)
    val productListBuffer = ListBuffer[Row]()

    for (i <- 0 until productVector.length-1){
      if (productVector(i).get(2).toString == "Books"){
        productListBuffer += productVector(i)
      }
    }
    val productList = productListBuffer.toList
    val resultVector = productList.toVector
    resultVector
  }

}