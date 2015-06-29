# Accumulo Spark sample

Sample of project with test jobs to run with Apache Accumulo and Apache Spark.

### Pre-build

Configuration used to test sample:

* Java 7 / 8
* Hadoop 2.6.x / 2.7.x
* Zookeeper 2.3.6
* Accumulo 1.6.x
* Spark 1.3.x

### Build

* `./sbt assembly` — to build a fat jar,
* `./raw-spark-test.sh` — sample of run spark only job (WordCount),
* `./accumulo-test.sh` — sample of run accumulo query.

## License

* Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
