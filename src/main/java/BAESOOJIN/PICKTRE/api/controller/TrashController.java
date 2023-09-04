package BAESOOJIN.PICKTRE.api.controller;

import BAESOOJIN.PICKTRE.api.dto.response.CommonResult;
import BAESOOJIN.PICKTRE.api.dto.trash.TrashRequest;
import BAESOOJIN.PICKTRE.domain.member.Member;
import BAESOOJIN.PICKTRE.service.MemberService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import BAESOOJIN.PICKTRE.transaction.RewardTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trash")
public class TrashController {

    private final MemberService memberService;
    private final ResponseService responseService;
    private final RewardTransactionService rewardTransactionService;

    @PostMapping
    public CommonResult updateTrashReward(@RequestBody TrashRequest trashRequest) {
        Member findMember = memberService.getMember(trashRequest.getMemberId());
        findMember.setTrashCount(findMember.getTrashCount()+1);
        rewardTransactionService.earnRewardPoints(findMember,trashRequest.getTrashName(),1,trashRequest.getReward());
        memberService.updateTier(findMember);
        return responseService.getSuccessResult();
    }

}
