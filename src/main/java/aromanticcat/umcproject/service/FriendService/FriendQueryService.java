package aromanticcat.umcproject.service.FriendService;

import aromanticcat.umcproject.entity.Friend;
import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.web.dto.Friend.FriendResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FriendQueryService {

    List<Friend> getFriendReceivedList(String userEmail);

    List<Friend> getFriendRequestedList(String userEmail);

    List<FriendResponseDTO.FriendInfoDTO> findFriendList(String userEmail, Integer page, String sort);

    List<FriendResponseDTO.FriendInfoDTO> findCloseFriendList(String userEmail, Integer page, String sort);

    List<FriendResponseDTO.FriendInfoDTO> getFriendbyFriendName(String userEmail, String friendName);

    List<FriendResponseDTO.FriendInfoDTO> getFriendbyFriendId(String userEmail, Long friendId);
}
