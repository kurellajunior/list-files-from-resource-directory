package org.kurella.jar

import java.nio.file.Files
import scala.io.Source
import scala.jdk.CollectionConverters._

class SpecReader {
  def readSpecMessage(spec: String): String = {
    List("CN", "DO", "KF")
      .flatMap(ConfigFiles.listPathsFromResource(s"/spec_$spec", _).asScala.toSeq)
      .flatMap(path ⇒ Source.fromInputStream(Files.newInputStream(path), "UTF-8").getLines())
      .reduce(_ + " " + _)
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    System.out.println(new SpecReader().readSpecMessage(args.head))
  }
}
