package com.github.simonedeponti.maprstreamprinter

import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}

import scala.collection.JavaConverters._


object MaprStreamPrinter {

  def makeConsumer(args: Seq[String]): KafkaConsumer[String, String] = {
    val props: Properties = new Properties()
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](props)
    val topics: Seq[String] = args.head.split(",").toSeq
    consumer.subscribe(topics.asJava)
    println(s"Subscribing to $topics")
    consumer
  }

  def main(args: Array[String]): Unit = {
    val consumer = makeConsumer(args)
    val timeout = args.last.toInt * 1000

    try {
      var stop = false
      while(!stop) {
        val records: ConsumerRecords[String, String] = consumer.poll(timeout)
        val iterator: java.util.Iterator[ConsumerRecord[String, String]] = records.iterator()
        if(iterator.hasNext) {
          while(iterator.hasNext) {
            val record: ConsumerRecord[String, String] = iterator.next()
            println(s"  Got record: $record")
          }
        }
        else {
          stop = true
        }
      }
    }
    finally {
      consumer.close()
      println("Consumer shut down")
    }
  }
}
