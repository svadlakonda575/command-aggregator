# command-aggregator

#### About
This project is to demonstrate few spring boot components by solving a simple problem called command aggregator.

#### Software Stack
* Java 1.8
* Maven
* lombok
* H2 in memory database

#### How To Build
mvn clean install

#### How to Run

java -jar target\command-aggregator-0.0.1-SNAPSHOT.jar

#### Testing
[POST]
endpoint : localhost:8080/commands

Sample Request:
```
{
    "texas" : [{
        "speaker" : "virginia",
        "command": "first palce3"
    },
    {
        "speaker" : "virginia",
        "command": "first palce2"
    },
    {
        "speaker" : "virginia",
        "command": "first palce2"
    }],

    "new Hampshire" : [{
        "speaker" : "virginia",
        "command": "first palce1"
    },
    {
        "speaker" : "virginia",
        "command": "first palce1"
    },
    {
        "speaker" : "virginia",
        "command": "first palce1"
    }]
}
```

Sample response:

```
{
    "new hampshire": {
        "mostFrequentCommand": "cnbc",
        "startProcessTime": 1630329507641,
        "stopProcessTime": 1630329507679
    },
    "topCommandsNationally": [
        "cnn",
        "cnbc",
        "game of throwns"
    ],
    "texas": {
        "mostFrequentCommand": "cnn",
        "startProcessTime": 1630329507393,
        "stopProcessTime": 1630329507641
    }
}
```
you can see h2 db console at: http://localhost:8080/h2-console
