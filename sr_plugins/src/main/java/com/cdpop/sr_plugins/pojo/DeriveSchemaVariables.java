package com.cdpop.sr_plugins.pojo;

public class DeriveSchemaVariables {

    private String MESSAGE = "{\"me\": \"Foo\", \"Age\": 12}";
    private String DATA_TYPE = "avro";
    private String OUTPUT_FILE="schema.json";
    private String SR_MAVEN_PLUGIN_VERSION="7.3.1";
    
    private String PATH = "/home/appuser";
    private String SHELL_FILE = "derive_schema.sh";

    //
    private String BROKER="broker:9092";
    private String SCHEMA_URL="http://schema-registry:8081";
    private String TOPIC="sample_data";
    private String ADDITIONAL_PROPERTIES="";

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getOUTPUT_FILE() {
        return OUTPUT_FILE;
    }

    public void setOUTPUT_FILE(String OUTPUT_FILE) {
        this.OUTPUT_FILE = OUTPUT_FILE;
    }

    public String getSR_MAVEN_PLUGIN_VERSION() {
        return SR_MAVEN_PLUGIN_VERSION;
    }

    public void setSR_MAVEN_PLUGIN_VERSION(String SR_MAVEN_PLUGIN_VERSION) {
        this.SR_MAVEN_PLUGIN_VERSION = SR_MAVEN_PLUGIN_VERSION;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public String getSHELL_FILE() {
        return SHELL_FILE;
    }

    public void setSHELL_FILE(String SHELL_FILE) {
        this.SHELL_FILE = SHELL_FILE;
    }

    public String getBROKER() {
        return BROKER;
    }

    public void setBROKER(String BROKER) {
        this.BROKER = BROKER;
    }

    public String getSCHEMA_URL() {
        return SCHEMA_URL;
    }

    public void setSCHEMA_URL(String SCHEMA_URL) {
        this.SCHEMA_URL = SCHEMA_URL;
    }

    public String getTOPIC() {
        return TOPIC;
    }

    public void setTOPIC(String TOPIC) {
        this.TOPIC = TOPIC;
    }

    public String getADDITIONAL_PROPERTIES() {
        return ADDITIONAL_PROPERTIES;
    }

    public void setADDITIONAL_PROPERTIES(String ADDITIONAL_PROPERTIES) {
        this.ADDITIONAL_PROPERTIES = ADDITIONAL_PROPERTIES;
    }

    @Override
    public String toString() {
        return "DeriveSchemaVariables{" +
                "MESSAGE='" + MESSAGE + '\'' +
                ", DATA_TYPE='" + DATA_TYPE + '\'' +
                ", OUTPUT_FILE='" + OUTPUT_FILE + '\'' +
                ", SR_MAVEN_PLUGIN_VERSION='" + SR_MAVEN_PLUGIN_VERSION + '\'' +
                ", PATH='" + PATH + '\'' +
                ", SHELL_FILE='" + SHELL_FILE + '\'' +
                ", BROKER='" + BROKER + '\'' +
                ", SCHEMA_URL='" + SCHEMA_URL + '\'' +
                ", TOPIC='" + TOPIC + '\'' +
                ", ADDITIONAL_PROPERTIES='" + ADDITIONAL_PROPERTIES + '\'' +
                '}';
    }
}
