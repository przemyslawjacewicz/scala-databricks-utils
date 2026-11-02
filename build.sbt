name         := "scala-databricks-utils"
organization := "pl.epsilondeltalimit"
version      := "1.0"

scalaVersion := "2.13.16"
scalacOptions ++= Seq("-feature", "-language:reflectiveCalls")

val sparkVersion = "4.0.0"
val deltaVersion = "4.0.0"

Test / fork := true

libraryDependencies ++= Seq(
  "org.apache.spark"           %% "spark-core"           % sparkVersion % Provided,
  "org.apache.spark"           %% "spark-sql"            % sparkVersion % Provided,
  "io.delta"                   %% "delta-spark"          % deltaVersion % Provided,
  "com.typesafe.scala-logging" %% "scala-logging"        % "3.9.2"      % Provided,
  "org.slf4j"                   % "slf4j-api"            % "2.0.16"     % Provided,
  "org.scalactic"              %% "scalactic"            % "3.2.19",
  "org.scalatest"              %% "scalatest"            % "3.2.19"     % Test,
  "org.scalamock"              %% "scalamock"            % "7.5.5"      % Test,
  "pl.epsilondeltalimit"       %% "scala-databricks-env" % "1.0"
)
