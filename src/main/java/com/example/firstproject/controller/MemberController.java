package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("members/signup")
    public String newMemberForm(){
        return "members/new";
    }

    @PostMapping("/members/join")
    public String joinMember(MemberForm form) {
        System.out.println(form.toString());

        Member member = form.toEntity();
        System.out.println(member.toString());

        Member saved = memberRepository.save(member);
        System.out.println(saved.toString());

        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        List<Member> memberEntityList = memberRepository.findAll();
        model.addAttribute("memberList", memberEntityList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member memberEntity = memberRepository.findById(id).orElse(null);

        model.addAttribute("member", memberEntity);

        return "members/edit";
    }

    @PostMapping("members/update")
    public String update(MemberForm form) {
        Member memberEntity = form.toEntity();

        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);

        if (target != null) {
            memberRepository.save(memberEntity);
        }

        return "redirect:/members/" + memberEntity.getId();
    }
    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        Member target = memberRepository.findById(id).orElse(null);

        if (target!=null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        return "redirect:/members";
    }
}
