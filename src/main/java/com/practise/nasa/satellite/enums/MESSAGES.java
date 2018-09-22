package com.practise.nasa.satellite.enums;

public enum MESSAGES {

    INVALID_ARGUMENTS_NUMBER_FORMAT(
            " Invalid arguments please enter numeric values \n Example: java -Dlat=134.11 -Dlong=353 -jar Solution"), INVALID_URL(
                    "Invalid URL: "), INVALID_HOST("Some thing Wrong With Url : "),

    SOMETHING_WRONG("Something wrong with URL: "), UNKNOWN("Uknown Issue with URL: "), INVALID_ARGUMENTS(
            ": "), UNABLE_TO_PARSE_DATE(""), PARSE_ERROR_MESSAGE("Unable to load or Parse  Json Data");

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    private String val;

    private MESSAGES(String val) {
        this.val = val;
    }
}
