package BAESOOJIN.PICKTRE.api.controller;

import BAESOOJIN.PICKTRE.api.dto.order.OrderRequest;
import BAESOOJIN.PICKTRE.api.dto.response.CommonResult;
import BAESOOJIN.PICKTRE.domain.order.Order;
import BAESOOJIN.PICKTRE.domain.order.OrderItem;
import BAESOOJIN.PICKTRE.service.OrderItemService;
import BAESOOJIN.PICKTRE.service.OrderService;
import BAESOOJIN.PICKTRE.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ResponseService responseService;

    /**
     * Ordre 생성
     * @param orderRequest Order 생성 하기 위한 DTO
     * @return 성공 DTO
     */
    @PostMapping
    public CommonResult createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest.getMemberId(),
                orderRequest.getProductId(),
                orderRequest.getQuantity(),
                orderRequest.getUseRewardPoints());

        List<Long> orderItemIds = new ArrayList<>();
        for (OrderItem orderItem : createdOrder.getOrderItems()) {
            orderItemIds.add(orderItem.getId());
        }

        return responseService.getSuccessResult();
    }
}

