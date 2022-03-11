package producerpack

import org.apache.spark.sql.SparkSession
import scala.collection.mutable.ListBuffer

// Trend One will show a larger amount of online grocery orders from North America than any other country.
// Our return string will be in the following format:
// "order_id,customer_id,customer_name,product_id,product_name,product_category,payment_type,qty,price,datetime,country,city,website,pay_id,success"

object trend1 {

  val trendTag = "TR1"
  val rs = new RandomSelections
  val trans = new Transactions

  //here we will inject our randomly created string with information that we need to create a trend
  //in this case, every third transaction will be updated to contain 'Crypto' as payment type and 'United States' as the country
  // We receive a comma separated string, split it by ",", use the array to create a new string, and return.
  /* 0- Order ID
  /  1 - Customer ID
  /  2 - Customer Name
  /  3 - Product ID
  /  4 - Product Name
  /  5 - Product Category
  /  6 - Payment Type
  /  7 - QTY
  /  8 - Price
  /  9 - Datetime
  /  10 - Country
  /  11 - City
  /  12 - Website
  /  13 - Transaction ID
  /  14 - Transaction Success
  /  15 - Transaction fail reason
   */

  def manipulateTransactionTrend1(inputTransaction: String): String = {
    val splitT = inputTransaction.split(",")
    var resultString = ""
    resultString = splitT(0) + "," + splitT(1) + "," + splitT(2) + "," + splitT(3) + "," + splitT(4) + "," + splitT(5) + "," + "Crypto" + "," +
      splitT(7) + "," + splitT(8) + "," + splitT(9) + "," + "United States" + "," + splitT(11) + "," + splitT(12) + ","+ splitT(13) + "," + splitT(14) + "," + splitT(15)
    resultString
  }

<<<<<<< HEAD

  /*def main(args: Array[String]): Unit = {
=======
  //This is the main driver of Trend1 that will return a vector of transaction strings.
  // For this trend I only want to look at grocery orders, so I createInitalTransactions using only 'Grocery'
  // the counter is integrated to ensure that I will have enough data entry points for Crypto/US to show a clear trend.
  def getTrend1(spark: SparkSession, returnAmount: Int): Vector[String]={
>>>>>>> producer/cameron
    var orderCounter = 100000
    var orderID = trendTag+orderCounter.toString
    var resultList = ListBuffer("")
    for (i <- 0 to returnAmount){
      val tempString = trans.createInitialTransaction(rs, spark, orderID,"Grocery")
      resultList += tempString
      orderCounter = orderCounter+1
      orderID = trendTag+orderCounter.toString
    }
<<<<<<< HEAD











    // ALL OF THE COMMENTED BELOW IS JUST FOR TESTING DIFFERENT METHODS OF DataCollection AND RandomSelections
//    val test = dc.getGroceryList(spark)
//    val test2 = dc.getSportsList(spark)
//    test.foreach(println)
//    test2.foreach(println)
//    val test3 = dc.filterByPriceAbove(spark, 1000.00)
//    test3.foreach(println)
//    println()
//    val test4 = dc.filterByPriceBelow(spark, 500)
//    test4.foreach(println)
//    val test4 = dc.filterByPriceBelow(spark, 500)
//    test4.foreach(println)
//    println(rs.getRandomCustomerID(spark))
//    println(rs.getRandomCustomerID(spark))
//    println(rs.getRandomCustomerID(spark))
//    println(rs.getRandomWebsite(spark))
//    println(rs.getRandomWebsite(spark))
//    println(rs.getRandomWebsite(spark))
//    println(rs.getRandomProduct(spark))
//    println(rs.getRandomProduct(spark))
//    println(rs.getRandomProduct(spark))
//    println(rs.getRandomCategory(spark))
//    println(rs.getRandomCategory(spark))
//    println(rs.getRandomCategory(spark))

//    println(customerVector(49))
//    println(customerVector(49).get(1))
  }*/
=======
    for (i <- 0 to returnAmount*3) {
      val tempString = trans.createInitialTransaction(rs, spark, orderID,"Grocery")
      val resultString = manipulateTransactionTrend1(tempString)
      orderCounter = orderCounter+1
      orderID = trendTag+orderCounter.toString
      resultList += resultString
    }
    val resultVector = resultList.toVector
    resultVector
  }
>>>>>>> producer/cameron
}

