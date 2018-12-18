package com.practise.nasa.satellite.client;

public interface Params {
    // Should be read from a property file.
    String URL = "https://api.nasa.gov/planetary/earth/assets?";
    String QUERY_PARAM_SKEY = "api_key=9Jz6tLIeJ0yY9vjbEUWaH9fsXA930J9hspPchute";
    String QUERY_PARAM_LONGITUTDE = "lon=";
    String QUERY_PARAM_LATTITUDE = "lat=";
    String QUERY_PARAM_SEPERATOR_AMPERSAND = "&";

    String GET = "GET";

    String HEADER_ACCEPT = "Accept";
    String APP_JSON = "application/json";
    int READ_TIME_OUT = 3000;

    enum HTTPCODES {
        TWO_HUNDERED(200);
        private int val;

        public int getVal() {
            return val;
        }

        private HTTPCODES(int val) {
            this.val = val;
        }

    }

}