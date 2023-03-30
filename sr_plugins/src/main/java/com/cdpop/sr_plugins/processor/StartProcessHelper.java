package com.cdpop.sr_plugins.processor;

import com.cdpop.sr_plugins.DeriveSchemaController;
import io.micrometer.core.instrument.util.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;

public class StartProcessHelper {
    public StartProcessHelper() {
    }
    static final Logger LOGGER = Logger.getLogger(StartProcessHelper.class.getName());


    public int processStart(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        StringBuilder output = new StringBuilder();
        int exitVal = -1;
        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            exitVal = process.waitFor();
            LOGGER.info(output.toString());

            System.out.println(output.toString());
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                return exitVal;
            } else {
                return exitVal;
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exitVal;
    }

    public int numOfCalls(String interval){
        int num_of_dup_messages = 1;
        try{
            num_of_dup_messages = Integer.parseInt(interval);
        }catch (Exception e){
            System.out.println("Could not convert interval " + interval + " to integer defaulting to 1");
            num_of_dup_messages = 1;
        }
        return num_of_dup_messages;
    }

    public String pathVariableFormatter(String pathVariable, String defaultValue){
        // Incase someone passes an empty string for a parameter
        return  Objects.toString(pathVariable.length() < 3 ? pathVariable.trim().replaceAll("\"","") : pathVariable,"").isEmpty() ?  defaultValue : pathVariable;
    }

    public String isPayLoadGood(String payload){
        if (payload.isEmpty() || payload.isBlank() || payload == null) {
            System.out.println("No payload was provided");
            return "No payload was provided, make sure to include your message in the curl with -d parameter \n" +
                    "curl -H \"Accept: application/json\" -H \"Content-type: application/json\"  -X POST http://localhost:8080/derive-schema/protobuf -d '{\"name\":\"value\"}'  ";
        }else {
            return "continue";
        }
    }


}
