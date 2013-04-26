Java 8 Benchmarks
=================

Project for analysing the performance impact of Java 8 changes: both language wise and in terms of libraries.

How to run
----------

* Set JAVA\_HOME to point to java 8 build 86 or later.
* Go into the `java-8-benchmarks` directory
* `mvn package`
* `./run-benchmarks`

Current Benchmarks
------------------

* Dot Product of 10 million entity vector
* Purchase Recommendation system (a la Amazon)

Ideas for future Benchmarks
---------------------------

* Auto-correlation
* Image Convolutions
 - blurring
 - feature detection via sobel
* Prolog Interpreter
* Re-encode strings into different character sets
* K-Means Clustering

