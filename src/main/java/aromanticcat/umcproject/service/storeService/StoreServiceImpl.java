package aromanticcat.umcproject.service.storeService;

import aromanticcat.umcproject.converter.StoreConverter;
import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.entity.Stamp;
import aromanticcat.umcproject.repository.AcquiredItemRepository;
import aromanticcat.umcproject.repository.LetterPaperRepository;
import aromanticcat.umcproject.repository.MemberRepository;
import aromanticcat.umcproject.repository.StampRepository;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService{

    private final LetterPaperRepository letterPaperRepository;
    private final StampRepository stampRepository;
    private final AcquiredItemRepository acquiredItemRepository;
    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(String email, int page, int pageSize){

        // 사용자가 구매한 아이템 목록 조회
        List<AcquiredItem> acquiredItemList = acquiredItemRepository.findByMemberEmail(email);

        // 모든 편지지 목록 조회
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<LetterPaper> allLetterPaperPage = letterPaperRepository.findAll(pageable);
        List<LetterPaper> allLetterPaperList = allLetterPaperPage.getContent();


        List<StoreResponseDTO.LetterPaperResultDTO> responseDTOs = allLetterPaperList.stream()
                .map(letterPaper -> StoreConverter.toLetterPaperResultDTO(letterPaper, acquiredItemList))
                .collect(Collectors.toList());
        return responseDTOs;
    }

    @Override
    @Transactional
    public List<StoreResponseDTO.StampResultDTO> findStampList(String email, int page, int pageSize){

        // 사용자가 구매한 아이템 목록 조회
        List<AcquiredItem> acquiredItemist = acquiredItemRepository.findByMemberEmail(email);


        // 모든 우표 목록 조회
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Stamp> allStampPage = stampRepository.findAll(pageable);

        List<Stamp> allStampList = allStampPage.getContent();

        List<StoreResponseDTO.StampResultDTO> responseDTOs = allStampList.stream()
                .map(stamp -> StoreConverter.toStampResultDTO(stamp, acquiredItemist))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    @Override
    @Transactional
    public void purchasedLetterPaper(String email, Long letterPaperId){
        // 사용자가 이미 편지를 구매했는지 확인
        boolean isAlreadyPurchased = acquiredItemRepository.existsByMemberEmailAndLetterPaperId(email, letterPaperId);

        if(!isAlreadyPurchased){
            Member member = memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 ID에 해당하는 사용자를 찾을 수 없습니다."));

            LetterPaper letterPaper = letterPaperRepository.findById(letterPaperId).orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 편지지를 찾을 수 없습니다."));

            // 편지의 가격을 회원의 코인에서 차감
            int price = letterPaper.getPrice();
            int remainingCoin = member.getCoin() - price;

            if(remainingCoin < 0) {
                throw new IllegalArgumentException("코인이 부족하여 구매할 수 없습니다.");
            }

            // 회원 코인 업데이트
            member.subtractCoin(price);
            memberRepository.save(member);

            // 획득한 아이템 업데이트
            AcquiredItem acquiredItem = StoreConverter.toAcquiredItem(member, letterPaper);
            acquiredItemRepository.save(acquiredItem);

        } else {
            throw new IllegalArgumentException("이미 해당 편지지를 구매했습니다.");
        }
    }

    @Override
    @Transactional
    public void purchasedStamp(String email, Long stampId){
        // 사용자가 이미 우표를 구매했는지 확인
        boolean isAlreadyPurchased = acquiredItemRepository.existsByMemberEmailAndStampId(email, stampId);

        if(!isAlreadyPurchased){
            Member member = memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("해당 ID에 해당하는 사용자를 찾을 수 없습니다."));

            Stamp stamp = stampRepository.findById(stampId).orElseThrow(() -> new IllegalArgumentException("해당 ID에 해당하는 우표를 찾을 수 없습니다."));

            // 편지의 가격을 회원의 코인에서 차감
            int price = stamp.getPrice();
            int remainingCoin = member.getCoin() - price;

            if(remainingCoin < 0) {
                throw new IllegalArgumentException("코인이 부족하여 구매할 수 없습니다.");
            }

            // 회원 코인 업데이트
            member.subtractCoin(price);
            memberRepository.save(member);

            // 획득한 아이템 업데이트
            AcquiredItem acquiredItem = StoreConverter.toAcquiredItem(member, stamp);
            acquiredItemRepository.save(acquiredItem);

        } else {
            throw new IllegalArgumentException("이미 해당 우표를 구매했습니다.");
        }
    }
}
