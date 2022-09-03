#!/bin/zsh

printf "GET Hello\n"
curl -XGET localhost:8080/hello

printf "\nPOST registerUser -> 200\n"
username=$(
  echo $RANDOM | base64 | head -c 20
  echo
)
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_$username\", \"password\":\"curlPassword\"}" localhost:8080/users

printf "\nPOST registerUser -> 403\n"
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_$username\", \"password\":\"curlPassword\"}" localhost:8080/users

printf "\nPUT signInUser -> 404\n"
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/name/token

printf "\nPUT signInUser -> 200\n"
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/curlName_"$username"/token

printf "\n"
