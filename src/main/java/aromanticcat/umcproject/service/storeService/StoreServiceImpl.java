package aromanticcat.umcproject.service.storeService;

import aromanticcat.umcproject.S3.S3Service;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService{

    private final LetterPaperRepository letterPaperRepository;
    private final StampRepository stampRepository;
    private final AcquiredItemRepository acquiredItemRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    @Transactional
    public int findUserCoin(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        // Optional 객체가 비어있는 경우에 예외를 던지도록 설정
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("사용자가 없습니다."));

        // 사용자가 존재하는 경우에는 해당 사용자의 코인을 반환
        return member.getCoin();
    }


    @Override
    @Transactional
    public List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(String email, int page, int pageSize, String sort) {
        // 사용자가 구매한 아이템 목록 조회
        List<AcquiredItem> acquiredItemList = acquiredItemRepository.findByMemberEmail(email);

        switch (sort) {
            case "alphabetical":
                return findLetterPaperListSortedAlphabetically(page, pageSize, acquiredItemList);
            case "popular":
                return findLetterPaperListSortedByPopularity(page, pageSize, acquiredItemList);
            case "latest":
                return findLetterPaperListSortedByLatest(page, pageSize, acquiredItemList);
            case "low_price":
                return findLetterPaperListSortedByLowPrice(page, pageSize, acquiredItemList);
            case "high_price":
                return findLetterPaperListSortedByHighPrice(page, pageSize, acquiredItemList);
            default:
                throw new IllegalArgumentException("유효하지 않은 정렬 방식입니다: " + sort);

        }
    }
    private List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperListSortedAlphabetically(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        return fetchAndConvertToResponseDTOs(pageable, acquiredItemList);
    }

    private List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperListSortedByPopularity(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        List<AcquiredItem> allAcquiredItems = acquiredItemRepository.findAll();
        Pageable pageable = PageRequest.of(page, pageSize);
        List<LetterPaper> letterPaperList = fetchAndSortByPopularity(pageable, allAcquiredItems);
        return convertToResponseDTOs(letterPaperList, acquiredItemList);
    }

    private List<LetterPaper> fetchAndSortByPopularity(Pageable pageable, List<AcquiredItem> allAcquiredItems){

        Map<LetterPaper, Long> letterPaperPopularityMap = allAcquiredItems.stream()
                .collect(Collectors.groupingBy(AcquiredItem::getLetterPaper, Collectors.counting()));

        List<LetterPaper> sortedLetterPapers = letterPaperPopularityMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedLetterPapers.size());
        return sortedLetterPapers.subList(start, end);
    }


    private List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperListSortedByLatest(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        return fetchAndConvertToResponseDTOs(pageable, acquiredItemList);
    }
    private List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperListSortedByLowPrice(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("price").ascending());
        return fetchAndConvertToResponseDTOs(pageable, acquiredItemList);
    }
    private List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperListSortedByHighPrice(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("price").descending());
        return fetchAndConvertToResponseDTOs(pageable, acquiredItemList);
    }


    private List<StoreResponseDTO.LetterPaperResultDTO> fetchAndConvertToResponseDTOs(Pageable pageable, List<AcquiredItem> acquiredItemList) {
        Page<LetterPaper> letterPaperPage = letterPaperRepository.findAll(pageable);
        return convertToResponseDTOs(letterPaperPage.getContent(), acquiredItemList);
    }

    private List<StoreResponseDTO.LetterPaperResultDTO> convertToResponseDTOs(List<LetterPaper> letterPaperList, List<AcquiredItem> acquiredItemList){
        return letterPaperList.stream()
            .map(letterPaper -> StoreConverter.toLetterPaperResultDTO(letterPaper, acquiredItemList))
            .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<StoreResponseDTO.StampResultDTO> findStampList(String email, int page, int pageSize, String sort){
        // 사용자가 구매한 아이템 목록 조회
        List<AcquiredItem> acquiredItemList = acquiredItemRepository.findByMemberEmail(email);

        switch (sort) {
            case "alphabetical":
                return findStampListSortedAlphabetically(page, pageSize, acquiredItemList);
            case "popular":
                return findStampListSortedByPopularity(page, pageSize, acquiredItemList);
            case "latest":
                return findStampListSortedByLatest(page, pageSize, acquiredItemList);
            case "low_price":
                return findStampListSortedByLowPrice(page, pageSize, acquiredItemList);
            case "high_price":
                return findStampListSortedByHighPrice(page, pageSize, acquiredItemList);
            default:
                throw new IllegalArgumentException("유효하지 않은 정렬 방식입니다: " + sort);

        }


//        // 모든 우표 목록 조회
//        Pageable pageable = PageRequest.of(page, pageSize);
//        Page<Stamp> allStampPage = stampRepository.findAll(pageable);
//
//        List<Stamp> allStampList = allStampPage.getContent();
//
//        List<StoreResponseDTO.StampResultDTO> responseDTOs = allStampList.stream()
//                .map(stamp -> StoreConverter.toStampResultDTO(stamp, acquiredItemist))
//                .collect(Collectors.toList());
//
//        return responseDTOs;
    }


    private List<StoreResponseDTO.StampResultDTO> findStampListSortedAlphabetically(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("name").ascending());
        return fetchAndConvertToStampResponseDTOs(pageable, acquiredItemList);
    }

    private List<StoreResponseDTO.StampResultDTO> findStampListSortedByPopularity(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        List<AcquiredItem> allAcquiredItems = acquiredItemRepository.findAll();
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Stamp> StampList = fetchAndSortStampByPopularity(pageable, allAcquiredItems);
        return convertToStampResponseDTOs(StampList, acquiredItemList);
    }

    private List<Stamp> fetchAndSortStampByPopularity (Pageable pageable, List<AcquiredItem> allAcquiredItems){

        Map<Stamp, Long> stampPopularityMap = allAcquiredItems.stream()
                .collect(Collectors.groupingBy(AcquiredItem::getStamp, Collectors.counting()));

        List<Stamp> sortedStamps = stampPopularityMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedStamps.size());
        return sortedStamps.subList(start, end);
    }


    private List<StoreResponseDTO.StampResultDTO> findStampListSortedByLatest(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        return fetchAndConvertToStampResponseDTOs(pageable, acquiredItemList);
    }
    private List<StoreResponseDTO.StampResultDTO> findStampListSortedByLowPrice(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("price").ascending());
        return fetchAndConvertToStampResponseDTOs(pageable, acquiredItemList);
    }
    private List<StoreResponseDTO.StampResultDTO> findStampListSortedByHighPrice(int page, int pageSize, List<AcquiredItem> acquiredItemList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("price").descending());
        return fetchAndConvertToStampResponseDTOs(pageable, acquiredItemList);
    }


    private List<StoreResponseDTO.StampResultDTO> fetchAndConvertToStampResponseDTOs(Pageable pageable, List<AcquiredItem> acquiredItemList) {
        Page<Stamp> StampPage = stampRepository.findAll(pageable);
        return convertToStampResponseDTOs(StampPage.getContent(), acquiredItemList);
    }

    private List<StoreResponseDTO.StampResultDTO> convertToStampResponseDTOs(List<Stamp> stampList, List<AcquiredItem> acquiredItemList){
        return stampList.stream()
                .map(stamp -> StoreConverter.toStampResultDTO(stamp, acquiredItemList))
                .collect(Collectors.toList());
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

    @Override
    @Transactional
    public Long uploadLetterPaper(MultipartFile file, String name, int price) throws IOException{
        String url = s3Service.uploadFile1(file);
        LetterPaper letterPaper = LetterPaper.builder()
                .name(name)
                .imageUrl(url)
                .price(price)
                .build();
        letterPaperRepository.save(letterPaper);
        return letterPaper.getId();
    }

    @Override
    @Transactional
    public Long uploadStamp(MultipartFile file, String name, int price) throws IOException{
        String url = s3Service.uploadFile2(file);
        Stamp stamp = Stamp.builder()
                .name(name)
                .imageUrl(url)
                .price(price)
                .build();
        stampRepository.save(stamp);
        return stamp.getId();
    }
}
