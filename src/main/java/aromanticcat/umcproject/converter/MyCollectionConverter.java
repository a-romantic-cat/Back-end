package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.web.dto.MyCollectionResponseDTO;

public class MyCollectionConverter{
    public static MyCollectionResponseDTO.AcquiredLetterPaperResultDTO toAcquiredLetterPaperResultDTO(AcquiredItem letterPaper){
        return MyCollectionResponseDTO.AcquiredLetterPaperResultDTO.builder()
                .letterPaperId(letterPaper.getLetterPaper().getId())
                .letterPaperName(letterPaper.getLetterPaper().getName())
                .letterPaperImageUrl(letterPaper.getLetterPaper().getImageUrl())
                .acquiredAt(letterPaper.getCreatedAt())
                .build();
    }

    public static MyCollectionResponseDTO.AcquiredStampResultDTO toAcquiredStampResultDTO(AcquiredItem stamp){
        return MyCollectionResponseDTO.AcquiredStampResultDTO.builder()
                .stampId(stamp.getStamp().getId())
                .stampName(stamp.getStamp().getName())
                .stampImageUrl(stamp.getStamp().getImageUrl())
                .acquiredAt(stamp.getCreatedAt())
                .build();
    }
}
