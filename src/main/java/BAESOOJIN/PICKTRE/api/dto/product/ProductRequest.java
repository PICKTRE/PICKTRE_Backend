package BAESOOJIN.PICKTRE.api.dto.product;

import lombok.Data;

@Data
public class ProductRequest {

    private String productName;
    private int price;
    private int quantity;
}
