#!/bin/zsh

printf "GET Hello\n"
curl -XGET localhost:8080/hello
printf "\nPOST registerUser\n"
curl -XPOST -H"Content-Type: application/json" -d'{"name":"curlName", "password":"curlPassword"}' localhost:8080/users
printf "\nPUT signInUser -> 404\n"
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/name/token
printf "\nPUT signInUser -> 200\n"
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/curlName/token
