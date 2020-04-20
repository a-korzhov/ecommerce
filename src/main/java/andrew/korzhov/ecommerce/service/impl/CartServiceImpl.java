package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.Product;
import andrew.korzhov.ecommerce.domain.repository.CartItemRepository;
import andrew.korzhov.ecommerce.domain.repository.ProductRepository;
import andrew.korzhov.ecommerce.service.CartService;
import andrew.korzhov.ecommerce.service.errors.NotFoundException;
import andrew.korzhov.ecommerce.service.mapper.CartItemMapper;
import andrew.korzhov.ecommerce.web.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Product product = productRepository.getOne(c.getProductId());
        int quantity = product.getQuantity() - c.getProductQuantity();
        if (quantity >= 0) {
            product.setQuantity(quantity);
        } else throw new NotFoundException("Product not found in stock or not enough");
        c.setTotal(product.getSum(c.getProductQuantity()));
        return cartItemMapper.toDto(cartItemRepository.save(cartItemMapper.toEntity(c)));
    }

    @Override
    @Transactional
    public void deleteByUserId(final long id) {
        cartItemRepository.deleteByUserId(id);
    }

    @Override
    @Transactional
    public void deleteByProductId(final long id) {
        cartItemRepository.deleteByProductId(id);
    }
}
