# Test Steps:
##1- #add accounts:
`INSERT INTO pooyabyte.account (id, created_by, created_date, last_modified_by, last_modified_date, national_code) VALUES (1, 'mehdi', '2019-12-22 19:22:45', null, null, '1');`

##2- add fake data
2-1 run TaskServiceApplication
2-2- run `http://127.0.0.1:8087/rest/api/v1/fake/1000000`

##3- add request
2-1 run RequestApplication
2-2- request as POST to `http://127.0.0.1:8085/rest/api/v1/request` Body: `
{
    "from": {"id": 2},
    "to": {"id": 3},
    "amount": 1223,
    "course": {"code": "DA"},
    "startDate": "2018-01-01"
}`

return id of new record

##4- get Inquiry
2-1 run RequestApplication
2-2- request as GET to `http://127.0.0.1:8085/rest/api/v1/inquiry/${id}`
return message:
if is new return "request not started yet" else return 
"request started in: n times, last in: date"

##3- perform requests
2-3- project start at 1:00 AM to change it change com.msrazavi.test.pooyabyte.task.config.LaunchBatchConfig -> perform() method

