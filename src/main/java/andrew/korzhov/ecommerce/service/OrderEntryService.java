package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.OrderEntryDto;

import java.util.List;

public interface OrderEntryService {

    List<OrderEntryDto> getResult(long id, long userId);

}
