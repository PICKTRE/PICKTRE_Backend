package BAESOOJIN.PICKTRE.transaction;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardTransactionRepository {
    List<RewardTransaction> findByMemberId(Long memberId);
}
