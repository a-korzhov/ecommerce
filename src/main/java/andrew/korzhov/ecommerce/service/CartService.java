package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.CartItemDto;

public interface CartService {

    CartItemDto save(CartItemDto c);

    void deleteByUserId(long id);

    void deleteByProductId(long id);

}
