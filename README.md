
# Consuming-Nasa-apis

[![GitHub issues](https://img.shields.io/github/issues/Akash-Mittal/consuming-nasa-apis.svg)](https://github.com/Akash-Mittal/consuming-nasa-apis/issues)
[![GitHub forks](https://img.shields.io/github/forks/Akash-Mittal/consuming-nasa-apis.svg)](https://github.com/Akash-Mittal/consuming-nasa-apis/network)
[![GitHub license](https://img.shields.io/github/license/Akash-Mittal/consuming-nasa-apis.svg)](https://github.com/Akash-Mittal/consuming-nasa-apis/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/Akash-Mittal/consuming-nasa-apis.svg)](https://github.com/Akash-Mittal/consuming-nasa-apis/stargazers)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=Akash-Mittal_consuming-nasa-apis&metric=alert_status)](https://sonarcloud.io/dashboard?id=Akash-Mittal_consuming-nasa-apis)
[![Build Status](https://travis-ci.org/Akash-Mittal/consuming-nasa-apis.svg?branch=master)](https://travis-ci.org/Akash-Mittal/consuming-nasa-apis)


Consume NASA APIs,Use Satellite Route Data to Predict the Next Satellite Position at Given `latitude` and `longitude`.

## Intro 

NASA's engineering mission is one of it's kind. But beyond that, How can one use data from NASA to make 
predictions about the future.Solving global warming is unfortunately outside the scope, so goal here is 
somewhat simpler:

``` 
Use NASA's public HTTP APIs to predicts the next time a satellite image will be taken of a certain location.
```

### Application

This can be handy when trying to get picture onto online mapping applications like Google Maps.

### More Info

* Pull up the documentation for the API using: https://api.nasa.gov/api.html 
* When there is enough data to do so, make a prediction for when the next picture will be taken. 
* The prediction will have a date and time based on the average time between successive pictures. 
* Using  https://api.nasa.gov/api.html#assets API to get the information needed to compute this. 
* NASA documentation mentions that ``` avg_time_delta ``` which is usually close to 16 days, 
  but let's calculate it since it's not always the same.

### Input Function

``` void flyby(double latitude, double longitude) ```

### Output 

In pseudocode, the prediction method might look like:  

``` Next time = (last_date + avg_time_delta) ```

#### Any Fun locations ? 

Here are a few:

* Grand Canyon 36.098592 -112.097796 
* Niagara Falls 43.078154 -79.075891 
* Four Corners Monument 36.998979 -109.045183

### Goal

* Write Production Grade Code.
* Only Use Basic Dependencies (This helps getting `CoderPad` Ready!!.).

    * json-simple for parsing/encoding JSON.
    * guava provides immutable collections and other handy utility classes.
    * Apache Commons Lang for assorted utilities.
    * JUnit
    * jMock
 
