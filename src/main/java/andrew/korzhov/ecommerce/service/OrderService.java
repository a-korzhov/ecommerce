package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.OrderDto;

public interface OrderService {

    OrderDto save(long userId);

    OrderDto getById(long id);

    void deleteById(long id);
}
