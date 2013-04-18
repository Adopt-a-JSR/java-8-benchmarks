#!/bin/sh

set -eu

java -server -Xmx2G -cp target/java-8-benchmarks-0.0.1-SNAPSHOT.jar org.adoptajsr.parallel.runners.VectorDotProduct

