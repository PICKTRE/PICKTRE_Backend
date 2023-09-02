package BAESOOJIN.PICKTRE.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MemberTier {
    TIER1,TIER2,TIER3,TIER4,TIER5;

    @JsonCreator
    public static MemberTier from(String s) {
        return MemberTier.valueOf(s.toUpperCase());
    }
}
