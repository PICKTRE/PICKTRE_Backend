package BAESOOJIN.PICKTRE.api.dto.product;

import BAESOOJIN.PICKTRE.domain.product.Product;
import lombok.Data;

@Data
public class ProductResponse {

    private Long productId;
    private String productName;

    private String imagePath;
    private int price;
    private int quantity;

    private int viewCount;

    public ProductResponse(Product product) {
        this.productId=product.getId();
        this.productName = product.getName();
        this.imagePath=product.getImagePath();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.viewCount=product.getViewCount();
    }

    public static ProductResponse toDto(Product product) {
        return new ProductResponse(product);
    }
}
