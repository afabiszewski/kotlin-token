#!/bin/zsh
#COLOURS
YELLOW='\033[0;33m' RED='\033[0;31m' GREEN='\033[0;32m' NC='\033[0m'

#USERS OPERATIONS
printf "${YELLOW}USERS OPERATIONS${NC}"

printf "\n${GREEN}GET hello -> 200${NC} -> "
curl -XGET localhost:8080/hello

printf "\n${GREEN}POST registerUser (admin) -> 200${NC} -> "
username=$(
  echo $RANDOM | base64 | head -c 20
  echo
)
group=$(
  echo $RANDOM | base64 | head -c 20
  echo
)
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_$username\", \"password\":\"curlPassword\", \"groups\": [ \"user\", \"admin\"]}" localhost:8080/users

printf "\n${GREEN}POST registerUser (user) -> 200${NC} -> "
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_${username}_user\", \"password\":\"curlPassword\", \"groups\": [ \"user\"]}" localhost:8080/users

printf "\n${RED}POST registerUser -> 403${NC} -> "
curl -XPOST -H"Content-Type: application/json" -d"{\"name\":\"curlName_$username\", \"password\":\"curlPassword\", \"groups\": [ \"user\", \"admin\"]}" localhost:8080/users

printf "\n${GREEN}PUT signInUser (admin) -> 200${NC} -> "
JWT_ADMIN=$(curl -s -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/curlName_"$username"/token)
printf $JWT_ADMIN

printf "\n${GREEN}PUT signInUser (user)-> 200${NC} -> "
JWT_USER=$(curl -s -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/curlName_"$username"_user/token)
printf $JWT_USER

printf "\n${RED}PUT signInUser -> 404${NC} -> "
curl -XPUT -H"Content-Type: application/json" -d'{"password":"curlPassword"}' localhost:8080/users/nameDoesNotExist/token

printf "\n${RED}PUT signInUser -> 401${NC} -> "
curl -XPUT -H"Content-Type: application/json" -d'{"password":"wrongPassword"}' localhost:8080/users/curlName_"$username"/token

#GROUPS OPERATIONS
printf "\n${YELLOW}GROUPS OPERATIONS${NC}"

printf "\n${GREEN}GET getGroup (admin) -> 200${NC} -> "
curl -XGET -H"Authorization: ${JWT_ADMIN}" localhost:8080/groups/admin

printf "\n${RED}GET getGroup (user) -> 401${NC} -> "
curl -XGET -H"Authorization: ${JWT_USER}" localhost:8080/groups/admin

printf "\n${RED}GET getGroup -> 404${NC} -> "
curl -XGET -H"Authorization: ${JWT_ADMIN}" localhost:8080/groups/groupDoesNotExist

printf "\n${GREEN}POST createGroup -> 200${NC} -> "
group=$(
  echo $RANDOM | base64 | head -c 20
  echo
)
curl -XPOST -H"Authorization: ${JWT_ADMIN}" -H"Content-Type: application/json" -d"{\"name\":\"curlGroup_$group\"}" localhost:8080/groups

printf "\n${RED}POST createGroup -> 403${NC} -> "
curl -XPOST -H"Authorization: ${JWT_ADMIN}" -H"Content-Type: application/json" -d"{\"name\":\"curlGroup_$group\"}" localhost:8080/groups

printf "\n${RED}GET deleteGroup -> 404${NC} -> "
curl -XDELETE -H"Authorization: ${JWT_ADMIN}" localhost:8080/groups/groupDoesNotExist

printf "\n${GREEN}GET deleteGroup -> 200${NC} -> "
curl -XDELETE -H"Authorization: ${JWT_ADMIN}" localhost:8080/groups/curlGroup_"$group"

printf "\n"
