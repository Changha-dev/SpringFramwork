package com.changha.project01.service;

import com.changha.project01.dto.MemberDTO;
import com.changha.project01.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//여기서 회원 만들 때는 서비스에서 별로 할 일이 없다.
@Service
// final 있어야 생성해줌
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public int save(MemberDTO memberDTO) {
        return memberRepository.save(memberDTO);
    }

    public boolean login(MemberDTO memberDTO) {
        MemberDTO loginMember = memberRepository.login(memberDTO);
        if (loginMember != null){
            return true;
        } else {
            return false;
        }
    }

    public List<MemberDTO> findAll() {
        return memberRepository.findAll();
    }

    public MemberDTO findById(Long id) {
        return memberRepository.findById(id);
        // 예외사항같은거 이곳에 추가
    }

    public void delete(Long id) {
        memberRepository.delete(id);
    }

    public MemberDTO findByMemberEmail(String loginEmail) {
        return memberRepository.findByMemberEmail(loginEmail);
    }

    public boolean update(MemberDTO memberDTO) {
        int result = memberRepository.update(memberDTO);
        if (result > 0){
            return true;
        } else {
            return false;
        }
    }

    public String emailCheck(String memberEmail) {
        MemberDTO memberDTO = memberRepository.findByMemberEmail(memberEmail);
        if (memberDTO == null){
            return "ok";
        } else {
            return "no";
        }
    }

    public String nameCheck(String memberName) {
        MemberDTO memberDTO = memberRepository.findByMemberName(memberName);
        if (memberDTO == null){
            return "ok";
        }else {
            return "no";
        }
    }
}
