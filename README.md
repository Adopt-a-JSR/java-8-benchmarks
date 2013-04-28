Java 8 Benchmarks
=================

Project for analysing the performance impact of Java 8 changes: both language wise and in terms of libraries.

How to run
----------

* Set JAVA\_HOME to point to java 8 build 86 or later.
* Go into the `java-8-benchmarks` directory
* `mvn package`
* `./run-benchmarks`

Current Implementations 
-----------------------

* Dot Product of 10 million entity vector
* Auto-correlation
* Re-encode strings into different character sets
* Purchase Recommendation system (a la Amazon)
 - needs imperative version

Ideas for future Benchmarks
---------------------------

* Image Convolutions
 - blurring
 - feature detection via sobel
* K-Means Clustering

