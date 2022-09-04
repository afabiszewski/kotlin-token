#!/bin/zsh

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

printf "GET Hello\n"
curl -XGET localhost:8080/hello

printf "\n${GREEN}POST registerUser -> 200${NC} -> "
username=$(
  echo $RANDOM | base64 | head -c 20
  echo
)
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_$username\", \"password\":\"curlPassword\"}" localhost:8080/users

printf "\n${RED}POST registerUser -> 403${NC} -> "
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_$username\", \"password\":\"curlPassword\"}" localhost:8080/users

printf "\n${GREEN}PUT signInUser -> 200${NC} -> "
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/curlName_"$username"/token

printf "\n${RED}PUT signInUser -> 404${NC} -> "
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/nameDoesNotExist/token

printf "\n${RED}PUT signInUser -> 401${NC} -> "
curl -XPUT -H"Content-Type: application/json" -d'{"password":"wrongPassword"}' localhost:8080/users/curlName_"$username"/token

printf "\n"
