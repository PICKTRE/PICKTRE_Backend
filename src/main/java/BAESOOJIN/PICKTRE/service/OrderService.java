package BAESOOJIN.PICKTRE.service;

import BAESOOJIN.PICKTRE.domain.member.Member;
import BAESOOJIN.PICKTRE.domain.order.Order;
import BAESOOJIN.PICKTRE.domain.order.OrderItem;
import BAESOOJIN.PICKTRE.domain.product.Product;
import BAESOOJIN.PICKTRE.repository.OrderRepository;
import BAESOOJIN.PICKTRE.repository.ProductRepository;
import BAESOOJIN.PICKTRE.transaction.RewardTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final RewardTransactionService rewardTransactionService;

    /**
     * 주문 생성
     *
     * @param memberId       주문자 ID
    //     * @param productIds     상품 ID 목록
    //     * @param quantities     상품 수량 목록
     * @param useRewardPoints 리워드 포인트 사용 여부
     * @return 생성된 주문 엔티티
     */
    public Order createOrder(Long memberId, Long productId, Integer quantity, boolean useRewardPoints) {
        Member member = memberService.getMember(memberId);
        Product product = productService.getProduct(productId);
        OrderItem orderItem = orderItemService.createOrderItem(product, quantity);

        Order order = new Order(member);
        order.addOrderItem(orderItem);
        order.setUseRewardPoints(useRewardPoints);

        // 리워드 포인트 차감 로직 추가
        if (useRewardPoints) {
            int totalRewardPointsNeeded = calculateTotalRewardPointsNeeded(orderItem);
            if (member.getRewardPoints() >= totalRewardPointsNeeded) {
                rewardTransactionService.useRewardPoints(member, product.getName(),quantity,totalRewardPointsNeeded);
            } else {
                throw new RuntimeException("Not enough reward points");
            }
        }

        // 주문 생성
        Order createdOrder = orderRepository.save(order);

        // 주문이 생성되었을 때 상품의 수량 감소
        if (createdOrder != null) {
            updateProductQuantity(orderItem.getProduct(), orderItem.getQuantity());
        }

        return createdOrder;
    }


    /**
     * 주문에 필요한 총 리워드 포인트 계산
     *
     * @param orderItem
     * @return
     */
    private int calculateTotalRewardPointsNeeded(OrderItem orderItem) {
        return orderItem.getProduct().getPrice() * orderItem.getQuantity();
    }

    /**
     * 주문 시 Product Quantity 수정
     *
     * @param product
     * @param quantity
     */
    private void updateProductQuantity(Product product, int quantity) {
        int updatedQuantity = product.getQuantity() - quantity;
        if (updatedQuantity < 0) {
            throw new RuntimeException("Not enough products in stock");
        }
        product.updateQuantity(updatedQuantity);
    }
}
