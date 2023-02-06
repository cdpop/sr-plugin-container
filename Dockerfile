FROM confluentinc/cp-schema-registry
LABEL maintainer="https://github.com/cdpop"

ENV MESSAGE='{"me": "Foo", "Age": 12}'
ENV DATA_TYPE="avro"
ENV OUTPUT_FILE="schema.json"
ENV SR_MAVEN_PLUGIN_VERSION=7.3.1
ENV PATHS="/home/appuser"
ENV SHELL_FILE="derive_schema.sh"

ENV BROKER="broker:9092"
ENV SCHEMA_URL="http://schema-registry:8081"
ENV TOPIC="sample_data"
ENV ADDITIONAL_PROPERTIES=""


USER root
RUN yum install -y jq maven
WORKDIR /home/appuser

RUN echo $MESSAGE > $PATHS/file.txt

RUN echo -e " \n\
<project xmlns=\"http://maven.apache.org/POM/4.0.0\" \n\
xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  \n\
  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0   \n\
http://maven.apache.org/xsd/maven-4.0.0.xsd\">  \n\
  <modelVersion>4.0.0</modelVersion>  \n\
  <groupId>io.confluent.app1</groupId>  \n\
  <artifactId>my-app</artifactId>  \n\
  <version>1</version>  \n\
    <pluginRepositories> \n\
        <pluginRepository> \n\
            <id>confluent</id> \n\
            <url>https://packages.confluent.io/maven/</url> \n\
        </pluginRepository> \n\
    </pluginRepositories> \n\
  <build> \n\
	<plugins> \n\
		<plugin> \n\
			<groupId>io.confluent</groupId> \n\
			<artifactId>kafka-schema-registry-maven-plugin</artifactId> \n\
			<version>$SR_MAVEN_PLUGIN_VERSION</version> \n\
			<configuration> \n\
				<messagePath>$PATHS/file.txt</messagePath> \n\
				<schemaType>$DATA_TYPE</schemaType> \n\
				<outputPath>$PATHS/$OUTPUT_FILE</outputPath> \n\
			</configuration> \n\
		</plugin> \n\
	</plugins> \n\
  </build> \n\
</project>  \n\
 \n\
 " > $PATHS/pom.xml

RUN  mvn io.confluent:kafka-schema-registry-maven-plugin:derive-schema
COPY $SHELL_FILE .

# Requires for me to escape JSON when passing in the variable otherwise shell script $1 will be broken

CMD ["bash","-c","/home/appuser/$SHELL_FILE"]