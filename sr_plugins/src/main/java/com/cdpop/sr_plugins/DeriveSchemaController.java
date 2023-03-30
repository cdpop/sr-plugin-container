package com.cdpop.sr_plugins;

import com.cdpop.sr_plugins.constant.AnnotationConstants;
import com.cdpop.sr_plugins.processor.StartProcessHelper;
import io.micrometer.core.instrument.util.StringEscapeUtils;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@Tag(name="Derive Schema APIs")
public class DeriveSchemaController {

    static final Logger LOGGER = Logger.getLogger(DeriveSchemaController.class.getName());


    @PostMapping(value = {"/derive-schema/{data_type}"})
    public String callDeriveSchema_data_type(
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);

        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }

        int processResult = startProcessHelper.processStart(
                String.format("/home/appuser/derive_schema.sh %s %s ",
                        data_type,
                        payload));
        return processResult > 0 ? "Message failed to send to topic, check logs" : "Success";

    }
    @PostMapping(value = {"/derive-schema/{data_type}/{interval}"})
    public String callDeriveSchema_data_type_interval(
            @Parameter(description = AnnotationConstants.intervalDescription) @PathVariable(required = false) String interval,
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);
        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }
        int iterator = 0;
        int intervals = startProcessHelper.numOfCalls(interval);
        while (iterator < intervals ){
            int processResult = startProcessHelper.processStart(
                    String.format("/home/appuser/derive_schema.sh %s %s ",
                            data_type,
                            payload));
            if (processResult > 0){
                return "Could not send message to topic, check container logs";
            }
            iterator++;

        }
        return "Message was sent to topic.";
    }

    @PostMapping(value = {"/derive-schema/topic/{topic}/{data_type}"})
    public String callDeriveSchemaTopic(
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.topicDescription) @PathVariable(required = false) String topic,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        topic = startProcessHelper.pathVariableFormatter(topic,"sample_data");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);
        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }

        int processResult = startProcessHelper.processStart(
                    String.format("/home/appuser/derive_schema.sh %s %s broker:9092 %s",
                            data_type,
                            payload, topic));
        if (processResult > 0){
            return "Could not send message to topic, check container logs";
        }
        return "Message was sent to topic.";
    }
    @PostMapping(value = {"/derive-schema/topic/{topic}/{data_type}/{interval}"})
    public String callDeriveSchemaTopicInterval(
            @Parameter(description = AnnotationConstants.intervalDescription) @PathVariable(required = false) String interval,
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.topicDescription) @PathVariable(required = false) String topic,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        topic = startProcessHelper.pathVariableFormatter(topic,"sample_data");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);
        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }
        int iterator = 0;
        int intervals = startProcessHelper.numOfCalls(interval);
        while (iterator < intervals ){
            int processResult = startProcessHelper.processStart(
                    String.format("/home/appuser/derive_schema.sh %s %s broker:9092 %s ",
                            data_type,
                            payload,topic));
            if (processResult > 0){
                return "Could not send message to topic, check container logs";
            }
            iterator++;

        }
        return "Message was sent to topic.";
    }

    @PostMapping(value = {"/derive-schema/broker/{broker}/{topic}/{data_type}"})
    public String callDeriveSchemaBrokerTopicDataType(
            @Parameter(description = AnnotationConstants.brokerDescription) @PathVariable(required = false) String broker,
            @Parameter(description = AnnotationConstants.topicDescription) @PathVariable(required = false) String topic,
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        broker = startProcessHelper.pathVariableFormatter(broker,"broker:9092");
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        topic = startProcessHelper.pathVariableFormatter(topic,"sample_data");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);
        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }

        int processResult = startProcessHelper.processStart(
                String.format("/home/appuser/derive_schema.sh %s %s %s %s ",
                        data_type,
                        payload,
                        broker,
                        topic));
        if (processResult > 0){
            return "Could not send message to topic, check container logs";
        }

        return "Message was sent to topic.";
    }

    @PostMapping(value = {"/derive-schema/broker/{broker}/{topic}/{data_type}/{interval}"})
    public String callDeriveSchemaBrokerTopicDataType(
            @Parameter(description = AnnotationConstants.brokerDescription) @PathVariable(required = false) String broker,
            @Parameter(description = AnnotationConstants.topicDescription) @PathVariable(required = false) String topic,
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.intervalDescription) @PathVariable(required = false) String interval,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        broker = startProcessHelper.pathVariableFormatter(broker,"broker:9092");
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        topic = startProcessHelper.pathVariableFormatter(topic,"sample_data");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);
        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }
        int iterator = 0;
        int intervals = startProcessHelper.numOfCalls(interval);
        while (iterator < intervals ) {
            int processResult = startProcessHelper.processStart(
                    String.format("/home/appuser/derive_schema.sh %s %s %s %s ",
                            data_type,
                            payload,
                            broker,
                            topic));
            if (processResult > 0) {
                return "Could not send message to topic, check container logs";
            }
        }

        return "Message was sent to topic.";
    }

    @PostMapping(value = {"/derive-schema/broker/{broker}/{topic}/{sr_url}/{data_type}/{interval}/{extra_props}"})
    public String callDeriveSchemaBrokerTopicDataType(
            @Parameter(description = AnnotationConstants.brokerDescription) @PathVariable(required = false) String broker,
            @Parameter(description = AnnotationConstants.topicDescription) @PathVariable(required = false) String topic,
            @Parameter(description = AnnotationConstants.srUrlDescription) @PathVariable(required = false) String sr_url,
            @Parameter(description = AnnotationConstants.dataTypeDescription) @PathVariable(required = false) String data_type,
            @Parameter(description = AnnotationConstants.intervalDescription) @PathVariable(required = false) String interval,
            @Parameter(description = AnnotationConstants.extraPropertiesDescription) @PathVariable(required = false) String extra_props,
            @Parameter(description = AnnotationConstants.payLoadDescription) @RequestBody String payload
    ){
        StartProcessHelper startProcessHelper = new StartProcessHelper();
        broker = startProcessHelper.pathVariableFormatter(broker,"broker:9092");
        data_type = startProcessHelper.pathVariableFormatter(data_type,"avro");
        topic = startProcessHelper.pathVariableFormatter(topic,"sample_data");
        sr_url = startProcessHelper.pathVariableFormatter(sr_url,"http://schema-registry:8081");
        extra_props = startProcessHelper.pathVariableFormatter(extra_props,"");
        String payloadResult = startProcessHelper.isPayLoadGood(payload);
        if (!payloadResult.equals("continue")){
            // Payload was not provided sending response back
            return payloadResult;
        }
        int iterator = 0;
        int intervals = startProcessHelper.numOfCalls(interval);
        while (iterator < intervals ) {
            int processResult = startProcessHelper.processStart(
                    String.format("/home/appuser/derive_schema.sh %s %s %s %s %s %s",
                            data_type,
                            payload,
                            broker,
                            topic,
                            sr_url,
                            extra_props));
            if (processResult > 0) {
                return "Could not send message to topic, check container logs";
            }
        }

        return "Message was sent to topic.";
    }



}
