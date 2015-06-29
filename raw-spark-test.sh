#!/usr/bin/env bash

JAR=target/scala-2.10/accumulo-spark.jar

DRIVER_MEMORY=1G

EXECUTOR_MEMORY=1G

MASTER=local[2]

INPUT=file:/data/words.txt

# OUTPUT is a dir; though it is possibe to modify job, to get a file with counted words (use core.HdfsUtils)
# /data/words-count/part-0000 contains pairs of counted words
OUTPUT=file:/data/words-count

zip -d $JAR META-INF/ECLIPSEF.RSA
zip -d $JAR META-INF/ECLIPSEF.SF

spark-submit \
--class jobs.RawSparkTest \
--master $MASTER \
--driver-memory $DRIVER_MEMORY \
--executor-memory $EXECUTOR_MEMORY \
$JAR \
--input $INPUT --output $OUTPUT
