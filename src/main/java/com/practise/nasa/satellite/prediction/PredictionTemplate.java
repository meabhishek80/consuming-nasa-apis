package com.practise.nasa.satellite.prediction;

import java.util.List;

import com.practise.nasa.satellite.domain.Result;
import com.practise.nasa.satellite.domain.Stats;
import com.practise.nasa.satellite.enums.MESSAGES;
import com.practise.nasa.satellite.exception.ClientException;
import com.practise.nasa.satellite.exception.ParserException;
import com.practise.nasa.satellite.exception.ServiceException;
import com.practise.nasa.satellite.exception.ValidationException;
import com.practise.nasa.satellite.prediction.enums.PREDICTIONSTRATEGY;
import com.practise.nasa.satellite.validator.Validators;

public abstract class PredictionTemplate {
    public void flyby(Double latitude, Double longitude, PREDICTIONSTRATEGY predictionStrategy) {
        String response = null;
        try {
            Validators.validatLatLong(latitude, longitude);
            response = call(latitude, longitude);
            Validators.validateClientResponse(response);
            Stats object = parse(response);
            Validators.validate(object);
            predict(predictionStrategy, object.getResults());
        } catch (ValidationException | ClientException e) {
            System.err.println(MESSAGES.UNKNOWN + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public abstract String call(Double latitude, Double longitude);

    public abstract Stats parse(String response) throws ParserException;

    public abstract void predict(PREDICTIONSTRATEGY predictionStrategy, List<Result> results);
}