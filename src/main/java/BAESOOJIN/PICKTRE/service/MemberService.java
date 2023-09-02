package BAESOOJIN.PICKTRE.service;

import BAESOOJIN.PICKTRE.domain.member.Member;
import BAESOOJIN.PICKTRE.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Value("${picktre.tier1}")
    private String tier1;

    @Value("${picktre.tier2}")
    private String tier2;

    @Value("${picktre.tier3}")
    private String tier3;

    @Value("${picktre.tier4}")
    private String tier4;

    @Value("${picktre.tier5}")
    private String tier5;


    /**
     * 새로운 사용자 생성
     *
     //     * @param username     사용자 이름
     //     * @param rewardPoints 리워드 포인트
     * @return 생성된 사용자 엔티티
     */
    public Member createMember(Member member) {
        update_Tier(member);
        return memberRepository.save(member);
    }

    /**
     *
     * @param member
     * @return
     */
    public Member updateMember(Member member) {
        // 기존 회원 정보 조회
        Member existingMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 업데이트할 정보 적용
        existingMember.setMail(member.getMail());
        existingMember.setRewardPoints(member.getRewardPoints());

        // 업데이트된 정보 저장 및 반환
        return memberRepository.save(existingMember);
    }

    /**
     * 사용자 조회
     *
     * @param memberId 사용자 ID
     * @return 사용자 엔티티
     * @throws RuntimeException 사용자가 없을 경우 예외 발생
     */
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member getMemberByMail(String mail) {
        return memberRepository.findMemberByMail(mail)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    /**
     * 모든 사용자 목록 조회
     *
     * @return 사용자 엔티티 목록
     */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }


    /**
     * 사용자 삭제
     * @param memberId
     */
    public void deleteMember(Long memberId) {
        // 해당 ID에 해당하는 회원 조회
        Member memberToDelete = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 회원 삭제
        memberRepository.delete(memberToDelete);
    }


    public void updateTodayReward() {
        List<Member> all = memberRepository.findAll();
        for(Member member:all) {
            member.resetTodayReward();
        }
    }

    public void update_Tier(Member member) {
        int currReward=member.getRewardPoints();
        if(currReward>=50000) {
            upgrade_Tier1(member);
        }
        else if(currReward>=20000) {
            upgrade_Tier2(member);
        }
        else if(currReward>=7000) {
            upgrade_Tier3(member);
        }
        else if(currReward>=4000) {
            upgrade_Tier4(member);
        }
        else {
            upgrade_Tier5(member);
        }
    }
    // 다른 사용자 관련 서비스 메소드 추가 가능

    public void upgrade_Tier1(Member member) {
        member.setTierPath(tier1);
    }

    public void upgrade_Tier2(Member member) {
        member.setTierPath(tier2);
    }

    public void upgrade_Tier3(Member member) {
        member.setTierPath(tier3);
    }

    public void upgrade_Tier4(Member member) {
        member.setTierPath(tier4);
    }

    public void upgrade_Tier5(Member member) {
        member.setTierPath(tier5);
    }

}
