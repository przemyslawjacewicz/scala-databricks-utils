package pl.epsilondeltalimit.scala_databricks_utils.transactional

// todo: move this to commons
object ThrowWhenNot {
  def throwWhenNotTrue(o: => Boolean): Unit =
    throwWhenNot(true)(o)

  def throwWhenNot[T](success: T)(o: => T): Unit = {
    val isSuccess = o

    if (isSuccess != success) {
      throw new RuntimeException("Operation finished with non-success value!")
    }
  }
}
