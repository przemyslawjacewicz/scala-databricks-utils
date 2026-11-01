package pl.epsilondeltalimit.scala_databricks_utils.transactional

//todo: add tupled method
//todo: consider a name change
trait Single[S, O, B] {
  val source: S
  val output: S => O
  val backup: S => B
  val restore: B => S => Unit

  //todo: create a version that does not need cleanup
  val cleanup: B => Unit // todo: check if we need this
}
