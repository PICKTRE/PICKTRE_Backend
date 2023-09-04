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

    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleAuth.getAccessToken(accessTokenResponse);
        access_Token_user=oAuthToken.getAccess_token();
        ResponseEntity<String> userInfoResponse = googleAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }
    private void validateDuplicateMember(String mail) {
        Optional<Member> memberByMail = memberRepository.findMemberByMail(mail);
        if(!memberByMail.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

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
