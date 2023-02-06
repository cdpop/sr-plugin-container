#! /bin/bash
set -e 


# data_type has to be lowercase
DATA_TYPE=$(echo "$DATA_TYPE" | tr '[:upper:]' '[:lower:]')

echo $MESSAGE > $PATHS/file.txt


echo -e " \n
<project xmlns=\"http://maven.apache.org/POM/4.0.0\" \n
xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  \n
  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0   \n
http://maven.apache.org/xsd/maven-4.0.0.xsd\">  \n
  <modelVersion>4.0.0</modelVersion>  \n
  <groupId>io.confluent.app1</groupId>  \n
  <artifactId>my-app</artifactId>  \n
  <version>1</version>  \n
    <pluginRepositories> \n
        <pluginRepository> \n
            <id>confluent</id> \n
            <url>https://packages.confluent.io/maven/</url> \n
        </pluginRepository> \n
    </pluginRepositories> \n
  <build> \n
	<plugins> \n
		<plugin> \n
			<groupId>io.confluent</groupId> \n
			<artifactId>kafka-schema-registry-maven-plugin</artifactId> \n
			<version>$SR_MAVEN_PLUGIN_VERSION</version> \n
			<configuration> \n
				<messagePath>$PATHS/file.txt</messagePath> \n
				<schemaType>$DATA_TYPE</schemaType> \n
				<outputPath>$PATHS/$OUTPUT_FILE</outputPath> \n
			</configuration> \n
		</plugin> \n
	</plugins> \n
  </build> \n
</project>  \n
 \n
 " > $PATHS/pom.xml


 mvn io.confluent:kafka-schema-registry-maven-plugin:derive-schema

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


 
