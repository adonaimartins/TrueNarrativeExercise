# TRUE NARRATIVE technical test


## starting the wiremock

- open terminal
- cd to wire-mock directory
- run "docker-compose build" 
- run "docker-compose up"

-- note: if the commands above are slow, run them as an admin

-- note: docker needs to be installed


##other notes

if needed to change the localhost port, 
so the application does not connect to the wiremock and connect to the real API. 
then navigate to "application.yml" in the project and change the value of "apiBaseUrl".
