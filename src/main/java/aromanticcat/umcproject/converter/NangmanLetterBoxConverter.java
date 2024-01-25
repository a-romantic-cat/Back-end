package aromanticcat.umcproject.converter;

import aromanticcat.umcproject.entity.Member;
import aromanticcat.umcproject.entity.NangmanLetter;
import aromanticcat.umcproject.entity.NangmanReply;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxRequestDTO;
import aromanticcat.umcproject.web.dto.nangmanLetterBox.NangmanLetterBoxResponseDTO;

public class NangmanLetterBoxConverter {

    public static NangmanLetterBoxResponseDTO.SendLetterResultDTO toWriteLetterResultDTO(NangmanLetter nangmanLetter){

        return NangmanLetterBoxResponseDTO.SendLetterResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .senderNickname(nangmanLetter.getSenderNickname())
                .createdAt(nangmanLetter.getCreatedAt())
                .build();
    }

    public static NangmanLetter toNangmanLetter(NangmanLetterBoxRequestDTO.WriteLetterDTO request, Member member){

        return NangmanLetter.builder()
                .isPublic(request.getIsPublic())
                .content(request.getContent())
                .senderNickname(request.getSenderRandomNickname())
                .member(member)
                .build();
    }

    public static NangmanLetterBoxResponseDTO.PreviewLetterResultDTO toPreviewLetterResultDTO(NangmanLetter nangmanLetter){
        return NangmanLetterBoxResponseDTO.PreviewLetterResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .preview(getPreviewText(nangmanLetter.getContent()))
                .createdAt(nangmanLetter.getCreatedAt())
                .build();
    }

    public static NangmanLetterBoxResponseDTO.SelectedLetterResultDTO toSelectedLetterResultDTO(NangmanLetter nangmanLetter, String randomNickname){
        return NangmanLetterBoxResponseDTO.SelectedLetterResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .nangmanLetterContent(nangmanLetter.getContent())
                .senderNickname(nangmanLetter.getSenderNickname())
                .replySenderNickname(randomNickname)
                .build();
    }

    public static NangmanReply toNangmanReply(NangmanLetterBoxRequestDTO.WriteReplyDTO request, NangmanLetter nangmanLetter, Member member){
        return NangmanReply.builder()
                .content(request.getReplyContent())
                .replySenderNickname(request.getReplySenderNickname())
                .nangmanLetter(nangmanLetter)
                .member(member)
                .build();

    }

    public static NangmanLetterBoxResponseDTO.WriteReplyResultDTO toWriteReplyResultDTO(NangmanReply nangmanReply){
        return NangmanLetterBoxResponseDTO.WriteReplyResultDTO.builder()
                .nangmanReplyId(nangmanReply.getId())
                .nangmanLetterId(nangmanReply.getNangmanLetter().getId())
                .replySenderNickname(nangmanReply.getReplySenderNickname())
                .createdAt(nangmanReply.getCreatedAt())
                .build();
    }

    public static NangmanLetterBoxResponseDTO.PreviewReplyResultDTO toPreviewReplyResultDTO(NangmanReply nangmanReply) {

        return NangmanLetterBoxResponseDTO.PreviewReplyResultDTO.builder()
                .nangmanReplyId(nangmanReply.getId())
                .nangmanLetterId(nangmanReply.getNangmanLetter().getId())
                .preview(getPreviewText(nangmanReply.getContent()))
                .totalEmojiCount(calculateTotalEmojiCount(nangmanReply.getNangmanLetter()))
                .build();

    }


    private static String getPreviewText(String content){
        // 답장 내용을 40자로 제한
        return content.length() <= 40 ? content : content.substring(0, 40) + "...";

    }


    public static NangmanLetterBoxResponseDTO.PreviewBothResultDTO toPreviewBothResultDTO(NangmanLetter nangmanLetter, NangmanReply nangmanReply){
        return NangmanLetterBoxResponseDTO.PreviewBothResultDTO.builder()
                .nangmanLetterId(nangmanLetter.getId())
                .nangmanReplyId(nangmanReply.getId())
                .previewLetter(getPreviewText(nangmanLetter.getContent()))
                .previewReply(getPreviewText(nangmanReply.getContent()))
                .totalEmojiCount(calculateTotalEmojiCount(nangmanLetter))
                .createAt(nangmanReply.getCreatedAt())
                .build();
    }

    private static Integer calculateTotalEmojiCount(NangmanLetter nangmanLetter){
        // isPublic이 false이거나 hasResponse가 false이면 이모지 수를 계산하지 않음
        if(!nangmanLetter.getIsPublic() || !nangmanLetter.getHasResponse()) {
            return null;
        }

        Integer thumbsUpCnt = nangmanLetter.getThumbsUpCnt();
        Integer heartCnt = nangmanLetter.getHeartCnt();
        Integer cryingCnt = nangmanLetter.getCryingCnt();
        Integer cloverCnt = nangmanLetter.getCloverCnt();
        Integer clapCnt = nangmanLetter.getClapCnt();
        Integer starCnt = nangmanLetter.getStarCnt();

        // 각 이모지 수가 null인 경우 0으로 간주하여 합산
        thumbsUpCnt = thumbsUpCnt != null ? thumbsUpCnt : 0;
        heartCnt = heartCnt != null ? heartCnt : 0;
        cryingCnt = cryingCnt != null ? cryingCnt : 0;
        cloverCnt = cloverCnt != null ? cloverCnt : 0;
        clapCnt = clapCnt != null ? clapCnt : 0;
        starCnt = starCnt != null ? starCnt : 0;

        return thumbsUpCnt + heartCnt + cryingCnt + cloverCnt + clapCnt + starCnt;

    }
}
