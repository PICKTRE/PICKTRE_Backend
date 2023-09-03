package BAESOOJIN.PICKTRE.api.controller;

import BAESOOJIN.PICKTRE.auth.GoogleAuth;
import BAESOOJIN.PICKTRE.auth.OAuthService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final OAuthService oAuthService;
    private final GoogleAuth googleAuth;
    private final ResponseService responseService;
}
