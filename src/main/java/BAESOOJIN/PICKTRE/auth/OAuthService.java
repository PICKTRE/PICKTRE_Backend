package BAESOOJIN.PICKTRE.auth;

import BAESOOJIN.PICKTRE.repository.MemberRepository;
import BAESOOJIN.PICKTRE.service.MemberService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final GoogleAuth googleAuth;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ResponseService responseService;
}
