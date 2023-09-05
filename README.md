# PICKTRE-backend <a target="_blank" rel="noopener noreferrer nofollow" href="https://github.com/PICKTRE/PICKTRE_backend/blob/main/LICENSE"><img src="https://camo.githubusercontent.com/624c9e93c3b48d62d41af5687661c2a8a60ce90093685a281cc181bbbe14c6c1/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4c6963656e73652d417061636865322e302d627269676874677265656e" alt="license" data-canonical-src="https://img.shields.io/badge/License-Apache2.0-brightgreen" style="max-width: 100%;"></a> 
## 1. 소개
<img src="https://github.com/PICKTRE/PICKTRE_backend/assets/101933437/bb49dce2-a814-4722-8545-8b91e318f268" width="300" height="200">
<br>

> 환경을 위한 행동, 모두가 참여하는 서비스 "PICKTRE"

현대 사회에서 쓰레기 문제는 점점 심각해지고 있으며, 쓰레기의 적절한 처리는 환경보호와 지속가능한 발전을 위해 중요한 문제로 인식되고 있습니다.

하지만, 여전히 공공장소에서 쓰레기를 적절하게 버리지 않는 경우가 많아 환경오염과 공공장소의 미관을 해치는문제가 발생하고 있습니다.

이러한 배경에서 PICKTRE는 쓰레기를 적절하게 처리하고, 시민들의 쓰레기 버리기 습관을 개선하여 환경보호와 재활용 문화를 확산 시키는데 목적을 두고 있습니다. 또한, 사용자들이 쓰레기를 버릴 때 보상을 제공하여 쓰레기 문제에 대한 인식과 참여 향상을 목표로 합니다.

[PICKTRE 홈페이지](https://picktre.netlify.app "PICKTRE") [PICKTRE WIKI](https://github.com/PICKTRE/PICKTRE_backend/wiki)

<br>

## 2. 사용 방법

### git clone

```sh
$ git clone https://github.com/PICKTRE/PICKTRE_backend.git
```
### configuration 

#### application.properties

```sh
spring.datasource.url=jdbc:mysql://{mysql 주소}:3306/{DB 이름}?characterEncoding=utf8
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username={사용자 이름}
spring.datasource.password={사용자 비밀번호}

#hibernate ??
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.show_sql=true
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=create

#google login
google.cliendId = {cliendId}
google.redirect = {redirectUrl}
google.secret ={secretKey}
spring.profiles.include=oauth

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

cloud.aws.credentials.access-key= {accessKey}
cloud.aws.credentials.secret-key= {secretKey}
cloud.aws.stack.auto=false
cloud.aws.region.static= ap-northeast-2
cloud.aws.s3.bucket= {bucketName}
cloud.aws.s3.endpoint= https://kr.object.ncloudstorage.com

server.ssl.key-store= classpath:keystore.p12
server.ssl.key-store-password= 
server.ssl.key-store-type= 

server.port={서버포트번호}
server.forward-headers-strategy=native
```

### build & execute

#### Project build

프로젝트를 빌드하려면 다음 명령어를 실행하세요:

```sh
$ ./gradlew clean build -x test
$ cd build/libs
$ java -jar *.jar
```

#### execute

<img width="563" alt="스크린샷 2023-09-05 오후 11 15 23" src="https://github.com/PICKTRE/PICKTRE_backend/assets/101933437/4b4513aa-2650-4222-b64a-c54b3bd369ed">

```sh
scp {Build 한 Jar 파일이 있는 경로}/Test.jar root@118.67.131.231:~/
```

build 한 파일을 Naver Cloud Server 내로 이동

<img width="488" alt="스크린샷 2023-09-05 오후 11 13 40" src="https://github.com/PICKTRE/PICKTRE_backend/assets/101933437/5eb46f89-2528-4656-9153-e2fbb91c3c54">

```sh
 ssh root@118.67.131.231
```
Naver Cloud Server 로 서버 배포후 서버 접속


<img width="586" alt="스크린샷 2023-09-05 오후 11 20 17" src="https://github.com/PICKTRE/PICKTRE_backend/assets/101933437/d80d3c8f-edc2-4380-8968-8e40866acbe9">



nohup 사용 명령어 - 끄지 않는 이상 Run
```sh
nohup java -jar Test.jar &
```

nohup 사용 명령어 X
```sh
java -jar Test.jar 
```

## 3. 흐름도

<img width="395" alt="스크린샷 2023-09-05 오후 10 44 53" src="https://github.com/PICKTRE/PICKTRE_backend/assets/101933437/b5f26106-6a14-41f4-87d7-bdcb6cf970b7">



## 4. 관련 저장소
+ [PICKTRE-frontend](https://github.com/PICKTRE/PICKTRE_frontend)
+ [PICKTRE-ai](https://github.com/PICKTRE/PICKTRE_ai)

<br>

## 5. 오픈소스

[APACHE License](LICENSE)

[Let's Encrypt License](https://spdx.org/licenses/CC-BY-NC-4.0)


 
