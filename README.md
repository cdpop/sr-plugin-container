# Schema Registry Plugin Container

This project uses [Confluent Schema Registry Maven Plugin](https://docs.confluent.io/platform/current/schema-registry/develop/maven-plugin.html#sr-maven-plugin) to produce a message into a topic. As of right now, [schema-registry:derive-schema](https://docs.confluent.io/platform/current/schema-registry/develop/maven-plugin.html#schema-registry-derive-schema) has been implemented in this project. In the near future, I plan on having the remainder of the plugins setup as well. 

## How to use
Pull docker image:

```
docker pull cdpop/sr-plugin
```

Run docker image:
```
docker run --network plaintext_default --name sr_plugins --env MESSAGE='{"me": "Foo", "Age": 22}' --rm sr_plugins
```


## Available Configurations
The following environment variables will help with producing the message to the topic:

| Environment Variable | Description                                                | Default                  | Valid Value(s)      | Example                                  |
|----------------------|------------------------------------------------------------|--------------------------|---------------------|------------------------------------------|
| MESSAGE              | JSON message which will get passed to the console producer | {"me": "Foo", "Age": 12} | Primitive data only | --env MESSAGE='{"me": "Foo", "Age": 12}' |
| DATA_TYPE            | Data type of the message being produced into the topic.    | avro                     | avro,json,protobuf  | --env DATA_TYPE=json                     |


The following are optional but useful environment variables:


| Environment Variable    | Description                                                                                                                                                                 | Default                     | Valid Value(s)                                        | Example                                                                                   |
|-------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|-------------------------------------------------------|-------------------------------------------------------------------------------------------|
| BROKER                  | Broker hostname:port to connect to.                                                                                                                                         | broker:9092                 | <hostname>:<port>                                     | --env BROKER=broker:9092                                                                  |
| SCHEMA_URL              | Schema registry url                                                                                                                                                         | http://schema-registry:8081 | http://<hostname>:<port>, https://< hostname> :<port> | --env SCHEMA_URL="http://schema-registry:8081"                                            |
| TOPIC                   | Kafka broker topic name                                                                                                                                                     | sample_data                 | *                                                     | --env TOPIC=sample_data                                                                   |
| ADDITIONAL_PROPERTIES   | Console producer options available for the CLI to process. The number of options being passed in is not limited as long as there's a white space in between each property.  | ""                          | --property <key>=<value>                              | --env ADDITIONAL_PROPERTIES="--property headers.separator=, --property=parse.header=true" |
| OUTPUT_FILE             | The file where output schema is written to.                                                                                                                                 | schema.json                 | *.json                                                | --env OUTPUT_FILE=test.json                                                               |
| SR_MAVEN_PLUGIN_VERSION | Maven plugin version                                                                                                                                                        | 7.3.1                       | 7.3.1                                                 | --env SR_MAVEN_PLUGIN_VERSION=7.3.1                                                       |
| PATHS                   | Path where to read JSON message from and path where to output schema.                                                                                                       | /home/appuser               | /home/appuser                                         | --env PATHS=/home/appuser                                                                 |
| SHELL_FILE              | Shell script run to update pom.xml file which also calls the *-console-producer                                                                                             | derive_schema.sh            | derive_schema.sh                                      | --env SHELL_FILE=derive_schema.sh                                                         |



## Limitations
- Derive-schema plugin is limited to primitive data types only per the following [link](https://docs.confluent.io/platform/current/schema-registry/develop/maven-plugin.html#primitive-data-types-mapping)
- This repository is meant as a POC and is not supported by any vendor at the time. 