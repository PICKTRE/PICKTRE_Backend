package BAESOOJIN.PICKTRE.api.controller;

import BAESOOJIN.PICKTRE.auth.GoogleAuth;
import BAESOOJIN.PICKTRE.auth.OAuthService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final OAuthService oAuthService;
    private final GoogleAuth googleAuth;
    private final ResponseService responseService;

    /**
     *
     * @param response
     * @throws Exception
     * 구글 로그인 페이지로 보내기
     */
    @GetMapping("/api/google")
    public void getGoogleAuthUrl(HttpServletResponse response) throws Exception {
        response.sendRedirect(googleAuth.getOauthRedirectURL());
    }



    /**
     *
     * @param code
     * @return
     * @throws IOException
     * 구글 로그인 후 리다이렉트 매핑 주소 받음
     */
    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity callback(@RequestParam(name="code") String code) throws IOException {
        String toUrl = oAuthService.googleLogin(code);// 여기서 memberId 여기서 보냄
        HttpHeaders headers=new HttpHeaders();
        headers.setLocation(URI.create(toUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
