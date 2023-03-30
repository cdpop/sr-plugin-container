package com.cdpop.sr_plugins;

import io.micrometer.core.instrument.util.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;

@RestController
public class DeriveSchemaController {

    static final Logger LOGGER = Logger.getLogger(DeriveSchemaController.class.getName());

    @PostMapping(value = {
            "/derive-schema/{data_type}",
            "/derive-schema/{data_type}/{interval}",
            "/derive-schema/{data_type}/{interval}/{extra_props}",
            "/derive-schema/topic/{topic}/{data_type}",
            "/derive-schema/topic/{topic}/{data_type}/{interval}",
            "/derive-schema/topic/{topic}/{data_type}/{interval}/{extra_props}",
            "/derive-schema/topic/{topic}/{sr_url}/{data_type}/{interval}/{extra_props}",
            "/derive-schema/broker/{broker}/{topic}/{data_type}",
            "/derive-schema/broker/{broker}/{topic}/{data_type}/{interval}",
            "/derive-schema/broker/{broker}/{topic}/{sr_url}/{data_type}/{interval}",
            "/derive-schema/broker/{broker}/{topic}/{sr_url}/{data_type}/{interval}/{extra_props}"
    })
    public String callDeriveSchemaSh(@PathVariable(required = false) String interval,
                                     @PathVariable(required = false) String broker,
                                     @PathVariable(required = false) String topic,
                                     @PathVariable(required = false) String sr_url,
                                     @PathVariable(required = false) String data_type,
                                     @PathVariable(required = false) String extra_props,
                                     @RequestBody String payload) {

        broker = Objects.toString(broker,"").isEmpty() ?  "broker:9092" : broker;
        topic = Objects.toString(topic,"").isEmpty() ? "sample_data" : topic;
        sr_url = Objects.toString(sr_url,"").isEmpty() ? "http://schema-registry:8081" : sr_url;
        data_type = Objects.toString(data_type,"").isEmpty() ? "avro" : data_type;
        extra_props = Objects.toString(extra_props,"").isEmpty() ? "" : extra_props;


        int num_of_dup_messages = 1;
        try{
            num_of_dup_messages = Integer.parseInt(interval);
        }catch (Exception e){
            System.out.println("Could not convert interval " + interval + " to integer defaulting to 1");
            num_of_dup_messages = 1;
        }
        int interator = 0;
        while(interator < num_of_dup_messages){

            if (payload.isEmpty() || payload.isBlank() || payload == null) {
                System.out.println("No payload was provided");
                return "No payload was provided, make sure to include your message in the curl with -d parameter \n" +
                        "curl -H \"Accept: application/json\" -H \"Content-type: application/json\"  -X POST http://localhost:8080/derive-schema/protobuf -d '{\"name\":\"value\"}'  ";
            }

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c",
                    String.format("/home/appuser/derive_schema.sh %s %s %s %s %s %s",
                            data_type,
                            StringEscapeUtils.escapeJson(payload),
                            broker,
                            topic,
                            sr_url,
                            extra_props)
                    );

            try {
                Process process = processBuilder.start();
                StringBuilder output = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

                int exitVal = process.waitFor();
                LOGGER.info(output.toString());


                System.out.println("Info for shell script -> " +
                        String.format("/home/appuser/derive_schema.sh %s %s %s %s %s %s",
                                data_type,
                                StringEscapeUtils.escapeJson(payload),
                                Objects.toString(broker,""),
                                Objects.toString(topic,""),
                                Objects.toString(sr_url,""),
                                Objects.toString(extra_props,"")));
                System.out.println(output.toString());
                if (exitVal == 0) {
                    System.out.println("Success!");
                    System.out.println(output);
                } else {
                    //abnormal...
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            interator++;
        }

        return "Request was successfull";
    }
}
