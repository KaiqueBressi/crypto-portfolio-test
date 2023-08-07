# Crypto Portfolio Challenge

## Building 

```
./gradlew build
```

## Running

```
./gradlew run 
```

## Tests

```
./gradlew test
```

## How it works? Threads vs Coroutines 

Since I decided to use Kotlin instead of Java I didn't use pure threads, I decided to use Coroutines which is an abstraction on top of threads, 
the idea behind it is making the code like any normal synchronius code and Kotlin takes charge of running it concurrently.

JVM Threads are also an abstraction on the top of system threads, what makes it perfect for things that are CPU-bound but HTTP Requests are not that case, CPU is not the bottleneck, 
so in this case using Threads is actually worse than Coroutines because it is way costly in terms of resources.

Coroutines are lightweight Thread that are perfect for those kind of situations where you have to wait for IO responses like for example Stream reading, Disk reading, Network responses, etc, making it 
very good in this scenario.
