package com.practise.nasa.satellite.prediction.enums;

import java.util.List;

import com.practise.nasa.satellite.domain.Result;
import com.practise.nasa.satellite.parser.Parser;

public enum PREDICTIONSTRATEGY {

    DAYS_AVERAGE_ELAPSED_STRATEGY {
        @Override
        public void predict(List<Result> results) {
            Long deltaSum = Long.valueOf(0);
            for (int i = 0; i < results.size() - 1; i++) {
                deltaSum = deltaSum + Parser.delta(results.get(i).getDate(), results.get(i + 1).getDate());
            }
            System.out.println(
                    "Next time:" + Parser.simpleDateFormat.format(Parser.addDays(deltaSum / (results.size() - 1))));
        }
    };

    public abstract void predict(List<Result> results);

}