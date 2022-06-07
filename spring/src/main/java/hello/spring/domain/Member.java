package hello.spring.domain;

import javax.persistence.*;

@Entity//JPA가 관리하는 entity가 된다.
public class Member {

    @Id//pk다
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동 생성=IDENTITY
    private Long id;

    @Column(name="usertname")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
