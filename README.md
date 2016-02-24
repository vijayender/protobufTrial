# Protobuf-trial

Very basic protobuf setup and file-io along with kafka-io
Look at the git commits to see the progressive steps.

### Objectives

- Maven https://maven.apache.org/guides/getting-started/
    - shade plugin https://maven.apache.org/plugins/maven-shade-plugin/examples/executable-jar.html
- protobuf
    - mvn proto compile
    - mvn project with builder and consumer
    -  File io of protobuf
    - Kafka io of protobuf

### Intellij Workarounds

- Intellij introspection fails as the sources are generated into target/generated-sources
and target/ is marked as excluded. http://stackoverflow.com/questions/15370978/intellij-include-a-directory-contained-in-an-excluded-directory
    1. To enable introspection for these files, right click
target/ >> "Mark Directory as" >> "Cancel Exclusion"
    2. Right Click "target/generated-sources" >> "Mark Directory as" >> "Generated-sources root"
 
### Resources

- protoc compiling from maven http://sergei-ivanov.github.io/maven-protoc-plugin/usage.html
  Not developed any more. Rather use
    - https://github.com/xolstice/protobuf-maven-plugin
    - https://www.xolstice.org/protobuf-maven-plugin/usage.html