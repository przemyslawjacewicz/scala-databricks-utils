package pl.epsilondeltalimit.scala_databricks_utils.transactional

import org.apache.hadoop.fs.FileSystem
import org.apache.spark.sql.SparkSession

//todo: move this to commons
object Using {

  //  def using[U <: { def close(): Unit }, R](u: U)(r: U => R): R =
  //    try r(u)
  //    finally u.close()

  def using[R, O](resource: R)(r: R => O)(implicit closeable: Closeable[R]): O =
    try r(resource)
    finally closeable.close(resource)

  def usingFileSystem[R](r: FileSystem => R)(implicit spark: SparkSession, c: Closeable[FileSystem]): R =
    using(FileSystem.newInstance(spark.sparkContext.hadoopConfiguration))(r)

  trait Closeable[T] {
    def close(resource: T): Unit
  }

  object implicits {
    implicit def javaIoClosable[T <: java.io.Closeable]: Closeable[T] =
      (resource: T) => resource.close()
  }
}
