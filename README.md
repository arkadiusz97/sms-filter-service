# Sms Filter Service
Just requirement task which consist:
* Service for checking if sms could be malicious in package com.github.arkadiusz97.sms_filter_service.smsfilterservice
* External service API Mock in package com.github.arkadiusz97.sms_filter_service.smsfilterservice.evaluate_sms_service_mock

# Requirements
```
Java 17
```

# Running application
```
./gradlew bootRun
```

# Testing aplication
Testing could be done by curl. Some test cases with expected results:

* Get reposne from external API mock that this phone number is unsafe.
```
curl.exe --location 'http://localhost:8080/evaluateSmsMock/evaluateSms' --header 'Content-Type:application/json' --data '{\"phoneNumber\": \"some-unsafe-number\"}'
```
Expected result:
```
{"mainThreatType":"MALWARE","confidenceLevel":"MEDIUM"}
```

* Get reposne from external API mock that this phone number is safe.
```
curl.exe --location 'http://localhost:8080/evaluateSmsMock/evaluateSms' --header 'Content-Type:application/json' --data '{\"phoneNumber\": \"123123123\"}'
```
Expected result:
```
{"mainThreatType":"THREAT_TYPE_UNSPECIFIED","confidenceLevel":"SAFE"}
```

* Get reposne from operator service that sms is safe.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"123123123\", \"recipient\": \"111\", \"message\": \"some message\"}'
```
Expected result:
```
{"smsServiceStatus":"SAFE"}
```

* Get reposne from operator service that service is not used by user.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"123123123\", \"recipient\": \"222\", \"message\": \"some message\"}'
```
Expected result:
```
{"smsServiceStatus":"SERVICE_NOT_USED_BY_USER"}
```

* Get reposne from operator service that operator's client(recipient from sms) doesn't exist.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"123123123\", \"recipient\": \"333\", \"message\": \"some message\"}'
```
Expected result:
```
{"smsServiceStatus":"USER_NOT_FOUND"}
```

* Get reposne from operator service that sms is unsafe.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"some-unsafe-number\", \"recipient\": \"111\", \"message\": \"some message\"}'
```
Expected result:
```
{"smsServiceStatus":"NOT_SAFE"}
```

* Get reposne from operator service that user disabled sms checking service.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"111\", \"recipient\": \"special-number\", \"message\": \"STOP\"}'
```
Expected result:
```
{"smsServiceStatus":"USER_STOP"}
```

* Get reposne from operator service that user enabled sms checking service.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"111\", \"recipient\": \"special-number\", \"message\": \"START\"}'
```
Expected result:
```
{"smsServiceStatus":"USER_START"}
```

* Get reposne from operator service that user command didn't change anything.
```
curl.exe --location 'http://localhost:8080/smsOperatorService/handleSms' --header 'Content-Type:application/json' --data '{\"sender\": \"111\", \"recipient\": \"special-number\", \"message\": \"other-message\"}'
```
Expected result:
```
{"smsServiceStatus":"NOTHING_CHANGED"}
```

# Architecture and additional assumptions
## Architecture
Application is in a monolith architecture. This architecture is chosen due simplicity which is significant to implement soultion as soon as possible.
In a real live implementation API mock would be separated to another Spring boot application.
Both API mock and operator service have 3 layers:
* Persistence layer
* Service layer
* Controller layer
## Additional assumptions
In real live production environment this application should have the following things, which weren't added due to lack of time:
* Unit and integration tests
* Logging events in application through log4j
* Additional errors handling
* API mock should be separated from operator service