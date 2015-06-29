package core.utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs._

class HdfsUtils(hadoopConfig: Configuration) {
  def copyFromLocal (source: String, dest: String) = {
    val hdfs = FileSystem.get(hadoopConfig)
    val srcPath = new Path(source)
    val dstPath = new Path(dest)

    if (hdfs.exists(dstPath)) {
      println(s"$dstPath exists!")
      delFromHdfs(source)
    }

    val filename = source.split("/").last

    hdfs.copyFromLocalFile(srcPath, dstPath)
    println(s"File $filename (local) copied to $dest (hdfs)")
    hdfs.close()
  }

  def copyFromHdfs (source: String, dest: String) = {
    val hdfs = FileSystem.get(hadoopConfig)
    val srcPath = new Path(source)
    val dstPath = new Path(dest)

    if (hdfs.exists(dstPath)) println(s"$dstPath exists!")

    val filename = source.split("/").last

    hdfs.copyToLocalFile(srcPath, dstPath)
    println(s"File $filename (hdfs) copied to $dest (local)")
    hdfs.close()
  }

  def mergeOnHdfs(source: String, dest: String) = {
    val hdfs = FileSystem.get(hadoopConfig)
    val srcPath = new Path(source)
    val dstPath = new Path(dest)

    if (hdfs.exists(dstPath)) println(s"$dstPath exists!")

    val filename = source.split("/").last

    FileUtil.copyMerge(hdfs, srcPath, hdfs, dstPath, false, hadoopConfig, null)
    println(s"File $filename (hdfs) copied to $dest (hdfs)")
    hdfs.close()
  }

  def delFromHdfs(path: String) = {
    val hdfs = FileSystem.get(hadoopConfig)

    hdfs.delete(new Path(path), true)

    println(s"File $path (hdfs) deleted")
    hdfs.close()
  }
}

object HdfsUtils {
  def apply(hadoopConfig: Configuration) = new HdfsUtils(hadoopConfig)
}
