package producerpack

import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer
import contextpack.MainContext

import java.net._
import java.io._
import java.util.Random

import socketpack.SocketWorkingServerpart

//import socketpack.SocketWorking

class TrendThread(data:Vector[String]) extends Runnable{
  println("kkk")
  val dat = data
  println("qqq")
  println("mmm")
  // this .accept() method is key
  //var clientSocketRaw = new SocketWorking()

  // var newSocketWorking = new SocketWorking()
  // newSocketWorking.start()

  // var clientSocket = clientSocketRaw.serverSocket.accept()
  println("rrr")
  // var out = new PrintWriter(clientSocket.getOutputStream(), true)
  println("www")
  // var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
  println("xxx")

  // while (true) {
  //   var randomnum = new Random()
  //   var randomnumstring = randomnum.toString()
  //   println("looping... " + randomnumstring)
  //   //////////////////////////////
  //   out.println("XPHEOXXNAJSNAINSDI")
  //   Thread.sleep(300)
  // }

  override def run(): Unit = {
    var clientSocket = SocketWorkingServerpart.serverSocket.accept()
    var out = new PrintWriter(clientSocket.getOutputStream(), true)
    var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    // dat.foreach(p => println(p))
    dat.foreach(p => {
      out.println(p)
      println(p)
      //MainSocketClient.sendOverSocket(p)
      // println(p)
      // // outC.println(p); 
      // // outS.println(p); 
      // var randomnum = new Random()
      // var randomnumstring = randomnum.toString()
      // println("looping... " + randomnumstring)
      Thread.sleep(300)
    })
  println("zzz")

  }
}

class TrendMaker {
  //this value stores the maximum number of threads we want running at once
  println("aaa")
  val threadCount = 3
  //stores the threads that arent running
  var waitingThreads = Stack[Thread]()
  //stores the running threads
  var runningThreads = ListBuffer[Thread]()
  println("bbb")
  //first im just gonna test that my thread is working
  def loadData(data:Vector[String]):Unit= {
    //this adds the data in the thread to waiting threads
    waitingThreads.push(new Thread(new TrendThread(data)))
  println("ccc")
  }

  def startTreads():Unit= {
  println("x1")
    //this starts the threads by first running through the currently running threads adn removing the dead ones.
    Thread.sleep(1000)
    println("x2")
    while (runningThreads.length > 0){
      //if there is a running thread, then we want toi leave it alone. otherwise, we need to stop it
      //this can be removed
      if(!runningThreads(runningThreads.length-1).isAlive){
      println("x4")
        runningThreads.remove(runningThreads.length-1)
      println("x5")
      }
    }
  println("x7")
    //add the new threads to running threads
    while(waitingThreads.nonEmpty && runningThreads.length < threadCount){
      println("x8")
      runningThreads += waitingThreads.pop()
      println("x9")
    }
    println("x10")
    runningThreads.foreach(p => {if (!p.isAlive) p.start()})
    println("x11")
  }
  println("x12")

}
