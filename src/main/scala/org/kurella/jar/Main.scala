package org.kurella.jar

import java.nio.file._
import java.util.function.{BiConsumer, BinaryOperator, Function, Supplier}
import java.util.stream.Collector
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.jdk.CollectionConverters._

class SpecReader (val spec:String) {

  private val basePath = s"/spec_$spec"

  lazy val jarFileSystem: FileSystem = FileSystems.newFileSystem(getClass.getResource(basePath).toURI, Map[String, String]().asJava);

  def readSpecMessage(): String = {
    List("CN", "DO", "KF")
      .flatMap(ConfigFiles.listPathsFromResource(basePath, _).asScala.toSeq)
      .flatMap(path ⇒ Source.fromInputStream(Files.newInputStream(path), "UTF-8").getLines())
      .reduce(_ + " " + _)
  }

  def readSpecMessageScala(): String = {
    List("CN", "DO", "KF")
      .flatMap(listPathsFromResource)
      .flatMap(path ⇒ Source.fromInputStream(Files.newInputStream(path), "UTF-8").getLines())
      .reduce(_ + " " + _)
  }

  val collector: Collector[_ >: Path, ListBuffer[Path], List[Path]] =  Collector.of(
    new Supplier[ListBuffer[Path]]() {
      override def get(): ListBuffer[Path] = ListBuffer[Path]()
    },
    new BiConsumer[ListBuffer[Path], Path]() {
      override def accept(t: ListBuffer[Path], u: Path): Unit = t.addOne(u)
    },
    new BinaryOperator[ListBuffer[Path]]() {
      override def apply(t: ListBuffer[Path], u: ListBuffer[Path]): ListBuffer[Path] = t.addAll(u)
    },
    new Function[ListBuffer[Path], List[Path]](){
      override def apply(v1: ListBuffer[Path]): List[Path] = v1.toList
    },
    Array[Collector.Characteristics](): _*
)

  def listPathsFromResource(folder: String): List[Path] = {
    Files.list(getPathForResource(folder))
      .filter(p ⇒ Files.isRegularFile(p, Array[LinkOption](): _*))
      .sorted.collect(collector)
  }

  private def getPathForResource(filename: String) = {
    val url = classOf[ConfigFiles].getResource(basePath + "/" + filename)
    if ("file" == url.getProtocol) Paths.get(url.toURI)
    else jarFileSystem.getPath(basePath, filename)
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    System.out.println(new SpecReader(args.head).readSpecMessageScala())
  }
}
