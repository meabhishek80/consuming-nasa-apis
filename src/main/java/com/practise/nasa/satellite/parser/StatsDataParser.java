package com.practise.nasa.satellite.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.practise.nasa.satellite.domain.Result;
import com.practise.nasa.satellite.domain.Stats;
import com.practise.nasa.satellite.enums.MESSAGES;
import com.practise.nasa.satellite.exception.ParserException;
import com.practise.nasa.satellite.exception.ValidationException;

@SuppressWarnings("unchecked")
public class StatsDataParser implements Parser<String, Stats> {

    @Override
    public Stats parse(String jsonText) throws ParserException {
        // System.out.println("Parsing Data: " + jsonText);
        Stats info = new Stats();
        List<Result> returns = new ArrayList<>();
        try {
            if (jsonText != null && !jsonText.isEmpty()) {
                JSONParser parser = new JSONParser();
                Object object = parser.parse(new StringReader(jsonText));
                JSONObject jsonObject = (JSONObject) object;
                info.withCount((Long) jsonObject.getOrDefault(Parser.JSONParams.COUNT, 0));
                JSONArray jsonArray = (JSONArray) jsonObject.get(Parser.JSONParams.RESULTS);
                Iterator<JSONObject> iterator = jsonArray.iterator();
                while (iterator.hasNext()) {
                    jsonObject = iterator.next();
                    Result returnn = new Result()
                            .withDate(Parser.toDate((String) jsonObject.get(Parser.JSONParams.DATE)));
                    returns.add(returnn);
                }
                info.withResults(returns);
            }
        } catch (IOException | ParseException | ValidationException e) {
            System.out.println("" + e.getMessage());
            throw new ParserException(MESSAGES.PARSE_ERROR_MESSAGE.getVal(), e);
        }
        // System.out.println(info);
        return info;
    }

}