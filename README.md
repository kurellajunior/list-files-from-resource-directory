# list-files-from-resource-directory
A short proof of concept how to interact with folders inside the resource folder with scala

Two ways are implemented. One via java streams with predefined Collector.
Second with selfwritten Collector that returns actually a scala.List via ListBuffer

Run from console `sbt package "run A"` to see test output `Yet another message to read for the upcoming test`

or run `sbt package "run A"`  to see option B: `Yet another message to read for the upcoming test`