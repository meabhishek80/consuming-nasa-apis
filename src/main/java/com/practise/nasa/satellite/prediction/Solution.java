package com.practise.nasa.satellite.prediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

abstract class PredictionTemplate {
    public void flyby(Double latitude, Double longitude, PredictionStrategy predictionStrategy) {
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

    public abstract void predict(PredictionStrategy predictionStrategy, List<Result> results);
}

public class Solution extends PredictionTemplate {
    /*
     * a) What other prediction methods could you potentially use other than using the average?
     * 
     * 1.) Delta from Dates --> Delta --> Delta till only one delta remains. 2.) Median of Frquently chaning Delta
     * (Sorted) of Deltas. 3.) Calculate Diff Days from Current Date and Then Find Delta 4.) Apply Steps 1 and 2 for 3
     * 
     */
    /*
     * b) What test cases would you use to validate the solution? Junits s 1.) Input Argument for Lat and Longs edge
     * Cases - Float.Nan Float Max Min 2.) HTTP CLient / Null Invalid URL / Service Down / 3.) Parser - Invalid / Null /
     * Missing / Date Formats 4/) Prediction - Add Days / Sparse Dates / Besides Happy Case
     */
    public static void main(String[] args) {
        try {
            Double lon = Double.valueOf(System.getProperty("lon", "100.75"));
            Double lat = Double.valueOf(System.getProperty("lat", "1.5"));
            new Solution().flyby(lat, lon, PredictionStrategy.DAYS_AVERAGE_ELAPSED_STRATEGY);
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
    public void predict(PredictionStrategy predictionStrategy, List<Result> results) {
        predictionStrategy.predict(results);
    }

}

enum PredictionStrategy {

    DAYS_AVERAGE_ELAPSED_STRATEGY {
        @Override
        public void predict(List<Result> results) {
            Long deltaSum = Long.valueOf(0);
            for (int i = 0; i < results.size() - 1; i++) {
                // System.out.println(Parser.delta(results.get(i).getDate(),
                // results.get(i + 1).getDate()));
                deltaSum = deltaSum + Parser.delta(results.get(i).getDate(), results.get(i + 1).getDate());
            }
            System.out.println(
                    "Next time:" + Parser.simpleDateFormat.format(Parser.addDays(deltaSum / (results.size() - 1))));
        }
    };

    public abstract void predict(List<Result> results);

}

// Test Cases needed for Time out / Service Down / Invalid Response
class HTTPClient implements Client<String, String> {
    Logger log = Logger.getLogger(HTTPClient.class.getName());

    @Override
    public String get(String urlString) throws ClientException {
        String output = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlString);
            System.out.println("Calling " + urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(Client.Params.GET);
            conn.setRequestProperty(Client.Params.HEADER_ACCEPT, Client.Params.APP_JSON);
            if (conn.getResponseCode() != Client.Params.HTTPCODES.TWO_HUNDERED.getVal()) {
                throw new ClientException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder stringBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                stringBuilder.append(output);
            }
            output = stringBuilder.toString();
        } catch (IOException e) {
            System.err.println(MESSAGES.SOMETHING_WRONG.getVal() + urlString + e.getMessage());
            throw new ClientException(MESSAGES.SOMETHING_WRONG.getVal() + urlString + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("System Exception - Unable to close Buffer");
                    throw new ClientException("System Exception - Unable to close Buffer", e);
                }
            }
        }
        return output;

    }
}

interface Validators {

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

    static void validateClientResponse(String val) throws ValidationException {
        validateString(val);
        if (val.contains("error")) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + val);
        }

    }

    static void validateString(String val) throws ValidationException {
        if (!(val != null && !val.isEmpty())) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + val);
        }
    }

    static void validate(Stats val) throws ValidationException {
        if (Objects.isNull(val)) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name());
        } else if (Objects.isNull(val.getResults())) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name() + "Results List is Null");
        } else if (val.getResults().isEmpty() || val.getResults().size() < 2) {
            throw new ValidationException(
                    MESSAGES.INVALID_ARGUMENTS.name() + "Results List is Empty or Of Size less than 2");
        }
    }

    static void validateObjects(Objects val) throws ValidationException {
        if (val == null) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS.name());
        }
    }

}

interface Client<K, V> {
    V get(K k1) throws ClientException;

    interface Params {
        // Should be read from a property file.
        String URL = "https://api.nasa.gov/planetary/earth/assets?";
        String QUERY_PARAM_SKEY = "api_key=9Jz6tLIeJ0yY9vjbEUWaH9fsXA930J9hspPchute";
        String QUERY_PARAM_LONGITUTDE = "lon=";
        String QUERY_PARAM_LATTITUDE = "lat=";
        String QUERY_PARAM_SEPERATOR_QUESTION_MARK = "&";

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

            public void setVal(int val) {
                this.val = val;
            }

            private HTTPCODES(int val) {
                this.val = val;
            }

        }

    }

    static String buildURL(Double latitude, Double longitude) {
        StringBuilder builder = new StringBuilder();
        builder.append(Params.URL).append(Params.QUERY_PARAM_LONGITUTDE).append(String.valueOf(longitude))
                .append(Params.QUERY_PARAM_SEPERATOR_QUESTION_MARK).append(Params.QUERY_PARAM_LATTITUDE)
                .append(String.valueOf(latitude)).append(Params.QUERY_PARAM_SEPERATOR_QUESTION_MARK)
                .append(Params.QUERY_PARAM_SKEY);
        return builder.toString();
    }

}

enum MESSAGES {

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

interface Parser<K, V> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    Calendar calendar = new GregorianCalendar();
    Date currentDate = new Date();

    interface JSONParams {
        String COUNT = "count";
        String RESULTS = "results";
        String DATE = "date";
        String ID = "id";
        String INVALID = "invalid";

    }

    V parse(K k) throws ParserException;

    static Date toDate(String date) throws ValidationException {
        // System.out.println(date);
        Date parsedDate = null;
        Validators.validateString(date);
        try {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            parsedDate = simpleDateFormat.parse(date);
        } catch (java.text.ParseException e) {
            throw new ValidationException(MESSAGES.INVALID_ARGUMENTS + "Date" + date);
        }
        // System.out.println("Recieved:"+date+"-->"+simpleDateFormat.format(parsedDate));
        return parsedDate;
    }

    static long delta(Date date1, Date date2) {
        return ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
    }

    static Date addDays(long days) {
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, Long.valueOf(days).intValue());
        return calendar.getTime();
    }
}

@SuppressWarnings("unchecked")
class StatsDataParser implements Parser<String, Stats> {

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
        } catch (IOException | ParseException e) {
            System.out.println("" + e.getMessage());
            throw new ParserException(MESSAGES.PARSE_ERROR_MESSAGE.getVal(), e);
        }
        // System.out.println(info);
        return info;
    }

}

class Stats implements Serializable {

    private Long count;
    private List<Result> results = null;
    private final static long serialVersionUID = 460539461792981326L;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Stats withCount(Long count) {
        this.count = count;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Stats withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("count", count).append("results", results).toString();
    }

}

class Result implements Serializable {

    private Date date;
    private String id;
    private final static long serialVersionUID = -3535993830592851852L;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Result withDate(Date date) {
        this.date = date;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Result withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("date", date).append("id", id).toString();
    }

}

class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 7045483476273857900L;
    // Status Codes and Messages
    // Needed For Business Decisions andJunits

    public BusinessException() {
        super();

    }

    public BusinessException(String paramString, Throwable paramThrowable, boolean paramBoolean1,
            boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);

    }

    public BusinessException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);

    }

    public BusinessException(String paramString) {
        super(paramString);

    }

    public BusinessException(Throwable paramThrowable) {
        super(paramThrowable);

    }
}

class ValidationException extends BusinessException {

    /**
     * 
     */
    private static final long serialVersionUID = 5770242470121818060L;

    public ValidationException() {
        super();

    }

    public ValidationException(String paramString, Throwable paramThrowable, boolean paramBoolean1,
            boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);

    }

    public ValidationException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);

    }

    public ValidationException(String paramString) {
        super(paramString);

    }

    public ValidationException(Throwable paramThrowable) {
        super(paramThrowable);

    }
}

class ServiceException extends BusinessException {

    private static final long serialVersionUID = -2292123407413311538L;

    public ServiceException() {
        super();

    }

    public ServiceException(String paramString, Throwable paramThrowable, boolean paramBoolean1,
            boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);

    }

    public ServiceException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);

    }

    public ServiceException(String paramString) {
        super(paramString);

    }

    public ServiceException(Throwable paramThrowable) {
        super(paramThrowable);

    }

}

class ClientException extends BusinessException {

    public ClientException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ClientException(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
        // TODO Auto-generated constructor stub
    }

    public ClientException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
        // TODO Auto-generated constructor stub
    }

    public ClientException(String paramString) {
        super(paramString);
        // TODO Auto-generated constructor stub
    }

    public ClientException(Throwable paramThrowable) {
        super(paramThrowable);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = -5978888355474202897L;

}

class ParserException extends BusinessException {

    public ParserException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ParserException(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
        super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
        // TODO Auto-generated constructor stub
    }

    public ParserException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
        // TODO Auto-generated constructor stub
    }

    public ParserException(String paramString) {
        super(paramString);
        // TODO Auto-generated constructor stub
    }

    public ParserException(Throwable paramThrowable) {
        super(paramThrowable);
    }

    private static final long serialVersionUID = -5241208536812190116L;
}