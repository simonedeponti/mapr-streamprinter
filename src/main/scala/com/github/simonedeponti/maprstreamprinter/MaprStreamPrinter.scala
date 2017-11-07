package com.github.simonedeponti.maprstreamprinter

import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._


object MaprStreamPrinter {

  def makeConsumer(topics: Seq[String], withOffset: Boolean): KafkaConsumer[String, String] = {
    val props: Properties = new Properties()
    props.setProperty("group.id", "maprstream-printer")
    props.setProperty("enable.auto.commit", "false")
    props.setProperty("auto.offset.reset", "earliest")
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(topics.asJava)
    println(s"### Subscribing to $topics\n\n")
    consumer
  }

  def main(args: Array[String]): Unit = {
    val offset: Option[(TopicPartition, Long)] = if (args.length > 2) {
      var offset_components = args(1).split(":")
      Some((new TopicPartition(offset_components.head, offset_components(1).toInt), offset_components.last.toLong))
    }
    else {
      None
    }
    val consumer = makeConsumer(args.head.split(",").toSeq, offset.isDefined)
    val timeout = args.last.toInt * 1000

    try {
      var stop = false
      if (offset.isDefined) {
        consumer.poll(0)
        consumer.seek(offset.get._1, offset.get._2)
      }
      while(!stop) {
        val records: ConsumerRecords[String, String] = consumer.poll(timeout)
        val iterator: java.util.Iterator[ConsumerRecord[String, String]] = records.iterator()
        if(iterator.hasNext) {
          while(iterator.hasNext) {
            val record: ConsumerRecord[String, String] = iterator.next()
            println(s"\n### Got record\n\n$record")
          }
        }
        else {
          stop = true
        }
      }
    }
    finally {
      consumer.close()
      println("### Consumer shut down")
    }
  }
}
