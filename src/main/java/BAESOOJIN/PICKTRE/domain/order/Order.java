package BAESOOJIN.PICKTRE.domain.order;

import BAESOOJIN.PICKTRE.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDateTime orderDate; // 주문한 날짜와 시간

    private int totalPrice; // 주문 총 가격

    private boolean useRewardPoints; // 리워드 포인트 사용 여부

    @ManyToOne
    @JoinColumn(name = "buyer_id") // Member 엔티티와의 관계 설정을 위한 외래키
    private Member buyer; // 주문의 구매자

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>(); // 주문에 속한 상품들

    // Constructors, getters, setters, and other methods

    /**
     * 기본 생성자
     * @param member
     */
    @Builder
    public Order(Member member,LocalDateTime localDateTime,int totalPrice) {
        this.buyer=member;
        this.orderDate = localDateTime;
        this.totalPrice = totalPrice;
    }


    /**
     * 주문에 상품을 추가하고 관계 설정
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        calculateTotalPrice();
    }

    /**
     * 주문한 상품들의 가격을 합산하여 총 가격 계산
     */
    public void calculateTotalPrice() {
        this.totalPrice = orderItems.stream().mapToInt(OrderItem::getSubtotal).sum();
    }


    /**
     * RewardPoints 사용 여부 Setter
     * @param useRewardPoints
     */
    public void setUseRewardPoints(boolean useRewardPoints) {
        this.useRewardPoints=useRewardPoints;
    }
    // 다른 주문 관련 메소드도 추가 가능
}