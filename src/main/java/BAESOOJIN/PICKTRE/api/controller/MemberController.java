package BAESOOJIN.PICKTRE.api.controller;


import BAESOOJIN.PICKTRE.api.dto.member.MemberRequest;
import BAESOOJIN.PICKTRE.api.dto.member.MemberResponse;
import BAESOOJIN.PICKTRE.api.dto.response.CommonResult;
import BAESOOJIN.PICKTRE.api.dto.response.ListResult;
import BAESOOJIN.PICKTRE.api.dto.response.SingleResult;
import BAESOOJIN.PICKTRE.domain.member.Member;
import BAESOOJIN.PICKTRE.service.MemberService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;



    @GetMapping
    public ListResult<MemberResponse> findAllUser(){
        List<Member> members = memberService.getAllMembers();
        List<MemberResponse> memberResponseList = members.stream().map(MemberResponse::toDto).collect(Collectors.toList());
        return responseService.getListResult(memberResponseList);
    }

    @GetMapping("/{msrl}")
    public SingleResult<MemberResponse> findUserById(@PathVariable Long msrl) {
        Member member = memberService.getMember(msrl);
        memberService.update_Tier(member);
        MemberResponse memberResponse = MemberResponse.toDto(member);
        return responseService.getSingleResult(memberResponse);
    }
//
////     회원 가입
//    @PostMapping
//    public CommonResult createMember(@RequestBody MemberRequest memberRequest) {
//        Member member=new Member("hys339631@mail.com",memberRequest.getUserName());
//        memberService.createMember(member);
//        return responseService.getSuccessResult();
//    }

    @PutMapping("/{msrl}")
    public SingleResult<MemberResponse> updateMember(@PathVariable Long msrl, @RequestBody MemberRequest memberRequest) {
        Member memberToUpdate = memberService.getMember(msrl);
        memberToUpdate.setMail(memberRequest.getUserName());

        Member updatedMember = memberService.updateMember(memberToUpdate);
        MemberResponse memberResponse = MemberResponse.toDto(updatedMember);
        return responseService.getSingleResult(memberResponse);
    }

    @DeleteMapping("/{msrl}")
    public CommonResult deleteMember(@PathVariable Long msrl) {
        memberService.deleteMember(msrl);
        return responseService.getSuccessResult();
    }

    @GetMapping("/reset")
    public CommonResult updateAll() {
        memberService.updateTodayReward();
        return responseService.getSuccessResult();
    }


    // 다른 회원과 관련된 API 메소드 추가 가능
}