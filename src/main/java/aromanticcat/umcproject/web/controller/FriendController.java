package aromanticcat.umcproject.web.controller;


import aromanticcat.umcproject.apiPayload.ApiResponse;
import aromanticcat.umcproject.converter.FriendConverter;
import aromanticcat.umcproject.converter.MemberConverter;
import aromanticcat.umcproject.entity.Friend;
import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.service.FriendService.FriendCommandService;
import aromanticcat.umcproject.service.FriendService.FriendQueryService;
import aromanticcat.umcproject.service.MemberService;
import aromanticcat.umcproject.web.dto.Friend.FriendResponseDTO;
import aromanticcat.umcproject.web.dto.Friend.FriendResponseDTO.WaitingFriendDTO;
import aromanticcat.umcproject.web.dto.Member.MemberRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address-book")
@RequiredArgsConstructor
public class FriendController {

    private final FriendQueryService friendQueryService;
    private final FriendCommandService friendCommandService;
    private final MemberService memberService;

    @GetMapping("/")
    @ApiOperation(
            value = "사용자의 주소록에 있는 친구들 조회 API",
            notes = "페이징 포함을 포함합니다, query String으로 page 번호를 주세요, 0번이 1번 페이지입니다." +
                    "sort(정렬 방식, 기본값 'alphabetical')를 주세요." +
                    "정렬 방식은 'alphabetical', 'mailbox_id', 'recent', 'long_time' 중 하나입니다.")
    public ApiResponse<List<FriendResponseDTO.FriendInfoDTO>> getFriendList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "alphabetical") String sort) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 요청 받은 페이지의 친구 수를 가져옴
            List<FriendResponseDTO.FriendInfoDTO> friendDTOList = friendQueryService.findFriendList(userEmail, page, sort);

            // 성공 응답 생성
            return ApiResponse.onSuccess(friendDTOList);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/close-friends")
    @ApiOperation(
            value = "사용자의 주소록에 있는 친한 친구들 조회 API",
            notes = "페이징 포함을 포함합니다, query String으로 page 번호를 주세요. 0번이 1번 페이지입니다." +
                    "sort(정렬 방식, 기본값 'alphabetical')를 주세요." +
                    "정렬 방식은 'alphabetical', 'mailbox_id', 'recent', 'long_time' 중 하나입니다.")
    public ApiResponse<List<FriendResponseDTO.FriendInfoDTO>> getCloseFriendList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "alphabetical") String sort) {

        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 요청 받은 페이지의 친구 수를 가져옴
            List<FriendResponseDTO.FriendInfoDTO> friendDTOList = friendQueryService.findCloseFriendList(userEmail, page, sort);

            // 성공 응답 생성
            return ApiResponse.onSuccess(friendDTOList);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/search/friend")
    @ApiOperation(value = "주소록에서 친구 이름 또는 친구 아이디(우편번호)를 통한 친구 검색 API", notes = "query String으로 친구 정보를 주세요.")
    @Parameters({
            @Parameter(name = "friend_info", description = "검색하고자 하는 친구 정보(닉네임 혹은 아이디), query string입니다!")
    })
    public ApiResponse<List<FriendResponseDTO.FriendInfoDTO>> getFriendbyInfo(
            @RequestParam(value = "friend_info") String friendInfo) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            List<FriendResponseDTO.FriendInfoDTO> friendInfoDTOList;  // 검색하고자 하는 친구 정보와 관련된 DTO 선언

            if (friendInfo.startsWith("#")) {     // 친구 아이디(우편 번호)으로 친구 검색
                String friendIdString = friendInfo.substring(1);    // "#"을 제외한 문자열
                Long friendId = Long.parseLong(friendIdString);     // Long 타입으로 변환
                friendInfoDTOList = friendQueryService.getFriendbyFriendId(userEmail, friendId);
            } else {     // 친구 이름(닉네임)으로 친구 검색
                friendInfoDTOList = friendQueryService.getFriendbyFriendName(userEmail, friendInfo);
            }

            // 성공 응답 생성
            return ApiResponse.onSuccess(friendInfoDTOList);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/search/member")
    @ApiOperation(value = "친구 추가에서 사용자 아이디(우편번호)를 통한 사용자 검색 API", notes = "query String으로 사용자 정보를 주세요. (예시: #11)")
    @Parameters({
            @Parameter(name = "member_info", description = "검색하고자 하는 멤버 정보(아이디), query string입니다!")
    })
    public ApiResponse<MemberRequestDTO.searchMemberDTO> getMemberbyId(@RequestParam(value = "member_info") String memberInfo) {
        try {

            String memberIdString = memberInfo.substring(1);
            Long memberId = Long.parseLong(memberIdString);     // 친구 추가하려는 사용자 아이디
            Member member = memberService.findByMemberId(memberId);

            MemberRequestDTO.searchMemberDTO memberDTO = MemberConverter.toSearchMemberDTO(member);

            // 성공 응답 생성
            return ApiResponse.onSuccess(memberDTO);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/friend/request")
    @ApiOperation(value = "친구 추가 API", notes = "query String으로 추가하려는 친구의 우편함 번호를 알려주세요.")
    @Parameters({
            @Parameter(name = "to_member_Id", description = "친구 추가 하고자 하는 친구 아이디(우편번호), query string입니다!")
    })
    public ApiResponse<String> sendFriendRequest(@RequestParam(value = "to_member_Id") Long toMemberId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친구 요청 보낸기
            friendCommandService.requestFriendship(userEmail, toMemberId);

            // 성공 응답 생성
            return ApiResponse.onSuccess("친구 요청이 성공적으로 보내졌습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/recieved")
    @ApiOperation(value = "사용자가 친구 추가 받은 요청 조회 API")
    public ApiResponse<List<FriendResponseDTO.WaitingFriendDTO>> getReceivedFriendList() {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친구 요청을 보낸 사용자들의 목록 조회
            List<Friend> friendList = friendQueryService.getFriendReceivedList(userEmail);

            // 친구 정보를 간략하게 변환
            List<FriendResponseDTO.WaitingFriendDTO> waitingFriendDTOS = friendList.stream()
                    .map(FriendConverter::toWaitingFriendDTO)
                    .collect(Collectors.toList());

            // 성공 응답 생성
            return ApiResponse.onSuccess(waitingFriendDTOS);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @GetMapping("/requested")
    @ApiOperation(value = "사용자가 친구 추가 보낸 요청 조회 API")

    public ApiResponse<List<FriendResponseDTO.WaitingFriendDTO>> getRequestedFriendList() {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친구 요청을 보낸 사용자들의 목록 조회
            List<Friend> friendList = friendQueryService.getFriendRequestedList(userEmail);

            // 친구 정보를 간략하게 변환
            List<WaitingFriendDTO> waitingFriendDTOS = friendList.stream()
                    .map(FriendConverter::toWaitingFriendDTO)
                    .collect(Collectors.toList());

            // 성공 응답 생성
            return ApiResponse.onSuccess(waitingFriendDTOS);

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/request/approve")
    @ApiOperation(value = "친구 요청 수락 API", notes = "query String으로 친구 요청을 보낸 친구의 아이디를 알려주세요")
    @Parameters({
            @Parameter(name = "friend_id", description = "친구 추가 요청을 보낸 친구의 아이디, query string입니다!")
    })
    public ApiResponse<String> approveFriendRequest(@RequestParam(value = "friend_id") Long friendId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친구 요청 수락하기
            friendCommandService.approveFriendship(userEmail, friendId);

            // 성공 응답 생성
            return ApiResponse.onSuccess("친구 요청이 성공적으로 수락되었습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/request/reject")
    @ApiOperation(value = "친구 요청 거절 API", notes = "query String으로 친구 요청을 보낸 친구의 아이디를 알려주세요")
    @Parameters({
            @Parameter(name = "friend_id", description = "친구 추가 요청을 보낸 친구의 아이디, query string입니다!")
    })
    public ApiResponse<String> rejectFriendRequest(@RequestParam(value = "friend_id") Long friendId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친구 요청 거절하기
            friendCommandService.rejectFriendship(userEmail, friendId);

            // 성공 응답 생성
            return ApiResponse.onSuccess("친구 요청이 성공적으로 거절 되었습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/close-friend/register")
    @ApiOperation(value = "친한 친구 등록 API", notes = "query String으로 친한 친구로 등록하려는 친구 아이디를 알려주세요.")
    @Parameters({
            @Parameter(name = "friend_id", description = "친한 친구로 등록하려는 친구의 아이디, query string입니다!")
    })
    public ApiResponse<String> registerCloseFriend(@RequestParam(value = "friend_id") Long friendId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친한 친구로 등록하기
            friendCommandService.setCloseFriend(userEmail, friendId);

            // 성공 응답 생성
            return ApiResponse.onSuccess("친구를 친한 친구로 등록하는데 성공했습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

    @PostMapping("/close-friend/delete")
    @ApiOperation(value = "친한 친구 해제 API", notes = "query String으로 친한 친구를 해제하려는 친구 아이디를 알려주세요.")
    @Parameters({
            @Parameter(name = "friend_id", description = "친한 친구를 해제하려는 친구의 아이디, query string입니다!")
    })
    public ApiResponse<String> deleteCloseFriend(@RequestParam(value = "friend_id") Long friendId) {
        try {
            String userEmail = memberService.getUserInfo().getEmail();

            // 친한 친구 해제하기
            friendCommandService.deleteCloseFriend(userEmail, friendId);

            // 성공 응답 생성
            return ApiResponse.onSuccess("친한 친구를 해제하는데 성공했습니다.");

        } catch (Exception e) {
            return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(), null);
        }
    }

}
