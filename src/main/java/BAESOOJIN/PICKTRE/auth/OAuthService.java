package BAESOOJIN.PICKTRE.auth;

import BAESOOJIN.PICKTRE.api.dto.auth.GoogleOAuthTokenDto;
import BAESOOJIN.PICKTRE.api.dto.auth.GoogleUserInfoDto;
import BAESOOJIN.PICKTRE.repository.MemberRepository;
import BAESOOJIN.PICKTRE.service.MemberService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final GoogleAuth googleAuth;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ResponseService responseService;
    private String access_Token_user;

    private GoogleUserInfoDto getGoogleUserInfoDto(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleAuth.requestAccessToken(code);
        GoogleOAuthTokenDto oAuthToken = googleAuth.getAccessToken(accessTokenResponse);
        access_Token_user=oAuthToken.getAccess_token();
        ResponseEntity<String> userInfoResponse = googleAuth.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleAuth.getUserInfo(userInfoResponse);
        return googleUser;
    }

}
