package core.utils

import org.apache.spark.{SparkContext, SparkConf}

object SparkUtils {
  def createSparkConf = new SparkConf()

  def createSparkContext(appName: String, sparkConf: SparkConf = createSparkConf): SparkContext = {
    sparkConf.setAppName(appName)

    new SparkContext(sparkConf)
  }
}
