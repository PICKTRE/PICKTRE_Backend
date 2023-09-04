package BAESOOJIN.PICKTRE.api.controller;

import BAESOOJIN.PICKTRE.api.dto.response.ListResult;
import BAESOOJIN.PICKTRE.api.dto.transaction.TransactionResponse;
import BAESOOJIN.PICKTRE.service.ResponseService;
import BAESOOJIN.PICKTRE.transaction.RewardTransaction;
import BAESOOJIN.PICKTRE.transaction.RewardTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reward-transactions")
@RequiredArgsConstructor
public class RewardTransactionController {

    private final RewardTransactionService rewardTransactionService;
    private final ResponseService responseService;

    /**
     * Member 단일별 사용내역 조회
     * @param msrl
     * @return
     */
    @GetMapping("/{msrl}")
    public ListResult<TransactionResponse> getRewardTransactionsForMember(@PathVariable Long msrl) {
        List<RewardTransaction> transactions = rewardTransactionService.getTransactionsForMember(msrl);

        List<TransactionResponse> transactionResponses = transactions.stream().map(TransactionResponse::toDto).collect(Collectors.toList());

        return responseService.getListResult(transactionResponses);
    }
}
