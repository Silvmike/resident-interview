# README

This app reads file containing events from the file system, and records them.

Also, it provides average statistics on read events through the following API:

## API

### Request
```
GET http://localhost:8080/{eventType}/average?from={timestamp}&to={timestamp}
```
Where:
 * **eventType** is a type of event (e.g. 'earth')
 * **from** is a timestamp in seconds of the beginning of the interval
 * **to** is a timestamp in seconds of the end of the interval
 
### Response
```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sun, 24 Jan 2021 18:42:38 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "type": "earth",
  "value": 0.8026,
  "processedCount": 2334
}
```
Where:
 * **type** is a type of event requested
 * **value** is an average value for given interval
 * **processedCount** is a count of processed events
 
## How to run?

```
./mvnw spring-boot:run
```

### Supported profiles

* **H2** - enables in-memory H2-based storage for processed events (enabled by default)
* **MONGO** - enables app to use MongoDB for storage
* **LOCAL_MONGO** - enable app to start it's own MongoDB instance (should be used in conjunction with **MONGO**, requires Docker installed)
* **SAMPLE_FILE** - enables usage of **resident-samples.csv** as a source of events (enabled by default)

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=<comma separated list of profiles>
```

### Examples

**H2 + SAMPLE_FILE** (which is default)
```
./mvnw spring-boot:run -Dspring-boot.run.profiles="H2,SAMPLE_FILE"
```
or just
```
./mvnw spring-boot:run
```

**MONGO + LOCAL_MONGO + SAMPLE_FILE**
```
./mvnw spring-boot:run -Dspring-boot.run.profiles="MONGO,LOCAL_MONGO,SAMPLE_FILE"
```

## Also

If you're running **MONGO + LOCAL_MONGO**, you can find a message like this in your output:
```
22:24:01.062 [main] INFO  r.s.i.r.a.s.m.c.l.MongoDBReplicaSetFactoryBean - Starting MongoDB: [mongodb://localhost:49247/test&readPreference=primary]
```

This MongoDB URI is available, go ahead and connect it through the **mongo** shell!