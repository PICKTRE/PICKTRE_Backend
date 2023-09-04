package BAESOOJIN.PICKTRE.domain.product;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 상품 이름
    private int price; // 상품 가격
    private int quantity; // 상품 수량
    private String imagePath; // 상품 이미지 파일 경로

    private int viewCount;

    @Builder
    public Product(String name,String imagePath,int price, int quantity,int viewCount) {
        this.name = name;
        this.imagePath=imagePath;
        this.price = price;
        this.quantity = quantity;
        this.viewCount=viewCount;
    }

    public void updateProduct(String name,int price,int quantity) {
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }

    public void addViewCount(int viewCount) {
        this.viewCount=viewCount;
    }
}

