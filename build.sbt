import AssemblyKeys._

name := "Accumulo Spark"

organization := ""

version := "0.0.1"

scalaVersion := "2.10.5"

def either(environmentVariable: String, default: String): String =
  sys.env.getOrElse(environmentVariable, default)

lazy val sparkVersion    = either("SPARK_VERSION", "1.3.1")
lazy val hadoopVersion   = either("SPARK_HADOOP_VERSION", "2.6.0")
lazy val accumuloVersion = either("ACCUMULO_VERSION", "1.6.2")

libraryDependencies ++= Seq(
  "org.apache.spark"    %% "spark-core"    % sparkVersion,
  "org.apache.hadoop"   %  "hadoop-client" % hadoopVersion,
  "org.apache.accumulo" %  "accumulo-core" % accumuloVersion,
  "com.quantifind"      %% "sumac"         % "0.3.0",
  "org.scalatest"       %% "scalatest"     % "2.2.0" % "test"
)

assemblySettings

jarName in assembly := "accumulo-spark.jar"

mergeStrategy in assembly <<= (mergeStrategy in assembly) {
  (old) => {
    case "reference.conf" => MergeStrategy.concat
    case "application.conf" => MergeStrategy.concat
    case "META-INF/MANIFEST.MF" => MergeStrategy.discard
    case "META-INF\\MANIFEST.MF" => MergeStrategy.discard
    case _ => MergeStrategy.first
  }
}
