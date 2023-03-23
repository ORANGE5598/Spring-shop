package com.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.shop.entity.Member;
import com.shop.entity.MemberForm;

public interface MemberService extends UserDetailsService {
    Long updateInfo(String username, String newName, String email);
    Long updatePassword(String username, String newPassword);
    Long createMember(MemberForm form);
    void deleteMember(String username);
    Optional<Member> findByUsername(String username);
    List<Member> findAll();
    Optional<Member> findByEmail(String email);
    public void deleteMember(Member member);
    public Member findOne(Long memberId);

}
