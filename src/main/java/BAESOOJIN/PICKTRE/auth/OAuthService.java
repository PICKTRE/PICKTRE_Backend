package BAESOOJIN.PICKTRE.auth;

import BAESOOJIN.PICKTRE.api.dto.auth.GoogleOAuthTokenDto;
import BAESOOJIN.PICKTRE.api.dto.auth.GoogleUserInfoDto;
import BAESOOJIN.PICKTRE.domain.member.Member;
import BAESOOJIN.PICKTRE.repository.MemberRepository;
import BAESOOJIN.PICKTRE.service.MemberService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final GoogleAuth googleAuth;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private String access_Token_user;

    /**
     * API 로 부터 GoogleUserInfo를 받아옴
     * @param code API 로 받은 Code
     * @return Google user 반환
     * @throws JsonProcessingException
     */
    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleAuth.getAccessToken(accessTokenResponse);
        access_Token_user=oAuthToken.getAccess_token();
        ResponseEntity<String> userInfoResponse = googleAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }

    /**
     * 중복된 Member 검사
     * @param mail 같은 Mail 있는지 확인
     */
    private void validateDuplicateMember(String mail) {
        Optional<Member> memberByMail = memberRepository.findMemberByMail(mail);
        if(!memberByMail.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /**
     * 실질적인 구글 로그인
     * @param code API 로 부터 받아온 code
     * @return redirectURL
     * @throws IOException
     */
    public String googleLogin(String code) throws IOException
    {
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(code);

        Optional<Member> memberByMail = memberRepository.findMemberByMail(googleUser.getEmail());
        if(memberByMail.isEmpty()) {
            Member member = new Member(googleUser.getEmail(),googleUser.getName(),googleUser.getPicture());
            memberService.createMember(member);

            String redirectUrl = UriComponentsBuilder
                    .fromUriString("https://picktre.netlify.app/oauth/redirected/google")
                    .queryParam("memberId",member.getId())
                    .queryParam("accessToken", access_Token_user)
                    .build()
                    .toUriString();
            return redirectUrl;
        }


        Member member2 =memberService.getMemberByMail(memberByMail.get().getMail());
        String redirectUrl = UriComponentsBuilder
                .fromUriString("https://picktre.netlify.app/oauth/redirected/google")
                .queryParam("memberId",member2.getId())
                .queryParam("accessToken",  access_Token_user)
                .build()
                .toUriString();
        return redirectUrl;
    }

}
