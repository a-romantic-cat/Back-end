package aromanticcat.umcproject.service;

import aromanticcat.umcproject.converter.StoreConverter;
import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.entity.Stamp;
import aromanticcat.umcproject.repository.AcquiredItemRepository;
import aromanticcat.umcproject.repository.LetterPaperRepository;
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


    @Override
    @Transactional
    public List<StoreResponseDTO.LetterPaperResultDTO> findLetterPaperList(Long memberId, int page, int pageSize, boolean purchasedOnly){
        Pageable pageable = PageRequest.of(page, pageSize);

        if(purchasedOnly){
            // 사용자가 구매한 편지지 목록 조회
            Page<AcquiredItem> acquiredLetterPaperPage = acquiredItemRepository.findByMemberIdAndLetterPaperIdIsNotNull(memberId, pageable);

            List<AcquiredItem> acquiredLetterPaperList = acquiredLetterPaperPage.getContent();

            List<StoreResponseDTO.LetterPaperResultDTO> responseDTOs = acquiredLetterPaperList.stream()
                    .map(letterPaper -> StoreConverter.toAcquiedLetterPaperResultDTO(letterPaper))
                    .collect(Collectors.toList());
            return responseDTOs;
        } else {
            // 사용자가 구매한 아이템 목록 조회
            List<AcquiredItem> acquiredItemList = acquiredItemRepository.findByMemberId(memberId);

            // 모든 편지지 목록 조회
            Page<LetterPaper> allLetterPaperPage = letterPaperRepository.findAll(pageable);

            List<LetterPaper> allLetterPaperList = allLetterPaperPage.getContent();

            List<StoreResponseDTO.LetterPaperResultDTO> responseDTOs = allLetterPaperList.stream()
                    .map(letterPaper -> StoreConverter.toLetterPaperResultDTO(letterPaper, acquiredItemList))
                    .collect(Collectors.toList());
            return responseDTOs;
        }



    }

    @Override
    @Transactional
    public List<StoreResponseDTO.StampResultDTO> findStampList(Long memberId, int page, int pageSize){

        // 사용자가 구매한 편지지 목록 조회
        List<AcquiredItem> acquiredItemist = acquiredItemRepository.findByMemberId(memberId);


        // 모든 우표 목록 조회
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Stamp> allStampPage = stampRepository.findAll(pageable);

        List<Stamp> allStampList = allStampPage.getContent();

        List<StoreResponseDTO.StampResultDTO> responseDTOs = allStampList.stream()
                .map(stamp -> StoreConverter.toStampResultDTO(stamp, acquiredItemist))
                .collect(Collectors.toList());

        return responseDTOs;
    }
}
