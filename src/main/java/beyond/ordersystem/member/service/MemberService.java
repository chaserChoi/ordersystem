package beyond.ordersystem.member.service;

import beyond.ordersystem.member.domain.Member;
import beyond.ordersystem.member.dto.MemberCreateDto;
import beyond.ordersystem.member.dto.LoginReqDto;
import beyond.ordersystem.member.dto.MemberResDto;
import beyond.ordersystem.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long save(MemberCreateDto dto) {
        if(memberRepository.findByEmail(dto.getEmail()).isPresent()) throw new IllegalArgumentException("중복되는 이메일입니다.");

        // member를 리턴하여, 컨트롤러에서 프론트에게 방금 저장한 id를 반환할 수 있게 함
        // 여기서는 id를 반환
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Member member = memberRepository.save(dto.toEntity(encodedPassword));
        return member.getId();
    }

    public Member doLogin(LoginReqDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())) throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        return member;
    }

    public List<MemberResDto> findAll() {
        List<MemberResDto> memberResDtoList = memberRepository.findAll().stream().map(a -> MemberResDto.fromEntity(a)).toList();
        return memberResDtoList;
    }

    public MemberResDto findMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        return MemberResDto.fromEntity(member);
    }

    public Long memberDelete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        member.setDelYn("Y");
        return member.getId();
    }
}
