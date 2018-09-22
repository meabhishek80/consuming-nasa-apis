# consuming-nasa-apis
Simple Project To consume NASA APIs and Parsing Results Using json-simple to Predict the Next Satellite at Given latitude and longitude.


### Intro 

I Admire NASA’s engineering mission. 
But beyond that, I can use data from NASA to make predictions about the future.
Solving global warming is unfortunately outside the scope, so my goal is somewhat simpler: 
use NASA’s public HTTP APIs to create a function which predicts the next time a satellite image will be
taken of a certain location. 
This can be handy when trying to get picture onto online mapping applications like Google Maps. :-) 

### Objective

You need to implement this in Java and make sure your code is production quality, well
designed, and easy to read. 

### Steps 

Pull up the documentation for the API using: https://api.nasa.gov/api.html 
Would need an API key in order to query the data from NASA. 
Implement a function flyby() whose method signature looks like this 

void flyby(double latitude, double longitude) 

When thereis enough data to do so, the function should print a prediction for when the
next picture will be taken. 
The prediction should have a date and time based on the average time between successive pictures. 

### Output 

In pseudocode, the prediction method might look like: print 

“Next time: ” + (last_date + avg_time_delta) 

Using  https://api.nasa.gov/api.html#assets API to get the information needed to compute this. 
NASA documentation mentions that avg_time_delta is usually close to 16 days, but let's calculate it since it’s not always the same.

### FAQs

Q: Any Fun locations ? 
Here are a few:
Fun location Latitude Longitude 
Grand Canyon 36.098592 -112.097796 
Niagara Falls 43.078154 -79.075891 
Four Corners Monument 36.998979 -109.045183 

