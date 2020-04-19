package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.Product;
import andrew.korzhov.ecommerce.domain.repository.CartItemRepository;
import andrew.korzhov.ecommerce.domain.repository.ProductRepository;
import andrew.korzhov.ecommerce.service.CartService;
import andrew.korzhov.ecommerce.service.mapper.CartItemMapper;
import andrew.korzhov.ecommerce.web.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CartItemDto save(CartItemDto c) {
        Product one = productRepository.getOne(c.getProductId());
        BigDecimal sum = one.getPrice().multiply(
                BigDecimal.valueOf(c.getProductQuantity()),
                new MathContext(0)
        );
        c.setTotal(sum);
        return cartItemMapper.toDto(cartItemRepository.save(cartItemMapper.toEntity(c)));
    }

    @Override
    @Transactional
    public void deleteByUserId(long id) {
        cartItemRepository.deleteByUserId(id);
    }

    @Override
    @Transactional
    public void deleteByProductId(long id) {
        cartItemRepository.deleteByProductId(id);
    }
}
