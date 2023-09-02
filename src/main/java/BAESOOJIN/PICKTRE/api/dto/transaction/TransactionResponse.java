package BAESOOJIN.PICKTRE.api.dto.transaction;

import BAESOOJIN.PICKTRE.transaction.RewardTransaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private int points;
    private LocalDateTime transactionDate;

    private String productName;
    private Integer productCount;

    public TransactionResponse(RewardTransaction rewardTransaction) {
        this.points = rewardTransaction.getPoints();
        this.transactionDate = rewardTransaction.getTransactionDateTime();
        this.productName = rewardTransaction.getProductName();
        this.productCount = rewardTransaction.getProductCount();
    }

    public static TransactionResponse toDto(RewardTransaction rewardTransaction) {
        return new TransactionResponse(rewardTransaction);
    }
}

