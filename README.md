# File
_[Majksa Commons](https://github.com/majksa-commons)_

Java library extending [Log4J](https://logging.apache.org/log4j/2.x/) introducing error logging and safe running a code.

## Summary
1. [Installation](#installation)
2. [Setup](#setup)

## Installation
Make sure to replace `%version%` with the latest version number or a commit hash, e.g. `1.0.0`.
More info [HERE](https://jitpack.io/#majksa-commons/logging)

###  Maven
Register the repository
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
Now add the dependency itself
```xml
<dependency>
    <groupId>com.github.majksa-commons</groupId>
    <artifactId>logging</artifactId>
    <version>%version%</version>
</dependency>
```
###  Gradle
Register the repository
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
```
Now add the dependency itself
```gradle
dependencies {
    implementation 'com.github.majksa-commons:logging:%version%'
}
```

## Setup
All settings are done through `logger.properties` file that needs to be located in the classpath.<br>
You can set:<br>
* errors - this is the location of the folder where serialized exceptions are saved to
* debug - if debug messages are shown

### Default values
```properties
errors = errors
debug = true
```
