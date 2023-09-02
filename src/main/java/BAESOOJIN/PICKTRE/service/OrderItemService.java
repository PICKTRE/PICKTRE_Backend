package BAESOOJIN.PICKTRE.service;

import BAESOOJIN.PICKTRE.domain.order.OrderItem;
import BAESOOJIN.PICKTRE.domain.product.Product;
import BAESOOJIN.PICKTRE.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    /**
     * 주문 상품 생성
     *
     * @param product   주문 상품
     * @param quantity  수량
     * @return 생성된 주문 상품 엔티티
     */
    public OrderItem createOrderItem(Product product, int quantity) {
        OrderItem orderItem = new OrderItem(product, quantity);
        return orderItemRepository.save(orderItem);
    }

    // 다른 주문 상품 관련 서비스 메소드 추가 가능
}

