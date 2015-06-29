#!/usr/bin/env bash

JAR=target/scala-2.10/accumulo-spark.jar

DRIVER_MEMORY=1G

EXECUTOR_MEMORY=1G

MASTER=local[2]

zip -d $JAR META-INF/ECLIPSEF.RSA
zip -d $JAR META-INF/ECLIPSEF.SF

spark-submit \
--class jobs.AccumuloTest \
--master $MASTER \
--driver-memory $DRIVER_MEMORY \
--executor-memory $EXECUTOR_MEMORY \
$JAR \
--instance gis --user root --password secret --zookeeper localhost \
--table accumulo.metadata
