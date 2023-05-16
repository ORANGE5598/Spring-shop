package com.shop.service;

import java.util.List;
import java.util.Optional;

import com.shop.dto.MemberDTO;
import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.entity.Member;

public interface MemberService { //extends UserDetailsService {
	
	/** 회원가입 **/
    void userJoin(RequestDTO memberDTO);

    /** member_id로 memberDto 반환 **/
    MemberDTO.ResponseDTO getById(Long member_id);
    
    Optional<Member> findByUsername(String username);
    
    void changePoint(RequestDTO dto, Long id);
    
    List<Member> findMembers();
    

    /** =============== 회원 수정 =============== **/

    /** 닉네임 중복 체크 **/
    boolean checkNickname(Long member_id, String nickname);

    /** 비밀번호 일치 확인 **/
    boolean checkPassword(Long member_id, String checkPassword);

    /** 회원 수정 **/
    void userInfoUpdate(RequestDTO memberDTO);
    
    void deleteMember(String username);

    /** =============== 비밀번호 찾기 : 임시 비밀번호 전송 =============== **/

    /** 이메일 존재 확인 **/
    boolean checkEmail(String memberEmail);

    /** 임시 비밀번호 생성 **/
    String getTmpPassword();

    /** 임시 비밀번호로 업데이트 **/
    void updatePassword(String tmpPassword, String memberEmail);
	
	
//    Long updateInfo(String username, String newName, String email);
//    Long updatePassword(String username, String newPassword);
//    Long createMember(MemberDTO memberDto);
//    void deleteMember(String username);
//    Optional<Member> findByUsername(String username);
//    List<Member> findAll();
//    Optional<Member> findByEmail(String email);
//    public void deleteMember(Member member);
//    public Member findOne(Long memberId);
//    
//    //////////////////////////////////////////////////////////////////
//    
//    Map<String, String> validateHandling(Errors errors);
//    public void checkUsernameDuplication(MemberDTO memberDto);
//    public void checkEmailDuplication(MemberDTO memberDto);

}
