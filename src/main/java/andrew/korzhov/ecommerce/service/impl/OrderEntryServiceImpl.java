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
    public List<OrderEntryDto> getResult(final long orderId, final long userId) {
        List<CartItem> cart = cartItemRepository.getAllByUserId(userId);
        List<OrderEntry> orderEntries = cart.stream()
                .map(c -> migrateToOrderEntry(orderId, c))
                .collect(Collectors.toList());

        entryRepository.saveAll(orderEntries);

        cartItemRepository.deleteByUserId(userId);

        return orderEntries.stream()
                .map(entryMapper::toDto)
                .collect(Collectors.toList());
    }

    private OrderEntry migrateToOrderEntry(long orderId, CartItem c) {
        OrderEntry oe = new OrderEntry();
        oe.setProductId(c.getProductId());
        oe.setOrderId(orderId);
        oe.setProductQuantity(c.getProductQuantity());
        oe.setPrice(c.getForOne());
        return oe;
    }
}
