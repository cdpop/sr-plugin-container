package com.cdpop.sr_plugins.constant;

public class AnnotationConstants {
    public static final String intervalDescription = "Number of times to send the message, default 1";
    public static final String dataTypeDescription = "avro,json,protobuf, this determines which console producer is used, default:avro";
    public static final String extraPropertiesDescription = "Extra properties for console consumers, default: \"\" ";
    public static final String topicDescription= "Topic name requires to be larger than , default: sample_data";
    public static final String payLoadDescription= "Message to be sent to topic, example: '{\"name\":\"value2\"}' ";
    public static final String brokerDescription = "<hostname:port>, default: broker:9092";
    public static final String srUrlDescription = "http://<hostname>:port, default: http://schema-registry:8081";


}
