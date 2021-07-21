과제로 만들었던건데 생각나서 올려놓습니다

# ThrowingMoney(돈뿌리기)

## 기술스택
 - Language : Java
 - JDK : zulu OpenJDK 11.0.8
 - Framework : Spring boot 2.3.4.RELEASE
 - Build : Maven 3.6.1
 - Database : MariaDB
 - Persistence Framework : Spring Data JPA
 - Test Framework : Junit5
 - Was : Spring Boot Embedded Tomcat
  
  
## 뿌리기 API
### Request Example
```
POST /send
X-USER-ID: ${userId}
X-ROOM-ID: ${roomId}

{
    "amount": 5000,
    "person": 4
}
```
  
### Response Example
```
{
    "code": 0,
    "message": null,
    "data": {
        "token": "TnS"
    }
}
```
```
{
    "code": 1002,
    "message": "뿌리기 금액이 인원수 보다 작습니다."
}
```
  
### Error Code
|코드|메세지|
|------|---|
|1001|금액이나 인원수를 확인해주시기 바랍니다.|
|1002|뿌리기 금액이 인원수 보다 작습니다.|
  
  
## 돈 받기 API
### Request Example
```
PUT /receive/{token}
X-USER-ID: ${userId}
X-ROOM-ID: ${roomId}
```
  
### Response Example
```
{
    "code": 0,
    "message": null,
    "data": {
        "receiveMoney": 604
    }
}
```
```
{
    "code": 2001,
    "message": "이미 만료 되었거나 존재하지 않는 받기 입니다."
}
```
  
### Error Code
|코드|메세지|
|------|---|
|2001|이미 만료 되었거나 존재하지 않는 받기 입니다.|
|2002|본인이 뿌리기 한 금액은 받을 수 없습니다.|
|2003|이미 돈을 받았습니다.|
|2004|선착순 받기가 모두 끝났습니다.|
  
  
## 조회 API
### Request Example
```
GET /receive/{token}
X-USER-ID: ${userId}
X-ROOM-ID: ${roomId}
```
  
### Response Example
```
{
    "code": 0,
    "message": null,
    "data": {
        "createdDate": "2020-10-11 13:31:02",
        "amount": 1000,
        "receiveMoney": 604,
        "receiveDetails": [
            {
                "amount": 604,
                "receiveUserId": 1234
            }
        ]
    }
}
```
```
{
    "code": 2005,
    "message": "요청하신 토큰이 7일이 경과 하였거나, 유효하지 않은 토큰 입니다."
}
```
  
### Error Code
|코드|메세지|
|------|---|
|2005|요청하신 토큰이 7일이 경과 하였거나, 유효하지 않은 토큰 입니다.|
  
  
## Entity
![화면 캡처 2020-10-11 143440](https://user-images.githubusercontent.com/47691300/95671235-49d89a80-0bcf-11eb-8263-be9e880794ac.jpg)
  
  
## 문제해결 전략

### 토큰 생성
  3자리 토큰 생성시 기존에 발급된 토큰이 있는지 확인하였습니다.  
  최근 10분간 동일한 조건으로 생성된 토큰이 있는지 확인하였습니다.  
  
### 돈 받기  
 동시에 돈 받기를 할 경우, 트랜잭션 격리수준을 REPEATABLE_READ로 지정하였습니다.  
 한 세션의 트랜잭션이 진행 중일 때는 외부에서 데이터 변경을 차단하고 동일한 세션에서는 데이터를 계속 읽을 수 있도록 하였습니다.
