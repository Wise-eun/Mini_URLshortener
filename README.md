# Mini_URLshortener (완료)
2022스마일게이트 윈터데브캠프에서 진행하는 개인프로젝트 URLShortener  레파지토리입니다.

사용자가 길이가 긴 URL을 입력하였을 때, 단축된 URL 제공하며 해당 단축URL을 주소에 입력 시 원 주소로 리다이렉트를 시켜줍니다.

- 8문자 이내의 단축된 링크 제공
- 단축된 링크로 주소접속시, 원본링크로 리다이렉트
- 단축된 링크들에대한 리스트 보여줌
- 어떤 링크들이 제일 많이 단축되었는지를 그래프로 보여줌


*********
### 개발진행상황
[12.25] 1차완료, 리다이렉션 부분과 URL차트 표시 부분은 진행중입니다.
<br>[12.26] 2차완료, 리다이렉션 부분 구현완료하였습니다.
<br>[12.26] 3차완료, URL차트 표시 부분 구현완료하였습니다.
<br>[12.26] 최종완료, 코드리뷰를 위한 리팩토링 작업을 완료하였습니다.

#### 완료된 부분
- 사용자 URL 입력 시, 유효검사
- base62를 이용한 단축URL 제공 ( 제공과 동시에 복사)
- 단축URL 기록 보여주기 (클릭하면 URL 복사)
- DB에 동일한 URL이 있을경우, 동일한 단축URL 제공 및 해당 URL count 증가 ( 제공과 동시에 복사)
- 인터넷주소링크에서 단축URL접속시 리다이렉션
- 단축URL count 수 차트 보여주기

*********
### 기술스택
- 백엔드
  - APACH
  - MySQL
  - PHP
- 프론트엔드
  - AndroidStudio
  - JAVA
*********
### 기능
- URL 입력 시, 단축된 URL 제공
- 동일한 URL 입력 시, 동일한 단축URL 제공
- 단축URL 접속시, 원본URL로 리다이렉트
- 원본URL 및 단축URL 기록 리스트 제공
- 리스트내 아이템 클릭시, 해당 단축링크 복사
- 단축횟수 상위 5개 원본링크 차트표시

*********
### 화면
![그림1](https://user-images.githubusercontent.com/67956564/209547208-564be008-51b1-4e3f-a491-925327732b71.png)
![그림2](https://user-images.githubusercontent.com/67956564/209547232-edd331ed-d0f2-449f-824e-cc52f4fb9715.png)
![그림3](https://user-images.githubusercontent.com/67956564/209547251-2f6379bf-6489-47a5-982e-4da1689dc930.png)

*********
### 코드 중 확인받고 싶은 부분
- PHP문에서 데이터 관련작업을 하는것이 괜찮을까
<br>[registerURL.php](https://github.com/Wise-eun/Mini_URLshortener/blob/main/php/registerURL.php)
<br>DB에 데이터를 저장할때, JAVA로부터 원본링크만을 전달받아 php문에서 SQL문을 통해 가장 높은 수의 ID를 찾고, 인코딩작업을하고 DB에 데이터를 저장을 하게됩니다. 
이 일련의 작업들이 php코드에서 이루어질때, 속도나 성능면에서 괜찮은지, 코드작업면에서도 JAVA가 아닌 php문에서 하는게 맞는지 확인을 받고싶습니다.
<br><br>
- 예외상황 발생 가능성 및 개선방향제시
<br>[FragmentMain.java](https://github.com/Wise-eun/Mini_URLshortener/blob/main/app/src/main/java/com/example/sgdevcmap_urlshortener/FragmentMain.java)
<br>해당 코드는 JAVA코드 중 메인인 코드입니다. URL유효성 검사와 중복검사, 그리고 긴URL을 짧은URL로 만들어줍니다.
<br>[redirect.php](https://github.com/Wise-eun/Mini_URLshortener/blob/main/php/redirect.php)
<br>해당 코드는 PHP코드 중 메인인 코드입니다. 리다이렉션 기능을 수행합니다.
*********

### 개발관련 과정에서 궁금했던 부분 

- URL 단축알고리즘 관련
  - hash함수사용 -> base62변환
<br>SHA256와 같은 긴 해쉬함수에서 앞 10글자를 가져와 base62변환
  - hash함수사용 
  <br>Adler32나 CRC32와 같은 길이가 짧은 hash함수를 이용하여 변환
  - base62 변환 (채택)
<br>DB에 저장되어있는 각 URL의 ID를 순차적으로 증가시키며 해당 ID를 base62로 변환

hash함수를 사용하여 알고리즘을 구현할 경우 중복생성 가능성이 있어 충돌이 생길 수 있기에  현재 DB에 저장되어있는 ID를 1씩 증가시켜, 해당 숫자를 base62로 변환하는 알고리즘을 채택하였습니다. 하지만 해당 방법을 사용하니 모양이 예쁘지 않아 (my8u4K , ny8u4K, oy8u4K ...)  어떻게 하면 좀 더 예쁘게 더 좋은 방법으로 나타낼 수 있을까 궁금합니다.
<br>
<br><br>
- HTTPS와 HTTP 문제 관련
<br>https://192.168.0.155/ny8u4K (NOT FOUND)
<br>http://192.168.0.155/ny8u4K (CONNECT)

본 프로젝트는 apach와 php를 이용하여 서버와 통신을 주고받고있습니다.
<br>그러는 과정에서 mysql과 연결하기위한 php연결 링크는 'https' 시작해야 제대로 연결되고 리다이렉트를 위한 php연결링크의 경우에는 'http'로 시작해야 연결이 정상적으로 됩니다.

<br>관련 코드
( [redirect.php](https://github.com/Wise-eun/Mini_URLshortener/blob/main/php/redirect.php) , httpd-vhosts.conf )
```
  <VirtualHost *:80>
      DocumentRoot "/home/web/public_html"
 
      ServerName auddms.com

      RewriteEngine On
      RewriteRule ^(.*)$ https://%{HTTP_HOST}/redirect.php?par=%{REQUEST_URI} [R=301,L]
  </VirtualHost>
```
현재 php연결같은 경우에는 리다이렉션을 통해서 https로 자동으로 수정하여 연결하였지만 원인에 대해서는 아직 파악하지 못하고 있습니다. 
