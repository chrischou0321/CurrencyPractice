# CurrencySample

A small java practice for RestTemplate, JPA & UnitTest,  base on SpringBoot & H2.

### application.properties
Change these properties to direct your h2 db or others. 

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
```

### The Script for Postman
> CurrencySample / doc / CurrencySample.postman_collection.json
Change environment parameter base after import


### Feature
- [ ] Currency List from DB
- [ ] AOP manage exceptions
- [ ] LoadingCache
- [ ] ActionLogHistory
- [ ] H2 in memory
