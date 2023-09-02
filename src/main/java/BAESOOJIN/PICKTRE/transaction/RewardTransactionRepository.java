package BAESOOJIN.PICKTRE.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardTransactionRepository extends JpaRepository<RewardTransaction,Long> {
    List<RewardTransaction> findByMemberId(Long memberId);
}
