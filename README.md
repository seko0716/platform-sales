# platform-sales

[![Build Status](https://travis-ci.org/Sergey34/platform-sales.svg?branch=feature%2Fmarket-dev)](https://travis-ci.org/Sergey34/platform-sales)

This is my final qualification master's work. 
"Development platform for the sale of products and services with a distributed architecture"

functions:
- CRUD for products, categories and orders +
- Product cart +
- Account creation +
- Add/remove users to account +
- Internal messaging (communication-service->rabbit->internal-sender) +
- External messaging (communication-service->rabbit->external-sender(telegram, vk)) -
- Calculation companion-products (statistic-service->kafka->spark->kafka->elk) +
- Customer churn forecast (statistic-service->spark MLib) -
- History of viewed products (statistic-service->kafka->kafka->elk) +
- Load balancing (ribbon) +
- Routing (api gateway - zuul) +
- External configuration +
- Service discovery (eureka) +
- Security (auth2.0) +
- Authorisation with social networks -
- Log analytics +- need create dashboards
- Sales analysis -



to do:
- Sales analysis
- Authorisation with social networks
- External messaging (communication-service->rabbit->external-sender(telegram, vk))
- Customer churn forecast (statistic-service->spark MLib)
- Create dashboards for log analytics

![](https://github.com/Sergey34/platform-sales/blob/feature/market-dev/img/011.png)
![](https://github.com/Sergey34/platform-sales/blob/feature/market-dev/img/012.png)
![](https://github.com/Sergey34/platform-sales/blob/feature/market-dev/img/013.png)
