package BAESOOJIN.PICKTRE.api.dto.order;


import lombok.Data;

@Data
public class OrderRequest {

    private Long memberId;
    private Long productId;
    private Integer quantity;
    private Boolean useRewardPoints;

}