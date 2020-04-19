package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.CartItem;
import andrew.korzhov.ecommerce.domain.OrderEntry;
import andrew.korzhov.ecommerce.domain.repository.CartItemRepository;
import andrew.korzhov.ecommerce.domain.repository.OrderEntryRepository;
import andrew.korzhov.ecommerce.service.OrderEntryService;
import andrew.korzhov.ecommerce.service.mapper.OrderEntryMapper;
import andrew.korzhov.ecommerce.web.dto.OrderEntryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderEntryServiceImpl implements OrderEntryService {

    private final OrderEntryRepository entryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderEntryMapper entryMapper;

    @Override
    @Transactional
    public List<OrderEntryDto> getResult(long orderId, long userId) {
        List<CartItem> cart = cartItemRepository.getAllByUserId(userId);
        List<OrderEntry> orderEntries = cart.stream()
                .map(c -> getOrderEntry(orderId, c))
                .collect(Collectors.toList());

        entryRepository.saveAll(orderEntries);

        cartItemRepository.deleteByUserId(userId);

        return orderEntries.stream()
                .map(entryMapper::toDto)
                .collect(Collectors.toList());
    }

    private OrderEntry getOrderEntry(long orderId, CartItem c) {
        OrderEntry oe = new OrderEntry();

        BigDecimal total = c.getTotal();
        int productQuantity = c.getProductQuantity();

        oe.setProductId(c.getProductId());
        oe.setOrderId(orderId);
        oe.setProductQuantity(productQuantity);
        oe.setTotal(total);
        BigDecimal forOne = total.divide(
                BigDecimal.valueOf(productQuantity),
                new MathContext(0)
        );
        oe.setPrice(forOne);
        return oe;
    }
}
