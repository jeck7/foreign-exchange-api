# 💱 Spring Boot Currency Conversion API

This is a self-contained Spring Boot RESTful API application that provides real-time foreign exchange functionality using external FX providers like exchangerate.host, fixer.io, and currencylayer.com.

## 📦 Features

- 🔁 Currency exchange rate lookup
- 💸 Currency conversion with transaction ID
- 📜 Conversion history endpoint (filter by ID or date)
- 🌐 External FX rate integration (configurable)
- 💥 Graceful error handling
- 🧪 Unit testing support
- 🧰 API documentation with Swagger UI
- ⚡ In-memory caching for performance
- 🧠 H2 in-memory database for development
- 🐳 Docker containerization

---

## 🚀 Endpoints

### 1. 📈 Exchange Rate
**GET** `/api/rate?from=USD&to=EUR`

**Response:**
```json
{
  "rate": 0.91
}
```

---

### 2. 💱 Currency Conversion
**POST** `/api/convert`

**Request:**
```json
{
  "from": "USD",
  "to": "EUR",
  "amount": 100
}
```

**Response:**
```json
{
  "transactionId": "c1234567-abc",
  "convertedAmount": 91.0,
  "rate": 0.91
}
```

---

### 3. 🕑 Conversion History
**GET** `/api/history?transactionId=c1234567-abc`  
**OR**  
**GET** `/api/history?date=2024-05-25`

**Response:**
```json
[
  {
    "transactionId": "c1234567-abc",
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "amount": 100.0,
    "convertedAmount": 91.0,
    "rate": 0.91,
    "timestamp": "2024-05-25T10:00:00"
  }
]
```

---

## ⚙️ Configuration

Edit `src/main/resources/application.yml`:

```yaml
exchange:
  provider: exchangeratehost # Options: exchangeratehost, fixer, currencylayer
  fixer:
    api-key: your_fixer_api_key
    url: http://data.fixer.io/api
  currencylayer:
    api-key: your_currencylayer_api_key
    url: http://api.currencylayer.com
```

---

## 🧪 Running Locally

### Using Maven
```bash
mvn spring-boot:run
```

### Or build JAR
```bash
mvn clean package
java -jar target/springboot-api-1.0.0.jar
```

---

## 🐳 Docker Support

### Build Docker Image
```bash
docker build -t currency-api .
```

### Run the container
```bash
docker run -p 8080:8080 currency-api
```

---

## 🧪 Testing

```bash
mvn test
```

---

## 📖 Swagger Documentation

Visit:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧠 In-Memory DB Console

Visit:  
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
JDBC URL: `jdbc:h2:mem:testdb`

---

## 📄 License

This project is provided as-is for educational and prototyping purposes.
