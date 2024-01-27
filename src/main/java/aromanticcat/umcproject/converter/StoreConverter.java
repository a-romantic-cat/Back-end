package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.AcquiredItem;
import aromanticcat.umcproject.entity.LetterPaper;
import aromanticcat.umcproject.web.dto.store.StoreResponseDTO;

import java.util.List;

public class StoreConverter {


    public static StoreResponseDTO.LetterPaperResultDTO toLetterPaperResultDTO(LetterPaper letterPaper, List<AcquiredItem> acquiredItemList){
        boolean isAcquired = isLetterPaperAcquired(letterPaper, acquiredItemList);
        Integer price = isAcquired ? null : letterPaper.getPrice();

        return StoreResponseDTO.LetterPaperResultDTO.builder()
                .letterPaperId(letterPaper.getId())
                .letterPaperName(letterPaper.getName())
                .letterPaperImageUrl(letterPaper.getImage_url())
                .price(price)
                .createdAt(letterPaper.getCreatedAt())
                .build();
    }

    private static boolean isLetterPaperAcquired(LetterPaper letterPaper, List<AcquiredItem> acquiredItemList){

        return acquiredItemList.stream()
                .anyMatch(acquredItem -> acquredItem.getLetterPaper().getId().equals(letterPaper.getId()));
    }
}
