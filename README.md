# platform-sales

[![Build Status](https://travis-ci.org/Sergey34/platform-sales.svg?branch=feature%2Fmarket-dev)](https://travis-ci.org/Sergey34/platform-sales)

This is my final qualification master's work. 
"Development platform for the sale of products and services with a distributed architecture"

functions:
- CRUD for products, categories and orders +
- product cart +
- account creation +
- add/remove users to account +
- internal messaging (communication-service->rabbit->internal-sender) +
- external messaging (communication-service->rabbit->external-sender(telegram, vk)) -
- calculation companion-products (statistic-service->kafka->spark->kafka->elk) +- need add logstash config
- Customer churn forecast (statistic-service->spark MLib) -
- the history of viewed products (statistic-service->kafka->spark->kafka->elk) +- need add logstash config
- load balancing (ribbon) +
- routing (api gateway - zuul) +
- external configuration +
- service discovery (eureka) +
- security (auth2.0) +
- authorisation with social networks -
- log analytics +- need create dashboards
- sales analysis -



to do:
- sales analysis
- authorisation with social networks
- external messaging (communication-service->rabbit->external-sender(telegram, vk))
- create logstash config for companion-products and history of viewed products
- Customer churn forecast (statistic-service->spark MLib)

![](https://github.com/Sergey34/platform-sales/blob/feature/market-dev/img/011.png)
![](https://github.com/Sergey34/platform-sales/blob/feature/market-dev/img/012.png)
![](https://github.com/Sergey34/platform-sales/blob/feature/market-dev/img/013.png)
