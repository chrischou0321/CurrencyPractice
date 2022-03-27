# CurrencySample

A small java practice for RestTemplate, JPA & UnitTest,  base on SpringBoot & H2.

### Environments
- JDK: 1.8
- H2:  2.1.210
- SpringBoot: 2.1.12.RELEASE

##### application.properties
Change these properties to direct your h2 db or others. 

./src/main/resources/application.properties

```properties
spring.datasource.url=jdbc:h2:tcp://localhost/e:/Java_workspace/CurrencySample/h2Data/currency-sample
spring.datasource.username=sa
spring.datasource.password=
```
### H2 Schema
```roomsql
CREATE TABLE CURRENCY(
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    CODE VARCHAR(3) NOT NULL DEFAULT '',
    SYMBOL VARCHAR(10) NOT NULL DEFAULT '',
    DESCRIPTION VARCHAR(255),
    RATE DECFLOAT(10) NOT NULL DEFAULT 1,
    CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IDX_CURRENCY_SYMBOL ON CURRENCY(SYMBOL);

CREATE SEQUENCE CHRIS_CURRENCY_SEQUENCE START WITH 1 INCREMENT BY 1;
```

### The Script for Postman
Create environment parameter `base_url` before use.
```properties
# import file
{baseDir}/CurrencySample/doc/CurrencySample.postman_collection.json
```
##### Include
- api.coindesk
- CoindeskTransfered
- findOne
- AddCurrency
- UpdateCurrency
- DeleteCurrency

### Feature
- [ ] New API: getCurrencyList
- [ ] AOP manage controller's exceptions
- [ ] LoadingCache(?
- [ ] add ActionHistoryLog
- [ ] H2 in memory while testing
