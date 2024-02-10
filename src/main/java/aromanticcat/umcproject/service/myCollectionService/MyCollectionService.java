package aromanticcat.umcproject.service.myCollectionService;

import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;

import java.util.List;

public interface MyCollectionService {

    List<MyCollectionResponseDTO.AcquiredLetterPaperResultDTO>  findLetterPaperList(String memberEmail, int page, int pageSize, boolean onlyMyDesign);
    List<MyCollectionResponseDTO.AcquiredStampResultDTO>  findStampList(String memberEmail, int page, int pageSize, boolean onlyMyDesign);
}
