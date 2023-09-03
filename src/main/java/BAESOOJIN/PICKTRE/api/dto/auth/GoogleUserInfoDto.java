package BAESOOJIN.PICKTRE.api.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GoogleUserInfoDto {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
    private String hd;
}
