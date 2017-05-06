참말로
========
#### 참말로를 개발하고자 하는 사람은 반드시 아래 내용을 숙지하시고 따르셔야합니다. 그렇지 않을 경우 거절당할 수 있습니다.
명칭에 관한 규칙
--------
#### 언더바(_)는 사용하지 않는다.
#### 맨 앞 글자를 제외하고 단어의 첫 글자는 대문자를 사용한다.
#### 다음 약자를 사용한다.
>int <code>i</code><br>
>float <code>f</code><br>
>double <code>d</code><br>
>String <code>str</code><br>
>TextView <code>txt</code><br>
>EditText<code>edt</code><br>
>Button <code>btn</code><br>
>ImageButton <code>btn</code><br>
>ImageView <code>img</code><br>
>CheckBox <code>ckb</code><br>
>RadioButton <code>rdb</code><br>
>ListView <code>list</code><br>
>그 외 <code>모음을 제거 후 3글자 까지</code><br>

#### 약자와 함께 의미 있는 이름을 부여한다.
> <code>strName</code><br>
> <code>strTitle</code><br>
> <code>btnNext</code><br>
> <code>imgGame</code>

#### 클래스 이름은 항상 대문자로 시작한다.
#### 일반 클래스와 액티비티 클래스를 구별하기 위해 액티비티 클래 이름은 다음과 같이 명명한다.
> <code>(Name)Activity.java</code> 형태로 이름 뒤에 Activity를 붙여준다.
#### 레이아웃 xml 파일 이름 앞에 레이아웃에 관한 간단한 설명을 붙인다.
> 액티비티 <code>activity_(name).xml</code><br>
> 다이얼로그 <code> dialog_(name).xml</code><br>
> 리스트뷰 아이템 <code> item_(name).xml</code><br>

값의 정리
------
#### 변경의 편리를 위해 자주 사용하는 값은 한 곳에 모아둔다.
#### [res/values](https://github.com/CPstudy/Chammalo/tree/master/app/src/main/res/values) 경로에 해당 파일들이 있다.
> 색상 <code>colors.xml</code><br>
> 크기 <code>dimens.xml</code><br>
> 문자열 <code>strings.xml</code><br>

레이아웃에 관한 규칙
------
#### 기본 dimens.xml 파일에 정의되어 있음
#### 여백(margin & padding)
> 보통 여백 <code>basicMargin: 8dp</code><br>
> 위젯 사이 여백 <code>layoutMargin: 16dp</code><br>
> 다이얼로그 여백 <code>dialogMargin: 8dp</code><br>
#### 크기
> 레이아웃의 크기는 <code>dp</code>를 사용하고 텍스트의 크기는 <code>sp</code>를 사용한다.<br>
> 타이틀바 <code>titlebar: 54dp</code><br>
> 타이틀바 제목 크기 <code>titletext: 20sp</code><br>
> 다이얼로그 버튼 가로 크기 <code>btnDialogWidth: 60dp</code><br>
> 다이얼로그 버튼 세로 크기 <code>btnDialogHeight: 40dp</code><br>
> 상태바 크기 <code>status: 24dp</code><br>

모양과 색상에 관한 규칙
------
#### 코드로 만들 수 있는 도형은 이미지로 만들지 말고 코드로 작성한다.
#### drawable 폴더에 xml 파일로 만들어 사용한다.
> 전체 배경 <code>bg.xml</code><br>
> 다이얼로그 배경 <code>bg_dialog.xml</code><br>
> EditText 흰색 테두리 <code>bg_edittext_white</code><br>
> EditText 흰색 투명 테두리 <code>bg_edittext_gray</code><br>
> EditText 흰색(실제 사용할) <code>edittext_white.xml</code><br>
> 흰색 테두리 배경(메인 테두리) <code>bg_main.xml</code></br>
> 투명 테두리 배경 <code>bg_transparent.xml</code><br>
> 다이얼로그 버튼 <code>btn_dialog.xml</code><br>
> 액티비티 버튼 <code>btn_transparent.xml</code><br>
> 액티비티 흰색 테두리 버튼 <code>btn_white.xml</code><br>
#### 색상은 투명도를 넣어 배경이 살짝 보이게 한다.
#### 이미지의 색상은 흰색으로 통일한다.
#### 액티비티 전체 배경색은 선형 그라데이션을 사용하고 다음 색상을 사용한다.
> startColor <code>#252840</code><br>
> endColor <code>#33213d</code><br>
> angle <code>270</code><br>
> type <code>linear</code><br>
#### 그라데이션의 시작 색과 끝 색은 같은 비슷한 색상으로 사용하면 안 된다.
> 파란색 - 파란색 (X)<br>
> 노란색 - 노란색 (X)<br>
> 파란색 - 빨간색 (O)<br>
> 빨간색 - 노란색 (O)<br>
> 노란색 - 초록색 (O)<br>
#### 색상은 배경과 어울리게 튀지 않을 정도로 설정해야한다.
