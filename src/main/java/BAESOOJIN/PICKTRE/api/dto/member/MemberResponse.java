package BAESOOJIN.PICKTRE.api.dto.member;

import BAESOOJIN.PICKTRE.domain.member.Member;
import BAESOOJIN.PICKTRE.domain.member.MemberTier;
import lombok.Data;

@Data
public class MemberResponse {

    private Long memberId;
    private String mail;
    private String username;
    private String picture;

    private MemberTier memberTier;

    private String tierPath;
    private int trashCount;
    private int rewardPoints;
    private int todayReward;


    public MemberResponse(Member member) {
        this.memberId=member.getId();
        this.mail=member.getMail();
        this.username = member.getUserName();
        this.picture=member.getPicture();
        this.memberTier=member.getMemberTier();
        this.tierPath=member.getTierPath();
        this.trashCount= member.getTrashCount();;
        this.rewardPoints = member.getRewardPoints();
        this.todayReward= member.getTodayReward();
    }

    public static MemberResponse toDto(Member member) {
        return new MemberResponse(member);
    }
}

