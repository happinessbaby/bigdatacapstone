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
  def manipulateTransactionTrend1(inputTransaction: String): String = {
    val splitT = inputTransaction.split(",")
    var resultString = ""
    resultString = splitT(0) + "," + splitT(1) + "," + splitT(2) + "," + splitT(3) + "," + splitT(4) + "," + splitT(5) + "," + splitT(6) + "," +
      "Crypto" + "," + splitT(8) + "," + splitT(9) + "," + splitT(10) + "," + "United States" + "," + splitT(12) + ","+ splitT(13) + "," + splitT(14) + "," + splitT(15)
    return resultString
    resultString = inputTransaction
    resultString
  }

  //This is the main driver of Trend1 that will return a vector of transaction strings.
  // For this trend I only want to look at grocery orders, so I createInitalTransactions using only 'Grocery'
  // the counter is integrated to ensure that I will have enough data entry points for Crypto/US to show a clear trend.
  def getTrend1(spark: SparkSession, returnAmount: Int): Vector[String]={
    var orderCounter = 100000
    var orderID = trendTag+orderCounter.toString
    var repeatCounter = 1
    var resultList = ListBuffer("")
    for (i <- 0 to returnAmount){
      val tempString = trans.createInitialTransaction(rs, spark, orderID,"Grocery")
      resultList += tempString
      orderCounter = orderCounter+1
      orderID = trendTag+orderCounter.toString
    }
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
}
