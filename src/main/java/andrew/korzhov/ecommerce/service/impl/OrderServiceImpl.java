package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.CartItem;
import andrew.korzhov.ecommerce.domain.Order;
import andrew.korzhov.ecommerce.domain.repository.CartItemRepository;
import andrew.korzhov.ecommerce.domain.repository.OrderRepository;
import andrew.korzhov.ecommerce.service.OrderService;
import andrew.korzhov.ecommerce.service.mapper.OrderMapper;
import andrew.korzhov.ecommerce.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public OrderDto save(long userId) {
        List<CartItem> cart = cartItemRepository.getAllByUserId(userId);
        BigDecimal totalAmount = new BigDecimal("0");
        for (CartItem c : cart) {
            totalAmount = totalAmount.add(c.getTotal());
        }
        Order order = new Order();
        order.setTotalAmount(totalAmount);
        order.setUserId(userId);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDto getById(long id) {
        return orderMapper.toDto(orderRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        orderRepository.deleteById(id);
    }
}
