# A Springboot based CPU Load Generator

This program trigger a CPU load across all the processors for a period.

## Compile
```
mvn clean package
```
## Run
Once build you can run it as
```
java -jar JARFILE
```
The web service is listening on 8080.

## Usage


launch the following URL:
  http://localhost:8008/heavyload?time=120&load=0.8

The "time" (in seconds) is how long you want the load to be lasting. The load is the target CPU usage you want to achieve.

This CPU Load is based on the work from [SriramKeerthi](https://gist.github.com/SriramKeerthi/0f1513a62b3b09fecaeb)
