package hello.core.singleton;

public class SingletonService {
    public static final SingletonService instance=new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }//동일한 객체만을 반환한다.

    private SingletonService(){
    }//외부 호출이 불가능하도록 생성자를 private로 만든다.

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
