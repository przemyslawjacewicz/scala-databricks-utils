package pl.epsilondeltalimit.scala_databricks_utils.transactional

import org.apache.hadoop.fs.{FileUtil, Path}
import org.apache.spark.sql.SparkSession
import SingleWithCopyBackup.{Source, delete}
import ThrowWhenNot.throwWhenNotTrue
import Using.implicits._
import Using.usingFileSystem

//todo: consider a better handling of backup location
//todo: consider removing cleanup with default behavior of creating backup in temp location that does not need cleanup
class SingleWithCopyBackup[O](override val source: Source,
                              override val output: String => O,
//                              b: String => String, //todo: ???
                              copy: (String, String) => Unit)(implicit spark: SparkSession)
    extends Single[String, O, String] {
  override val backup: String => String          = s => { val b = s + ".bak"; copy(s, b); b; }
  override val restore: String => String => Unit = b => s => copy(b, s)
  override val cleanup: String => Unit           = delete
}

object SingleWithCopyBackup {
  type Source = String
  type Output = String

  def delete(p: String)(implicit spark: SparkSession): Unit =
    throwWhenNotTrue(usingFileSystem(_.delete(new Path(p), true)))

  case class SparkCopy[O](format: String)(override val source: String, override val output: String => O)(
      implicit spark: SparkSession)
      extends SingleWithCopyBackup[O](
        source = source,
        output = output,
        copy = (from, to) => spark.read.format(format).load(from).write.format(format).mode("overwrite").save(to)
      )

  case class FileSystemCopy[O](override val source: String, override val output: String => O)(
      implicit spark: SparkSession)
      extends SingleWithCopyBackup[O](
        source = source,
        output = output,
        copy = (from, to) =>
          throwWhenNotTrue {
            usingFileSystem { fs =>
              FileUtil.copy(fs, new Path(from), fs, new Path(to), false, true, spark.sparkContext.hadoopConfiguration)
            }
          }
      )
}
