# Consuming-Nasa-apis

Simple Project To That feeds on NASA APIs  to Predict the Next Satellite Position at Given latitude and longitude.


## Intro 

NASA’s engineering mission is one of it's Kind. But beyond that, How can one use data from NASA to make 
predictions about the future.Solving global warming is unfortunately outside the scope, so goal here is 
somewhat simpler:

``` 
Use NASA’s public HTTP APIs to  predicts the next time a satellite image will betaken of a 
certain location.
```

This can be handy when trying to get picture onto online mapping applications like Google Maps. :-) 

### More Info

* Pull up the documentation for the API using: https://api.nasa.gov/api.html 
* When there is enough data to do so, make a prediction for when the next picture will be taken. 
* The prediction will have a date and time based on the average time between successive pictures. 
* Using  https://api.nasa.gov/api.html#assets API to get the information needed to compute this. 
* NASA documentation mentions that ``` avg_time_delta ```which is usually close to 16 days, 
  but let's calculate it since it’s not always the same.

### Output 

In pseudocode, the prediction method might look like: print 

``` Next time = (last_date + avg_time_delta) ```

### FAQs

### Any Fun locations ? 
Here are a few:
Fun location Latitude Longitude 
* Grand Canyon 36.098592 -112.097796 
* Niagara Falls 43.078154 -79.075891 
* Four Corners Monument 36.998979 -109.045183
