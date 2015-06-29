package jobs

import com.quantifind.sumac.validation.Required
import com.quantifind.sumac.{ArgMain, FieldArgs}
import core.utils.SparkUtils
import org.apache.accumulo.core.client.ZooKeeperInstance
import org.apache.accumulo.core.client.mapreduce.AccumuloInputFormat
import org.apache.accumulo.core.client.mapreduce.lib.util.{ConfiguratorBase => CB, InputConfigurator => IC}
import org.apache.accumulo.core.client.security.tokens.PasswordToken
import org.apache.accumulo.core.data.{Key, Range => ARange, Value}
import org.apache.spark._

import scala.collection.JavaConversions._

class AccumuloTestArgs extends FieldArgs {
  @Required var zookeeper: String = _
  @Required var instance : String = _
  @Required var user     : String = _
  @Required var password : String = _
  @Required var table    : String = _
}

object AccumuloTest extends ArgMain[AccumuloTestArgs] with Logging {
  def main(args: AccumuloTestArgs) {
    val sc = SparkUtils.createSparkContext("AccumuloTest")

    val conf = sc.hadoopConfiguration
    val user = args.user
    val authToken = new PasswordToken(args.password)
    val instance = new ZooKeeperInstance(args.instance, args.zookeeper)
    val table = args.table

    CB.setConnectorInfo(classOf[AccumuloInputFormat], conf, user, authToken)
    CB.setZooKeeperInstance(classOf[AccumuloInputFormat], conf, instance.getInstanceName, instance.getZooKeepers)

    IC.setInputTableName(classOf[AccumuloInputFormat], conf, table)
    IC.setRanges(classOf[AccumuloInputFormat], conf, List(new ARange()))

    val rdd = sc.newAPIHadoopRDD(conf, classOf[AccumuloInputFormat], classOf[Key], classOf[Value])

    println(s"RECORD COUNT IS: ${rdd.count()}")
  }
}
