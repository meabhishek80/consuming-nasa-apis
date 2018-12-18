package com.practise.nasa.satellite.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.practise.nasa.satellite.enums.MESSAGES;
import com.practise.nasa.satellite.exception.ClientException;

public class HTTPClient implements Client<String, String> {
    Logger log = Logger.getLogger(HTTPClient.class.getName());

    @Override
    public String get(String urlString) throws ClientException {
        String output = null;
        HttpURLConnection conn = null;

        URL url;
        try {
            url = new URL(urlString);
            System.out.println("Calling " + urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(Client.Params.GET);
            conn.setRequestProperty(Client.Params.HEADER_ACCEPT, Client.Params.APP_JSON);
            conn.setReadTimeout(Client.Params.READ_TIME_OUT);
            if (conn.getResponseCode() != Client.Params.HTTPCODES.TWO_HUNDERED.getVal()) {
                throw new ClientException("Failed : HTTP error code : " + conn.getResponseCode());
            }
        } catch (IOException e) {
            disconnect(conn);
            throw new ClientException(MESSAGES.SOMETHING_WRONG.getVal() + urlString + "\n" + e.getMessage(), e);
        }
        output = readInput(urlString, conn);
        return output;

    }

    private String readInput(final String urlString, final HttpURLConnection conn) {
        String output = StringUtils.EMPTY;
        try (BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())))) {
            StringBuilder stringBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                stringBuilder.append(output);
            }
            output = stringBuilder.toString();
        } catch (IOException e) {
            System.err.println(MESSAGES.SOMETHING_WRONG.getVal() + urlString + "\n" + e.getMessage());
            throw new ClientException(MESSAGES.SOMETHING_WRONG.getVal() + urlString + "\n" + e.getMessage(), e);
        } finally {
            disconnect(conn);

        }
        return output;
    }

    private void disconnect(HttpURLConnection conn) {
        if (conn != null) {
            conn.disconnect();
        }
    }
}
