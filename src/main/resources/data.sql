insert into category(category_name, priority, created_by, last_modified_by, created_at, last_modified_at) values('전체글', 0, 'tmdal1', 'tmdal1', now(), now());
insert into category(category_name, priority, created_by, last_modified_by, created_at, last_modified_at) values('java', 1, 'tmdal1', 'tmdal1', now(), now());

insert into category(category_name, priority, created_by, last_modified_by, created_at, last_modified_at) values('spring', 2, 'tmdal1', 'tmdal1', now(), now());
insert into category(category_name, priority, created_by, last_modified_by, created_at, last_modified_at) values('jpa', 3, 'tmdal1', 'tmdal1', now(), now());
insert into category(category_name, priority, created_by, last_modified_by, created_at, last_modified_at) values('db', 6, 'tmdal1', 'tmdal1', now(), now());
insert into category(category_name, priority, created_by, last_modified_by, created_at, last_modified_at) values('cs', 4, 'tmdal1', 'tmdal1', now(), now());

insert into user_account(created_by, last_modified_by, created_at, last_modified_at, user_id, user_password) values('tmdal1', 'tmdal1', now(), now(), 'tmdal1', '{noop}asdf');

insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), '제어의 역전은 간단히 프로그램의 제어 흐름 구조가 뒤바뀌는 것이다.

​

일반적인 프로그램의 흐름은 main() 메소드와 같이 프로그램이 시작되는 지점에서 다음에 사용할 오브젝트를 결정하고, 결정한 오브젝트를 생성하고, 만들어진 오브젝트에 있는 메소드를 호출하고, 그 오브젝트 메소드 안에서 다음에 사용할 것을 결정하고 호출하는 식의 작업이 반복된다. 모든 오브젝트가 능동적으로 자신이 사용할 클래스를 결정하고, 언제 어떻게 그 오브젝트를 만들지를 스스로 관장하며, 모든 종류의 작업을 사용하는 쪽에서 제어하는 구조이다.

​

제어의 역전이란 이런 제어 흐름의 개념을 거꾸로 뒤집는 것이다. 오브젝트가 자신이 사용할 오브젝트를 스스로 선택하지 않고 생성하지도 않고 자신도 어떻게 만들어지고 어디서 사용되는지 알 수 없다. 모든 제어 권한을 자신이 아닌 다른 대상에게 위임하기 때문이다. 프로그램의 시작을 담당하는 main()과 같은 엔트리 포인트를 제외하면 모든 오브젝트는 이렇게 위임받은 제어 권한을 갖는 특별한 오브젝트에 의해 결정되고 만들어진다.

​

예를 들어서, 서블릿의 경우 서블릿을 개발해서 서버에 배포할 수 있지만, 그 실행을 개발자가 직접 제어하는 것이 아니라 서블릿에 대한 제어 권한을 가진 컨테이너가 적절한 시점에 서블릿 클래스의 오브젝트를 만들고 그 안의 메소드를 호출한다.

​

UserDao는 자신이 어떤 ConnectionMaker 구현 클래스를 만들고 사용할지를 결정할 권한을 DaoFactory에게 넘겻기 때문에 UserDao 자신도 팩토리에 의해 수동적으로 만들어지고 자신이 사용할 오브젝트도 DaoFactory가 공급해주는 것을 수동적으로 사용해야 할 입장이 되었다.

​

제어의 역전 (IoC)는 프레임워크가 꼭 필요한 기술이 아니라 디자인 패턴에서도 발견할 수 있는 것처럼 상당히 폭넓게 사용되는 프로그래밍 모델이다. IoC를 사용하면 설계가 깔끔해지고 유연성이 증가하며 확장성이 좋아지기 때문에 필요하다면 IoC 스타일의 설계와 코드를 만들어 사용하면 된다.

​

제어의 역전에서는 프레임워크 또는 컨테이너 같이 애플리케이션 컴포넌트의 생성과 관계설정, 사용, 생명주기 관리 등을 관장하는 존재가 필요한데 단순한 적용이라면 DaoFactory와 같이 IoC 제어권을 가진 오브젝트를 분리해서 만드는 방법이면 충분하겠지만, IoC를 애플리케이션 전반에 걸쳐 본격적으로 적용하려면 스프링과 같은 IoC 프레임워크의 도움을 받는 것이 유리하다.', '제어의 역전', 3, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), 'UserDao의 관심사는 JDBC API와 User 오브젝트를 이용해서 DB에 정보를 넣고 빼는것이기 때문에 어떤 ConnectionMaker 구현 클래스의 오브젝트를 이용하게 할지를 결정하는 것은 UserDao의 관심사가 아니다. 그렇기 때문에 해당 코드를 따로 분리해주어야 UserDao가 독립적으로 확장가능한 클래스가 될 수 있다.

​

그러기 위해서 UserDao 오브젝트와 특정 클래스로부터 만들어진 ConnectionMaker 오브젝트 사이에 관계를 설정해준다.

클래스 사이에 관계를 만드는 것은 한 클래스가 인터페이스 없이 다른 클래스를 직접 사용한다는 뜻이고, 오브젝트 사이의 관계는 런타임 시에 한쪽이 다른 오브젝트의 레퍼런스를 갖고 있는 방식으로 만들어 진다.

​

메소드 파라미터나 생성자 파라미터를 이용해서 외부에서 만든 오브젝트를 전달받아서 다른 오브젝트와의 관게를 맺을 수 있다.

​', '관계설정 책임의 분리', 3, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), '자바는 클래스의 다중상속을 허용하지 않는다.

UserDao가 이미 다른 클래스를 상속하고 있다면 추가로 또 상속을 받는 것은 불가능하다. 커넥션 객체를 가져오는 방법을 분리하기 위해 상속구조를 만들면 이후 다른 목적으로 UserDao에 상속을 적용하기도 힘들어 진다.

​

2. 상하위 클래스의 긴밀한 결합

서브클래스는 슈퍼클래스의 기능을 직접 사용할 수 있어서 슈퍼클래스에 변경이 생겼을 때 모든 서브클래스를 함께 수정하거나 다시 개발해야 할 수도 있다.

​

위와 같은 상속구조의 한계 때문에 코드를 상속으로 분리하지 않고 아예 별도의 클래스로 분리를 하고, UserDao가 그 클래스를 이용하게 한다.', '상속의 한계', 3, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), 'XML 이란?

확장 가능한 마크업 언어

​

XML(Extensible Markup Language)은 데이터를 저장하고 전송하기 위한 마크업 언어이다. XML은 사용자 정의 태그를 사용하여 데이터의 구조와 의미를 정의하며, 텍스트 기반 형식으로 인간과 컴퓨터 모두가 읽고 쓸 수 있다. 즉 태그로 데이터를 설명(표시, Markup)하는 것이고, 더 필요한 데이터가 생길 때 태그를 추가하거나 태그안에 내용을 추가할 수 있다는 특징이 있다. (Extensible)

​

​

XML의 특징

​

확장 가능성 (Extensibility): XML은 사용자 정의 태그를 생성하여 데이터의 구조를 정의할 수 있어 다양한 종류의 데이터를 표현하기에 유용하다.

계층적 구조 (Hierarchical Structure): XML 문서는 중첩된 요소들의 계층적 구조로 이루어져 있어서 데이터 간에 부모-자식 관계를 표현하기에 적합하다.

사람과 기계 모두 이해 가능 (Human-Readable and Machine-Readable): XML 문서는 일반적으로 텍스트로 작성되며, 이는 사람이 읽고 수정할 수 있을 뿐만 아니라 컴퓨터가 분석하고 처리할 수 있는 형식이다.

데이터의 의미 전달 (Data Semantics): XML은 데이터의 구조와 의미를 태그와 속성을 통해 명시적으로 나타내므로 데이터의 의미를 명확하게 전달할 수 있다.

다양한 용도로 활용 가능 (Versatility): 웹 서비스, 데이터 교환, 설정 파일, 문서 저장 등 다양한 용도로 활용될 수 있다.

​

XML은 주로 HTML과 비슷한 마크업 언어이지만, 주 목적은 데이터를 구조화하고 전달하는 데에 있다. XML은 데이터 표현을 위한 공통 형식을 제공하여 다양한 시스템 간에 데이터를 교환하고 공유하는 데에 매우 유용하다.

​

마크업 언어란?

​

마크업 언어는 문서 내의 구조와 표시를 정의하는 언어이다. 마크업 언어는 문서의 요소, 구조, 형식을 태그와 규칙을 통해 표현하며, 이를 통해 문서의 의미와 구조를 명확하게 표현할 수 있다. 마크업 언어는 컴퓨터와 사람 모두가 문서의 내용을 이해하고 해석할 수 있도록 도와준다.

​

​

마크업 언어의 특징

​

태그 (Tags): 마크업 언어에서는 태그를 사용하여 문서의 요소를 지정하고 태그는 각 요소의 역할과 의미를 정의한다.

구조와 계층성 (Structure and Hierarchy): 마크업 언어는 문서를 계층적 구조로 표현한다. 요소들은 부모-자식 관계로 구성되어 있으며, 이를 통해 문서의 논리적인 구조를 나타낼 수 있다.

속성 (Attributes): 요소에는 추가 정보나 설정을 제공하기 위해 속성을 사용할 수 있다. 속성은 태그 내에 추가적인 정보를 제공하는 역할을 한다.

의미 전달 (Semantic Communication): 마크업 언어는 데이터의 의미와 의도를 태그를 통해 전달할 수 있다. 각 태그는 특정한 의미와 역할을 가지며, 문서의 구조와 의미를 명확하게 표현할 수 있다.

용도에 따른 다양한 언어: 웹 문서의 경우 HTML(HyperText Markup Language)이 주로 사용되며, 데이터의 구조와 교환을 위한 XML(Extensible Markup Language)도 널리 사용된다.

사람과 기계 모두 이해 가능 (Human and Machine Readable): 마크업 언어는 사람이 문서를 읽고 이해할 수 있을 뿐만 아니라, 기계가 문서를 해석하고 처리할 수 있도록 도와준다.

​

XML의 스키마 작성방법

​

XML은 다른 마크업 언어를 만드는데 사용되는 다목적 마크업 언어이다. 이렇게 다른 언어를 정의하기 위해서는 먼저 해당 언어에 필요한 요소와 속성을 파악해야만 하는데 이러한 정보들의 집합을 스키마(schema)라고 부른다.

​

스키마는 일관성 있는 XML 문서를 유지하는데 아주 중요한 역할을 하고, XML에서 스키마를 작성할 때 다음과 같은 두 가지 방법을 사용할 수 있다.

​

DTD (Document Type Definition)

XML 스키마 (XSD)

​

​

문서타입 정의(DTD)란?

​

XML 문서의 구조 및 해당 문서에서 사용할 수 있는 적법한 요소와 속성을 정의한다. DTD는 오래된 구식의 방법이지만 특유의 장점을 바탕으로 아직도 널리 사용되고 있다. DTD를 사용하여 새로운 XML 문서의 구조를 정의함으로써 새로운 문서 타입을 만들 수 있으며 이렇게 생성된 DTD는 새로운 문서타입을 이용한 데이터의 교환에서 표준으로써 활용된다. 또한 응용프로그램은 DTD의 정의에 따라 XML 문서의 구문 및 구조에 대한 유효성 검사를 할 수 있다.

​', 'XML - DTD와 스키마', 6, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), '컴퓨터는 0과 1의 조합으로 이루어진 비트만을 저장하고 연산할 수 있기 때문에 컴퓨터에 저장되는 모든 것은 이진수로 저장되게 된다. 따라서 실수를 표현하기 위해 1. 고정 소수점 (Fixed Point) 방식, 2. 부동 소수점 (Floating Point) 방식을 사용한다. 그리고 컴퓨터는 실수를 정확히 표현할 수 없기 때문에 가장 근사치의 값이 저장되게 된다.


​

고정 소수점 (Fixed Point) 방식

​

실수는 보통 정수부와 소수부로 나눌 수 있기 때문에 실수를 표현하는 가장 간단한 방식은 소수부의 자릿수를 정하여 고정된 자릿수의 소수를 표현하는 것이다.

32bit 실수를 고정 소수점 방식으로 표현하면 아래와 같은데 이 방식은 정수부와 소수부의 자릿수가 크지 않기 때문에 표현할 수 있는 값의 범위가 매우 작다.', '컴퓨터에서의 실수의 표현', 6, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), '서로 다른 타입간의 연산을 수행해야 할 때 연산을 수행하기 전에 타입을 일치시켜야 하는데 변수나 리터럴의 타입을 다른 타입으로 변환하는 것을 형변환(casting)이라고 한다.

​

형변환 방법은 형변환하고자 하는 변수나 리터럴의앞에 변환하고자 하는 타입을 괄호와 함께 붙여주기만 하면 된다.

​

(타입)피연산자

​

​

기본형의 형변환

​

정수형간의 형변환

큰타입에서 작은타입으로 변환하는 경우에는 크기의 차이만큼 값손실이 발생할 수 있다. (int형(4byte) > byte형(1byte))

반대로 작은타입에서 큰타입으로 변환하는 경우에는 저장공간의 부족으로 값이 잘려나가는 일이 발생하지 않으며, 변환하려는 값이 양수인 경우에는 빈 공간을 0으로 채우고, 음수인 경우에는 1로 채운다.

​

2. 실수형간의 형변환

실수형에서도 정수형처럼 작은타입에서 큰 타입으로 변환하는 경우, 빈 공간을 0으로 채운다. float타입의 값을 double타입으로 변환하는 경우, 지수(E)는 float의 bias인 127을 뺀 후 double의 bias인 1023을 더해서 변환하고, 가수(M)은 float의 가수 23자리를 채우고 남은 자리를 0으로 채운다.

반대로 double타입에서 float타입으로 변환하는 경우, 지수(E)는 double의 bias인 1023을 뺀 후 float의 bias인 127을 더하고 가수(M)은 double의 가수 52자리 중 23자리만 저장되고 나머지는 버려진다. (이때 double형의 24자리 값이 1이면 반올림이 발생하여 23번째 자리의 값이 1 증가한다.)

그리고 float타입의 범위를 넘는 값을 float로 형변환 하는 경우는 ''± 무한대'' 또는 ''± 0''을 결과로 얻는다.

​

3. 정수형을 실수형으로 변환

실수형은 정수형보다 훨씬 큰 저장범위를 갖기 때문에 정수를 2진수로 변환한 다음 정규화해서 실수의 저장형식에 맞게 저장하기만 하면된다. 하지만 실수형의 정밀도의 제한으로 인한 오차가 발생할 수 있다.

​

4. 실수형을 정수형으로 변환

실수형을 정수형으로 변환하면, 실수형의 소수점이하 값은 버려지고 따로 반올림은 발생하지 않는다.

​

5. 자동 형변환

경우에 따라 편의상의 이유로 형변환을 생략할 수 있는데 컴파일러가 생략된 형변환을 자동적으로 추가해준다.

컴파일러는 기존의 값을 최대한 보존할 수 있는 타입으로 자동으로 형변환해준다. 표현범위가 좁은 타입에서 넓은 타입으로 형변환하는 경우에는 값 손실이 없으므로 두 타입 중에서 표현범위가 더 넓은 쪽으로 형변환된다. 더 좁은 타입으로의 형변환의 경우에는 반드시 형변환 연산자를 써줘야 한다.

char와 short은 둘 다 2byte 크기로 크기는 같지만 서로 표현하는 범위가 다르기 때문에 둘 중 어느쪽으로의 형변환도 값 손실이 발생할 수 있으므로 자동 형변환이 수행될 수 없다.

​', '형변환(casting)이란?', 2, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), '컴퓨터는 근본적으로 0과 1밖에 모르기 때문에 고급언어 (C, JAVA, C++ 등)로 작성한 코드를 컴퓨터가 이해할 수 있는 (즉, 실행이 가능한) 소프트웨어 산출물로 만드는 과정을 빌드(Build) 라고 한다.', '빌드란?', 2, 'tmdal1');
insert into article(created_by, last_modified_by, created_at, last_modified_at, content, title, category_id, user_id) values('tmdal1', 'tmdal1', now(), now(), '객체지향이론의 기본 개념은 ''실제 세계는 사물(객체)로 이루어져 있으며, 발생하는 모든 사건들은 사물간의 상호작용이다.

​

실제 사물의 속성과 기능을 분석하여 데이터(변수)와 함수로 정의함으로써 실제 세계를 컴퓨터 속에 가상화하고 가상세계에서 모의실험을 함으로써 많은 시간과 비용을 절약할 수 있었다.

​

객체지향언어의 특징

​

코드의 재사용성이 높다.

새로운 코드를 작성할 때 기존의 코드를 이용하여 쉽게 작성할 수 있다.

2. 코드의 관리가 용이하다.

코드간의 관계를 이용해서 적은 노력으로 쉽게 코드를 변경할 수 있다.

3. 신뢰성이 높은 프로그래밍을 가능하게 한다.

제어자와 메서드를 이용해서 데이터를 보호하고, 올바른 값을 유지하도록 하며 코드의 중복을 제거하여 코드의 불일치로 인한 오동작을 방지할 수 있다.', '객체지향언어', 2, 'tmdal1');


