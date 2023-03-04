package com.changha.project01.controller;

import com.changha.project01.dto.MemberDTO;
import com.changha.project01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
// 스프링 bean 객체로 사용하기 위해 의존성 주입
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/save")
    public String saveForm() { //회원가입 폼
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        int saveResult = memberService.save(memberDTO);
        if (saveResult > 0){
            return "login";
        } else {
            return "save";
        }
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session){
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult){
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        } else {
            return "login";
        }
    }
    @GetMapping("/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    // /member?id=1
    @GetMapping
    public String findById(@RequestParam("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){
        // delete 가 return 을 원래 int로 주니까 판단 해주는 로직 짤 수 있음
        memberService.delete(id);
        return "redirect:/member/";
    }

    // 수정화면 요청
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        // 세션에 저장된 나의 이메일 가져오기
       String loginEmail = (String) session.getAttribute("loginEmail");
       MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);
       model.addAttribute("member", memberDTO);
       return "update";
    }

    // 수정 처리
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        boolean result = memberService.update(memberDTO);
        if (result) {
            return "redirect:/member?id=" + memberDTO.getId();
        } else {
            return "index";
        }
    }

    @PostMapping("/email-check")
    //ResponseBody = 서버 -> 클라
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail){
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }

    @PostMapping("/name-check")
    public @ResponseBody String nameCheck(@RequestParam("memberName") String memberName){
        System.out.println("memberName = " + memberName);
        String checkResult = memberService.nameCheck(memberName);
        return checkResult;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if(session != null){
            System.out.println("session = " + session);
            session.removeAttribute("memberEmail");
        }
        return "redirect:/member/login";

    }

    @PostMapping("/emailAuth")
    public @ResponseBody boolean emailAuth(@RequestParam("email") String email){
        System.out.println("email = " + email);
        return false;
    }

}

