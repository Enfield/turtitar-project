[![Build Status](https://travis-ci.com/Enfield/turtitar-project.svg?branch=master)](https://travis-ci.com/Enfield/turtitar-project)
[![Maintainability](https://api.codeclimate.com/v1/badges/1748eabe39c0591067fa/maintainability)](https://codeclimate.com/github/Enfield/turtitar-project/maintainability)
[![codecov](https://codecov.io/gh/Enfield/turtitar/branch/master/graph/badge.svg)](https://codecov.io/gh/Enfield/turtitar-project)

# Turtitar project
> A simple web service for money transfers between accounts 

## FAQ

*A: Why Turtitar? What does it means?*  
*Q: I really don't know, i always use pokemon name generator for project names* ¯\\_(ツ)\_/¯  

*A: What technology stack you use?*  
*Q: Jersey + Grizzly + H2Database*

*A: What about concurrency? Is it thread-safe?*  
*Q: The High Concurrency strategy used: https://www.ibm.com/developerworks/java/library/j-ts5/index.html (TL;DR; 'SELECT ... FOR UPDATE')*

*A: Why only 91% coverage? Why not 100%?*  
*Q: It's only 11-lines of untested code (click badges for proof), six in main class and two is a annotation for example.* 

## Installation

```sh
git clone git@github.com:Enfield/turtitar.git
mvn -f turtitar clean verify package
java -cp ./turtitar/target/turtitar-1.0.jar pro.suslov.Main
```
## Usage example
Api is very simple and bulletproof.
### Create account
```http
POST /api/v1/accounts HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*
Content-Length: 24

 {
    "amount" : 99.12345
 }

HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 63

{
    "amount": 99.12345,
    "id": "79837f37-a033-4f8e-b7ca-1a99149cdbd8"
}
```
### Get account
```http
GET /api/v1/accounts/79837f37-a033-4f8e-b7ca-1a99149cdbd8 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*
Content-Length: 0
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 62

{
    "amount": 99.1235,
    "id": "79837f37-a033-4f8e-b7ca-1a99149cdbd8"
}

```
### Create transaction
```http
POST /api/v1/transactions HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Accept: */*
Content-Length: 117
{
    "from":"d94f38d6-0639-4589-8ea8-64492d6f0a42",
    "to":"79837f37-a033-4f8e-b7ca-1a99149cdbd8",
    "amount" : 15.9547
}

HTTP/1.1 200 OK
Content-Length: 0
```
---
Kirill Suslov – kirill@suslov.pro  
[https://github.com/Enfield](https://github.com/Enfield/)  
Distributed under the MIT license. See ``LICENSE`` for more information.
