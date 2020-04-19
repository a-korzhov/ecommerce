package andrew.korzhov.ecommerce.service.mapper;

import andrew.korzhov.ecommerce.domain.Order;
import andrew.korzhov.ecommerce.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "id", source = "order.id"),
            @Mapping(target = "userId", source = "order.userId"),
            @Mapping(target = "createdAt", source = "order.createdAt"),
            @Mapping(target = "totalAmount", source = "order.totalAmount"),
    })
    OrderDto toDto(Order order);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "userId", source = "dto.userId"),
            @Mapping(target = "createdAt", source = "dto.createdAt"),
            @Mapping(target = "totalAmount", source = "dto.totalAmount"),
    })
    Order toEntity(OrderDto dto);

}
