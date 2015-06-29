package jobs

import com.quantifind.sumac.validation.Required
import com.quantifind.sumac.{ArgMain, FieldArgs}
import core.utils.SparkUtils
import org.apache.spark._

class RawSparkTestArgs extends FieldArgs {
  @Required var input : String = _
  @Required var output: String = _
}

object RawSparkTest extends ArgMain[RawSparkTestArgs] with Logging {
  def main(args: RawSparkTestArgs) {
    val sc = SparkUtils.createSparkContext("RawSparkTest")
    val input = sc.textFile(args.input).repartition(1)
    val words = input.flatMap(line => line.split(" "))
    val counts = words.map(word => (word, 1)).reduceByKey { case (x, y) => x + y }
    counts.saveAsTextFile(args.output)
  }
}
