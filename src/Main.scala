package com.afeibaili

import scala.collection.mutable

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello world!")

    val array = new Array[Int](10)
    val newArray = 99 +: array

    //基于简化写法，我们可以这样写
    val newArray1 = 99 +: 99 +: 99 +: array :+ 88 :+ 88 :+ 88

    println(newArray1.mkString("-"))
  }
}

object ArrayBufferTest extends App {
  val tuple: (String, Int, Char, Boolean) = ("string", 1, 'c', true)
}

object ArrayCollection extends App {
  val list: List[Int] = List(1, 2, 3, 5, 2, 9, 2)
  val set: Set[Int] = Set(1, 2, 3)
  val array: Array[Int] = Array(1, 2, 3)
  val map: Map[Int, Int] = Map((1, 1), (2, 2), (3, 3))

  println(list.map(_ * 2))
}

object ListTest extends App {
  val list: List[(String, Int)] = List(("a", 10), ("b", 2), ("c", 3), ("d", 4), ("e", 5), ("f", 6), ("g", 7), ("h", 8))

  println(list.sortWith(_._2 < _._2))
}

object ListFlattenTest extends App {
  val list = List("hello world", "hello Ema", "what do you do", "i want watch")
  println(list.flatMap(_.split(" ")).fold("我是初始值")(_ + _))
}

object MergedMap extends App {
  val map = Map(("a", 1), ("b", 1), ("c", 1))
  val map1 = mutable.Map(("a", 1), ("b", 2), ("c", 3), ("d", 4))

  println(map.foldLeft(map1)((mergedMap, kv) => {
    mergedMap(kv._1) = mergedMap(kv._1) + kv._2
    mergedMap
  }))
}

object WordCount extends App {
  val list = List(("hello scala hbase kafka", 2), ("hello scala hbase", 3), ("hello scala", 2), ("hello", 5))

  //HashMap(kafka -> List((kafka,2)), scala -> List((scala,2), (scala,3), (scala,2)), hello -> List((hello,2), (hello,3), (hello,2), (hello,5)), hbase -> List((hbase,2), (hbase,3)))
  println(list.flatMap(kv => kv._1.split(" ").map((_, kv._2))).groupBy(_._1).map(tuple => (tuple._1, tuple._2.map(_._2).sum)).toList.sortBy(_._2))
}