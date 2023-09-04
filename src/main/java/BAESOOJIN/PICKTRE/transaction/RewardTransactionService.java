package BAESOOJIN.PICKTRE.transaction;

import BAESOOJIN.PICKTRE.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RewardTransactionService {

    private final RewardTransactionRepository rewardTransactionRepository;

    /**
     * 리워드 포인트 적립
     *
     * @param member 회원
     * @param points 적립할 포인트
     */
    public void earnRewardPoints(Member member, String productName, int productCount, int points) {
        RewardTransaction transaction= RewardTransaction.builder()
                .member(member)
                .productName(productName)
                .productCount(productCount)
                .points(points)
                .transactionDateTime(LocalDateTime.now())
                .build();
        rewardTransactionRepository.save(transaction);
        member.addRewardPoints(points);
    }

    /**
     * 리워드 포인트 사용
     *
     * @param points                  사용할 포인트
     * @param member                  회원
     */
    public void useRewardPoints(Member member, String productName, int productCount, int points) {
        RewardTransaction transaction= RewardTransaction.builder()
                .member(member)
                .productName(productName)
                .productCount(productCount)
                .points(-points)
                .transactionDateTime(LocalDateTime.now())
                .build();
        rewardTransactionRepository.save(transaction);
        member.deductRewardPoints(points);
    }

    // 다른 리워드 포인트 관련 서비스 메소드 추가 가능

    public List<RewardTransaction> getTransactionsForMember(Long memberId) {
        return rewardTransactionRepository.findByMemberId(memberId);
    }

}
