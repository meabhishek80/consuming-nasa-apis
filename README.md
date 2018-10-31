# Consuming-Nasa-apis

Utilise NASA APIs, Satellite Data to Predict the Next Satellite Position at Given `latitude` and `longitude`.

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
* Only Use Basic Dependencies (This helps getting CoderPad Ready!!.).

    * json-simple for parsing/encoding JSON.
    * guava provides immutable collections and other handy utility classes.
    * Apache Commons Lang for assorted utilities.
    * JUnit
    * jMock
    
 ### Suprise!! Are you getting this ?
 
 ```
 Caused by: javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.ssl.Alerts.getSSLException(Unknown Source)
	at sun.security.ssl.SSLSocketImpl.fatal(Unknown Source)
	at sun.security.ssl.Handshaker.fatalSE(Unknown Source)
	at sun.security.ssl.Handshaker.fatalSE(Unknown Source)
	at sun.security.ssl.ClientHandshaker.serverCertificate(Unknown Source)
	at sun.security.ssl.ClientHandshaker.processMessage(Unknown Source)
	at sun.security.ssl.Handshaker.processLoop(Unknown Source)
	at sun.security.ssl.Handshaker.process_record(Unknown Source)
	at sun.security.ssl.SSLSocketImpl.readRecord(Unknown Source)
	at sun.security.ssl.SSLSocketImpl.performInitialHandshake(Unknown Source)
	at sun.security.ssl.SSLSocketImpl.startHandshake(Unknown Source)
	at sun.security.ssl.SSLSocketImpl.startHandshake(Unknown Source)
	at sun.net.www.protocol.https.HttpsClient.afterConnect(Unknown Source)
	at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(Unknown Source)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(Unknown Source)
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(Unknown Source)
	at java.net.HttpURLConnection.getResponseCode(Unknown Source)
	at sun.net.www.protocol.https.HttpsURLConnectionImpl.getResponseCode(Unknown Source)
	at com.practise.nasa.satellite.prediction.HTTPClient.get(Solution.java:134)
	... 5 more
Caused by: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.validator.PKIXValidator.doBuild(Unknown Source)
	at sun.security.validator.PKIXValidator.engineValidate(Unknown Source)
	at sun.security.validator.Validator.validate(Unknown Source)
	at sun.security.ssl.X509TrustManagerImpl.validate(Unknown Source)
	at sun.security.ssl.X509TrustManagerImpl.checkTrusted(Unknown Source)
	at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(Unknown Source)
	... 20 more
Caused by: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at sun.security.provider.certpath.SunCertPathBuilder.build(Unknown Source)
	at sun.security.provider.certpath.SunCertPathBuilder.engineBuild(Unknown Source)
	at java.security.cert.CertPathBuilder.build(Unknown Source)
	... 26 more
```
That means you need add a certificate - Could be of Chrome/Firefix to the JRE trusted security certificates.

https://stackoverflow.com/questions/21076179/pkix-path-building-failed-and-unable-to-find-valid-certification-path-to-requ

