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
        "speaker" : "John",
        "command": "CNN"
    },
    {
        "speaker" : "John",
        "command": "CNN"
    },
    {
        "speaker" : "John",
        "command": "BBC"
    }],

    "new Hampshire" : [{
        "speaker" : "John",
        "command": "Game Of throwns"
    },
    {
        "speaker" : "John",
        "command": "CNBC"
    },
    {
        "speaker" : "John",
        "command": "CNBC"
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
