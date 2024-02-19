package aromanticcat.umcproject.repository;

import aromanticcat.umcproject.entity.Friend;
import aromanticcat.umcproject.entity.FriendStatus;
import aromanticcat.umcproject.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT f FROM Friend f WHERE f.member = :member AND f.friendStatus IN (:friendStatus)")
    Page<Friend> findFriendByMemberAndFriendStatus(Member member, Set<FriendStatus> friendStatus, Pageable pageable);

    List<Friend> findFriendByMemberAndFriendName(Member member, String friendName);

    List<Friend> findFriendByMemberAndFriendId(Member member, Long friendId);

    Friend findByMemberAndFriendId(Member member, Long friendId);
}
