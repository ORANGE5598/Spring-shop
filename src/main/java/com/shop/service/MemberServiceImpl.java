package com.shop.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.dto.MemberDTO.RequestDTO;
import com.shop.dto.MemberDTO.ResponseDTO;
import com.shop.entity.Member;
import com.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	/** 회원 가입 **/
	public void userJoin(RequestDTO memberDTO) {
		memberDTO.encryptPassword(passwordEncoder.encode(memberDTO.getPassword()));
		
		Member member = memberDTO.toEntity();
		memberRepository.save(member);
		log.info("회원 가입 멤버 : " + member);
	}
	
	@Override
	public ResponseDTO getById(Long member_id) {
		Member member = memberRepository.findById(member_id).orElseThrow(() -> new IllegalArgumentException("해당 사용자 찾을 수 없음."));
		return new ResponseDTO(member);
	}
	
	@Override
	public boolean checkNickname(Long member_id, String nickname) {
		return false;
	}
	
	@Override
	public boolean checkPassword(Long member_id, String checkPassword) {
		Member member = memberRepository.findById(member_id).orElseThrow(() -> new IllegalArgumentException("해당 사용자 찾을 수 없음."));
		
		String password = member.getPassword();
		boolean check = passwordEncoder.matches(checkPassword, password);
		return check;
	}
	
	@Override
	@Transactional
	public void userInfoUpdate(RequestDTO memberDTO) {
		Member member = memberRepository.findById(memberDTO.toEntity().getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자 찾을 수 없음."));
		log.info("변경 패스워드 : " + memberDTO.getPassword());
		String encryptPassword = passwordEncoder.encode(memberDTO.getPassword());
		String name = memberDTO.getName();
		String email = memberDTO.getEmail();
		String address1 = memberDTO.getAddress1();
		String address2 = memberDTO.getAddress2();
		String phone = memberDTO.getPhone();
		String profile = "/img/profile/" + memberDTO.getProfile();
		
		log.info(member);
		
		log.info("패스워드 일치 여부 : " + passwordEncoder.matches(memberDTO.getPassword(), encryptPassword));
		
		member.update(encryptPassword, name, email, address1, address2, phone, profile);
		
		log.info("회원 정보 수정 : " + member);
	}
	
	@Override
	public boolean checkEmail(String memberEmail) {
		return memberRepository.existsByEmail(memberEmail);
	}
	
	@Override
	public String getTmpPassword() {
		char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String pwd = "";

        /* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            pwd += charSet[idx];
        }

        log.info("임시 비밀번호 생성" + pwd);

        return pwd;
	}
	
	@Override
	public void updatePassword(String tmpPassword, String memberEmail) {
		String encryptPassword = passwordEncoder.encode(tmpPassword);
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(() ->
                new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        member.updatePassword(encryptPassword);
        log.info("임시 비밀번호 업데이트" + tmpPassword);
		
	}
	
	@Override
    @Transactional
    public void deleteMember(String username) {
        Member member = memberRepository.findByUsername(username).get();
        memberRepository.delete(member);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

	@Override
	public void changePoint(RequestDTO dto, Long id) {
		@SuppressWarnings("deprecation")
		Member entity = memberRepository.getById(id);
		
		entity.changePoint(dto.getPoint());
		memberRepository.save(entity);
	}
	
	@Override
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	
	
	
/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member memberEntity = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없읍니다."));
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
		}
		
		return new Member(username, memberEntity.getPassword(), memberEntity.getEmail(), memberEntity.getAddress(), memberEntity.getPhone(), authorities);
	}

	public Long updateInfo(String username, String newName, String email) {
        Member member  = memberRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));

        // 중복체크 로직 추가 필요.
        member.setUsername(newName);
        member.setEmail(email);
        return member.getId();
    }

    @Override
    public Long updatePassword(String username, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        Member member = optionalMember.get();
        member.setPassword(newPassword);
        return member.getId();
    }

    @Transactional
    @Override
    public Long createMember(MemberDTO form) {
//    	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        return memberRepository.save(
                Member.builder()
                        .username(form.getUsername())
                        .password(form.getPassword())
                        .email(form.getEmail())
                        .build()).getId();
    }

    @Override
    @Transactional
    public void deleteMember(String username) {
        Member member = memberRepository.findByUsername(username).get();
        memberRepository.delete(member);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public List<Member> findAll() { 
    	return memberRepository.findAll(); 
    }
    
    @Override
    public Optional<Member> findByEmail(String email) { 
    	return memberRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
        return;
    }

    @SuppressWarnings("deprecation")
	@Override
    public Member findOne(Long memberId) {
        return memberRepository.getOne(memberId);
    }

	@Override
	@Transactional
	public Map<String, String> validateHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();
		
		for(FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		
		return validatorResult;
	}

	@Override
	@Transactional
	public void checkUsernameDuplication(MemberDTO memberDto) {
		boolean usernameDuplicate = memberRepository.existsByUsername(memberDto.getUsername());
		if(usernameDuplicate) {
			throw new IllegalStateException("이미 존재하는 아이디.");
		}
		
	}

	@Override
	@Transactional
	public void checkEmailDuplication(MemberDTO memberDto) {
		boolean emailDuplicate = memberRepository.existsByEmail(memberDto.getEmail());
		if(emailDuplicate) {
			throw new IllegalStateException("이미 사용중인 이메일.");
		}
	}
*/
}