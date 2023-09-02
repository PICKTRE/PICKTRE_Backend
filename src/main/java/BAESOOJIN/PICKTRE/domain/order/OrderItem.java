package BAESOOJIN.PICKTRE.domain.order;

import BAESOOJIN.PICKTRE.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id") // Order 엔티티와의 관계 설정을 위한 외래키
    private Order order; // 주문

    @ManyToOne
    @JoinColumn(name = "product_id") // Product 엔티티와의 관계 설정을 위한 외래키
    private Product product; // 주문한 상품

    private int quantity; // 주문한 상품의 수량


    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    // 주문한 상품의 소계 가격 계산
    public int getSubtotal() {
        return product.getPrice() * quantity;
    }
}
