package com.practise.nasa.satellite.prediction;

import java.util.List;

import com.practise.nasa.satellite.client.Client;
import com.practise.nasa.satellite.client.HTTPClient;
import com.practise.nasa.satellite.domain.Result;
import com.practise.nasa.satellite.domain.Stats;
import com.practise.nasa.satellite.enums.MESSAGES;
import com.practise.nasa.satellite.exception.BusinessException;
import com.practise.nasa.satellite.exception.ServiceException;
import com.practise.nasa.satellite.parser.Parser;
import com.practise.nasa.satellite.parser.StatsDataParser;
import com.practise.nasa.satellite.prediction.enums.PREDICTIONSTRATEGY;

public class Solution extends PredictionTemplate {

    public static void main(String[] args) {
        try {
            Double lon = Double.valueOf(System.getProperty("lon", "100.75"));
            Double lat = Double.valueOf(System.getProperty("lat", "1.5"));
            new Solution().flyby(lat, lon, PREDICTIONSTRATEGY.DAYS_AVERAGE_ELAPSED_STRATEGY);
        } catch (NumberFormatException e) {
            System.err.println(MESSAGES.INVALID_ARGUMENTS_NUMBER_FORMAT + e.getMessage());
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
            throw new BusinessException(e);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String call(Double latitude, Double longitude) {
        Client<String, String> client = new HTTPClient();
        String response = client.get(Client.buildURL(latitude, longitude));
        return response;
    }

    @Override
    public Stats parse(String response) {
        Parser<String, Stats> parser = new StatsDataParser();
        return parser.parse(response);
    }

    @Override
    public void predict(PREDICTIONSTRATEGY predictionStrategy, List<Result> results) {
        predictionStrategy.predict(results);
    }

}
