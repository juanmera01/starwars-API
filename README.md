# Starwars-API

## Overview
This API facilitates the management of missions by allowing the creation of new missions, the listing of missions or their recommendation. 
missions, the listing of missions or the recommendation of missions. It does not require authentication of 
authentication of any kind. It communicates with another system through another API to obtain the data of ships, people and planets, 
people and planets. It uses json as response format.
It is developed using java Spring framework and a HSQLDB as a persistence system.

You can find th documentation of the existing StarWars API [here](https://swapi.dev/documentation)

## How to run
For the execution of the application, it will be enough to execute the database as a local server. 
To do this we execute the selected file from the bin folder.

![image](https://user-images.githubusercontent.com/79599587/209660851-d71a9a83-3b97-4cb1-b53d-f400298a8281.png)

With the database running, we can view its contents with the file runManagerSwing in the same folder as above. 
the same folder as the previous one. This provides us with a simple graphical interface with which we can view the tables and run 
see the tables and to be able to execute SQL queries.
After running the database, we can run the application normally. The uploaded database 
The database uploaded along with the code to the repository is empty. Along with the upload of the data from the 
Star Wars api data, some test missions are created.

## End-points
The system provides the following end-points:

* http://localhost:8090/missions?page=1&size=3 - GET
To obtain a list of missions. You can also add the "search" parameter to 
search by name of the captains participating in the mission:
http://localhost:8090/missions?page=1&size=3&search=vader
The response of a size 3 mission page is as follows:
```json
{
 "current": "http://localhost:8090/missions?null",
 "results": [
 {
 "id": "1",
 "initialDate": "2022-12-20T14:04:26.068529",
 "endDate": "2022-12-21T20:04:26.068529",
 "starship": {
 "id": "3",
 "name": "Sentinel-class landing craft",
 "crew": "5",
 "passengers": "75"
 },
 "captains": [
 {
 "id": "3",
 "name": "R2-D2"
 }
 ],
 "planets": [
 {
 "id": "3",
 "name": "Yavin IV",
 "diameterKM": "10200"
 }
 ],
 "crew": "25",
 "durationHours": "30",
 "reward": "10000.0"
 },
…
 ]
}
```

*  http://localhost:8090/missions - POST
Sending as the body of the request a json with date in ISO format and the following fields. 
fields:
```json
{
 "initialDate": "2022-12-20T12:12:00",
 "starship_id": 1,
 "people_ids": [1],
 "planet_ids": [1],
 "crew": 170,
 "reward": 1000
}
```
And getting the following in response:
```json
{
 "id": "7",
 "initialDate": "2022-12-20T12:12",
 "endDate": "2022-12-20T18:12",
 "starship": {
 "id": "1",
 "name": "CR90 corvette",
 "crew": "30-165",
 "passengers": "600"
 },
 "captains": [
 {
 "id": "1",
 "name": "Luke Skywalker"
 }
 ],
 "planets": [
 {
 "id": "1",
 "name": "Tatooine",
 "diameterKM": "10465"
 }
 ],
 "crew": "170",
 "durationHours": "6",
 "reward": "1000"
}
```

*  http://localhost:8090/missions/{id}
To get back the mission that has that identifier.

*  http://localhost:8090/missions/next?criteria=reward
The system recommends us the next best pending mission according to two criteria 
configurable with the "criteria" parameter:
- "reward": the reward obtained by completing the mission.
- rewardPerHour": the reward in relation to the duration of the mission.
In successive calls, the system will recommend the next mission according to the criteria. 
Once a mission is recommended, it is removed from the recommender subsystem and will not be taken into account for the next call. 
for the next call. An output for "reward" criterion:
```json
{
 "id": "3",
 "initialDate": "2022-12-20T14:04:26.104528",
 "endDate": "2022-12-20T15:04:26.104528",
 "starship": {
 "id": "4",
 "name": "Death Star",
 "crew": "342,953",
 "passengers": "843,342"
 },
 "captains": [
 {
 "id": "4",
 "name": "Darth Vader"
 }
 ],
 "planets": [],
 "crew": "342960",
 "durationHours": "1",
 "reward": "20000.0"
}
```

## Implementation and review documentation
### Integration with existing star wars API
The data of people, ships and planets are loaded and stored in the database at the start of the run. 
the beginning of the execution. For this reason, the application takes some time to start receiving requests. 
to start receiving requests. 
To facilitate the review, once the procedure of downloading and storing the data has been performed 
once, making the following changes obviates those tedious steps in subsequent 
executions:
1.  Change in the application.properties file the value of the selected line from 
"create" to "validate".
![image](https://user-images.githubusercontent.com/79599587/209660656-45eabee7-d9fa-4a27-b8cd-da067ee64fe9.png)
2. Commenting on the class "StarwarsAPIApplication" the content of the method. 
"postconstruct" method:
![image](https://user-images.githubusercontent.com/79599587/209660696-0c197992-7cd0-482a-ac6f-59f598f83fe4.png)

### Persistence
The persistence mechanism chosen is a local HSQLDB database. For the simplicity and 
agility it brings to the development. Also for the possibility of sending this database empty and not having to 
empty and not having to have any cloud storage provider with its corresponding cost. 
JPA is also chosen as the persistence api for data access.
The database uploaded along with the code to the repository is empty. Along with the upload of the 
Star Wars api data some test missions are created.

### E-R diagram
![image](https://user-images.githubusercontent.com/79599587/209660764-a6dfbdb6-7505-4920-8d14-c2076a15416e.png)

