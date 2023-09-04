package BAESOOJIN.PICKTRE.auth;

import BAESOOJIN.PICKTRE.api.dto.auth.GoogleOAuthTokenDto;
import BAESOOJIN.PICKTRE.api.dto.auth.GoogleUserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class GoogleAuth {

    private final String googleLoginUrl = "https://accounts.google.com";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;


    @Value("${google.cliendId}")
    private String googleClientId;
    @Value("${google.redirect}")
    private String googleRedirectUrl;
    @Value("${google.secret}")
    private String googleClientSecret;

    /**
     * Accesstoken 요청
     * @param code
     * @return responseEntity
     */
    public ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> reponseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        if (reponseEntity.getStatusCode() == HttpStatus.OK) {
            return reponseEntity;
        }
        return null;
    }

    /**
     * 받아온 AccessToken 확인
     * @param response 구글로 부터 받아온 UserDTO
     * @return googleOAuthToken 반환
     * @throws JsonProcessingException
     */
    public GoogleOAuthTokenDto getAccessToken(ResponseEntity<String> response) throws JsonProcessingException { // accessToken에 대한 정보를 자바 객체로 저장후 리턴
        System.out.println("respone.getBody()= " + response.getBody());
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.getBody(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

    /**
     * User 정보 요청
     * @param oAuthToken 받아온 Token
     * @return Response
     */

    public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken){// 토큰을 가지고 사용자 정보를 요청
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+oAuthToken.getAccess_token());
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);
        System.out.println("response.getBody() = "+response.getBody());
        return response;
    }

    /**
     * User 정보 가져오기
     * @param response 구글로 부터 받아온 유저 정보 DTO
     * @return GoogleUserInfoDto
     * @throws JsonProcessingException
     */
    public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfoDto googleUserInfoDto = objectMapper.readValue(response.getBody(),GoogleUserInfoDto.class);
        return googleUserInfoDto;
    }

    /**
     * RedirectURL 반환
     * @return redirectURL
     */
    public String getOauthRedirectURL() {
        String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";

        return reqUrl;
    }


}