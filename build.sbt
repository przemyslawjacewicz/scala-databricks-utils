// versions
val sparkVersion = "4.0.0"
val deltaVersion = "4.0.0"

Test / fork := true

//todo: adding env as a source dependency, once env is complete it can be added as a usual/local dependency
lazy val env = ProjectRef(file("../scala-databricks-env"), "scala-databricks-env")

lazy val root = (project in file("."))
  .dependsOn(env)
  .settings(
    name         := "scala-databricks-utils",
    version      := "1.0",
    scalaVersion := "2.13.16",
    scalacOptions ++= Seq("-deprecation", "-encoding", "utf-8", "-feature", "-unchecked"),
    libraryDependencies ++= Seq(
      "org.apache.spark"           %% "spark-core"    % sparkVersion % Provided,
      "org.apache.spark"           %% "spark-sql"     % sparkVersion % Provided,
      "io.delta"                   %% "delta-spark"   % deltaVersion % Provided,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"      % Provided,
      "org.slf4j"                   % "slf4j-api"     % "2.0.16"     % Provided,
      "org.scalactic"              %% "scalactic"     % "3.2.19",
      "org.scalatest"              %% "scalatest"     % "3.2.19"     % Test,
      "org.scalamock"              %% "scalamock"     % "7.5.5"      % Test
    )
  )
