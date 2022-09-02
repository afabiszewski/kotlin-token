#!/bin/zsh

#GET Hello
curl -XGET localhost:8080/hello

#POST createUser
curl -XPOST -H"Content-Type: application/json" -d'{"name":"curlName", "password":"curlPassword"}' localhost:8080/users
