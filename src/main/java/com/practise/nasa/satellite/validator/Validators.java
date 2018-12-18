package com.practise.nasa.satellite.validator;

import java.util.Objects;

import com.practise.nasa.satellite.domain.Stats;
import com.practise.nasa.satellite.enums.MESSAGES;
import com.practise.nasa.satellite.exception.ValidationException;

public interface Validators {

    static void validatLatLong(Double lat, Double lon) {
        validatLat(lat);
        validatLong(lon);
    }

    static void validatLat(Double lat) {
        if (lat < Double.valueOf(-90) || lat > Double.valueOf(90) || Objects.isNull(lat)) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + "Latitude in degrees is -90 and +90 f");
        }
    }

    static void validatLong(Double lat) {
        if (lat < Double.valueOf(-180) || lat > Double.valueOf(180) || Objects.isNull(lat)) {
            throw new ValidationException(
                    MESSAGES.INVALID_ARGUMENTS.name() + "Longitude is in the range -180 and +180");
        }
    }

    static void validateClientResponse(String val) {
        validateString(val);
        if (val.contains("error")) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + val);
        }

    }

    static void validateString(String val) {
        if (!(val != null && !val.isEmpty())) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + val);
        }
    }

    static void validate(Stats val) {
        if (Objects.isNull(val)) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name());
        } else if (Objects.isNull(val.getResults())) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + "Results List is Null");
        } else if (val.getResults().isEmpty() || val.getResults().size() < 2) {
            throw new ValidationException(
                    MESSAGES.INVALID_ARGUMENTS.name() + "Results List is Empty or Of Size less than 2");
        }
    }

    static void validateObjects(Objects val) {
        if (val == null) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name());
        }
    }

}
