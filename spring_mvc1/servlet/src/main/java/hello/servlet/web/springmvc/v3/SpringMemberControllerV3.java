package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private final MemberRepository memberRepository=MemberRepository.getInstance();

//    @RequestMapping("/new-form")
//    @RequestMapping(value="/new-form",method=RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm(){
        return "new-form";
    }

//    @RequestMapping("/save")
//    @RequestMapping(value="/save",method= RequestMethod.POST)
    @PostMapping("/save")
    //requestparam annotation으로 파라미터를 직접 받을 수 있음, 타입도 뒤에 지정가능
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member",member);
        return "save-result";
    }

//    @RequestMapping
//    @RequestMapping(method=RequestMethod.GET)
    @GetMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute(members);
        return "members";
    }
}
