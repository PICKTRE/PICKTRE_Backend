package BAESOOJIN.PICKTRE.api.dto.trash;


import lombok.Data;

@Data
public class TrashRequest {

    private Long memberId;
    private String trashName;
    private int reward;
}
