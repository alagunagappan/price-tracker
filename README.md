# Product Price Tracker with Custom Alerts

A Spring Boot application that allows users to track product prices and receive notifications when prices drop below their desired target price.

## Features

- **Price Alert Creation**: Users can set alerts for product URLs
- **Scheduled Price Checks**: Configurable check frequencies (daily, morning, night)
- **Notification System**: Configured to print logs for MVP
- **H2 Integration**: Persistent storage for alerts and products
- **Quartz Scheduler**: Reliable job scheduling for price checks

## Technologies Used

- Java 17
- Spring Boot 3.4.4
- Quartz Scheduler
- Maven

## Prerequisites

- Java 17 JDK
- Maven 3.8+
- IDE (IntelliJ, Eclipse, or VS Code)

## Git repo
git clone https://github.com/alagunagappan/price-tracker.git

## API Endpoints
| Method  |   Endpoint   |        Description        |
|:-------:|:------------:|:-------------------------:|
|  POST   | /api/alerts  | Create a new price alert  |

## Sample Requests
```
curl --location 'http://localhost:8081/api/alerts' \
--header 'Content-Type: application/json' \
--data-raw '{
"productUrl": "http://example.com/product5",
"price": 250.00,
"email": "user@domain1.com",
"frequency": "MORNING"
}'
```

```
curl --location 'http://localhost:8081/api/alerts' \
--header 'Content-Type: application/json' \
--data-raw '{
"productUrl": "http://example.com/product1",
"price": -20.00,
"email": "userln@domain1.com",
"frequency": "NIGHT"
}'
```

## Notes
- The static data is being read from products.json.
- To test the notification service, any of the scheduler's interval can be set to minutes.


