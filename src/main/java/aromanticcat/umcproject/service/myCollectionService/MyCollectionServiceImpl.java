package aromanticcat.umcproject.service.myCollectionService;

import aromanticcat.umcproject.converter.MyCollectionConverter;
import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.MyLetterPaper;
import aromanticcat.umcproject.entity.MyStamp;
import aromanticcat.umcproject.repository.AcquiredItemRepository;
import aromanticcat.umcproject.repository.MyLetterPaperRepository;
import aromanticcat.umcproject.repository.MyStampRepository;
import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyCollectionServiceImpl implements MyCollectionService {

    private final AcquiredItemRepository acquiredItemRepository;
    private final MyLetterPaperRepository myLetterPaperRepository;
    private final MyStampRepository myStampRepository;

    @Override
    @Transactional
    public List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> findLetterPaperList(Long memberId, int page, int pageSize, boolean onlyMyDesign) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> responseDTOs;

        if(onlyMyDesign) {
            Page<MyLetterPaper> myLetterPaperPage = myLetterPaperRepository.findByMemberId(memberId, pageable);
            List<MyLetterPaper> myLetterPaperList = myLetterPaperPage.getContent();

            responseDTOs = myLetterPaperList.stream()
                    .map(myLetterPaper -> MyCollectionConverter.toMyLetterPaperResultDTO(myLetterPaper))
                    .collect(Collectors.toList());
        }else {
            // 사용자가 구매한 편지지 목록 조회
            Page<AcquiredItem> acquiredLetterPaperPage = acquiredItemRepository.findByMemberIdAndLetterPaperIdIsNotNull(memberId, pageable);
            List<AcquiredItem> acquiredLetterPaperList = acquiredLetterPaperPage.getContent();

            // 마이 디자인 편지지 목록 조회
            Page<MyLetterPaper> myLetterPaperPage = myLetterPaperRepository.findByMemberId(memberId, pageable);
            List<MyLetterPaper> myLetterPaperList = myLetterPaperPage.getContent();

            // MyLetterPaper와 AcquredItem에서 가져온 편지지 목록 병합
            List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> myLetterPaperDTOs = myLetterPaperList.stream()
                    .map(myLetterPaper -> MyCollectionConverter.toMyLetterPaperResultDTO(myLetterPaper))
                    .collect(Collectors.toList());

            List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO> acquiredLetterPaperDTOs = acquiredLetterPaperList.stream()
                    .map(letterPaper -> MyCollectionConverter.toAcquiredLetterPaperResultDTO(letterPaper))
                    .collect(Collectors.toList());

            responseDTOs = new ArrayList<>();
            responseDTOs.addAll(myLetterPaperDTOs);
            responseDTOs.addAll(acquiredLetterPaperDTOs);
        }
        return responseDTOs;

    }

    @Override
    @Transactional
    public List<MyCollectionResponseDTO.AcquiredStampResultDTO> findStampList(Long memberId, int page, int pageSize, boolean onlyMyDesign) {
        Pageable pageable = PageRequest.of(page, pageSize);
        List<MyCollectionResponseDTO.AcquiredStampResultDTO> responseDTOs;

        if(onlyMyDesign){
            Page<MyStamp> myStampPage = myStampRepository.findByMemberId(memberId, pageable);

            List<MyStamp> myStampList = myStampPage.getContent();

            responseDTOs = myStampList.stream()
                    .map(myStamp -> MyCollectionConverter.toMyStampResultDTO(myStamp))
                    .collect(Collectors.toList());
        }else{
            // 사용자가 구매한 우표 목록 조회
            Page<AcquiredItem> acquiredStampPage = acquiredItemRepository.findByMemberIdAndStampIdIsNotNull(memberId, pageable);
            List<AcquiredItem> acquiredStampList = acquiredStampPage.getContent();

            // 마이 디자인 우표 목록 조회
            Page<MyStamp> myStampPage = myStampRepository.findByMemberId(memberId, pageable);
            List<MyStamp> myStampList = myStampPage.getContent();

            // MyStamp와 AcquredItem에서 가져온 우표 목록 병합
            List<MyCollectionResponseDTO.AcquiredStampResultDTO> myStampDTOs = myStampList.stream()
                    .map(myStamp -> MyCollectionConverter.toMyStampResultDTO(myStamp))
                    .collect(Collectors.toList());

            List<MyCollectionResponseDTO.AcquiredStampResultDTO> acquiredStampDTOs = acquiredStampList.stream()
                    .map(stamp -> MyCollectionConverter.toAcquiredStampResultDTO(stamp))
                    .collect(Collectors.toList());

            responseDTOs = new ArrayList<>();
            responseDTOs.addAll(myStampDTOs);
            responseDTOs.addAll(acquiredStampDTOs);
        }

        return responseDTOs;

    }




}
