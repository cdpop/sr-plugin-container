#!/bin/bash
set -e 

EXECUTE=$1
DATA_TYPE="${2:-avro}"
DATA_TYPE=$(echo "$DATA_TYPE" | tr '[:upper:]' '[:lower:]')

MESSAGE="${3:-{\"me\":\"Foo\", \"Age\": 14}}"
# needed to export to fix escape issues
export MESSAGE="$MESSAGE"

echo $MESSAGE > $PATHS/file.txt

BROKER="${4:-broker:9092}"
TOPIC="${5:-sample_data}"
SCHEMA_URL="${6:-http://schema-registry:8081}"
ADDITIONAL_PROPERTIES="${7:-}"
# data_type has to be lowercase
DATA_TYPE=$(echo "$DATA_TYPE" | tr '[:upper:]' '[:lower:]')

SR_MAVEN_PLUGIN_VERSION="${8:-7.3.1}"
PATHS="${9:-/home/appuser}"
SHELL_FILE="${10:-derive_schema.sh}"
OUTPUT_FILE="${11:-schema.json}"

echo $MESSAGE > $PATHS/file.txt

echo -e " \n
<project xmlns=\"http://maven.apache.org/POM/4.0.0\" 
xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  
  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0   
http://maven.apache.org/xsd/maven-4.0.0.xsd\">  
  <modelVersion>4.0.0</modelVersion>  
  <groupId>io.confluent.app1</groupId>  
  <artifactId>my-app</artifactId>  
  <version>1</version>  
    <pluginRepositories> 
        <pluginRepository> 
            <id>confluent</id> 
            <url>https://packages.confluent.io/maven/</url> 
        </pluginRepository> 
    </pluginRepositories> 
  <build> 
	<plugins> 
		<plugin> 
			<groupId>io.confluent</groupId> 
			<artifactId>kafka-schema-registry-maven-plugin</artifactId> 
			<version>$SR_MAVEN_PLUGIN_VERSION</version> 
			<configuration> 
				<messagePath>$PATHS/file.txt</messagePath> 
				<schemaType>$DATA_TYPE</schemaType> 
				<outputPath>$PATHS/$OUTPUT_FILE</outputPath> 
			</configuration> 
		</plugin> 
	</plugins> 
  </build> 
</project>  
 \n
 " > $PATHS/pom.xml

if [ $EXECUTE = true ]
 then
  mvn io.confluent:kafka-schema-registry-maven-plugin:derive-schema
fi

if [ $DATA_TYPE = "avro" ]
 then
   echo "$MESSAGE" | kafka-$DATA_TYPE-console-producer --broker-list $BROKER --topic $TOPIC --property schema.registry.url=$SCHEMA_URL --property value.schema="$(cat $PATHS/$OUTPUT_FILE | jq -r '.schemas[]|del(.messagesMatched)|.schema')" $ADDITIONAL_PROPERTIES
   echo "Message was sent to $TOPIC."
elif [ $DATA_TYPE = "protobuf" ]
  then
    echo "$MESSAGE" | kafka-$DATA_TYPE-console-producer --broker-list $BROKER --topic $TOPIC --property schema.registry.url=$SCHEMA_URL --property value.schema="$(cat $PATHS/$OUTPUT_FILE | jq -r '.schemas[]|del(.messagesMatched)|.schema')" $ADDITIONAL_PROPERTIES
    echo "Message was sent to $TOPIC."
elif [ $DATA_TYPE = "json" ]
  then
    echo "$MESSAGE" | kafka-$DATA_TYPE-schema-console-producer --broker-list $BROKER --topic $TOPIC --property schema.registry.url=$SCHEMA_URL --property value.schema="$(cat $PATHS/$OUTPUT_FILE | jq -r '.schemas[]|del(.messagesMatched)|.schema')" $ADDITIONAL_PROPERTIES
    echo "Message was sent to $TOPIC."
else
    echo "The provided $DATA_TYPE is not supported. No message was produced to $BROKER in topic $TOPIC."
fi


 
