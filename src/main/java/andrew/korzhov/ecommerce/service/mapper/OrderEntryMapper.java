package andrew.korzhov.ecommerce.service.mapper;

import andrew.korzhov.ecommerce.domain.OrderEntry;
import andrew.korzhov.ecommerce.web.dto.OrderEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderEntryMapper {

    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "productId", source = "entity.productId"),
            @Mapping(target = "orderId", source = "entity.orderId"),
            @Mapping(target = "productQuantity", source = "entity.productQuantity"),
            @Mapping(target = "price", source = "entity.price"),
            @Mapping(target = "total", source = "entity.total"),
    })
    OrderEntryDto toDto(OrderEntry entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "productId", source = "dto.productId"),
            @Mapping(target = "orderId", source = "dto.orderId"),
            @Mapping(target = "productQuantity", source = "dto.productQuantity"),
            @Mapping(target = "price", source = "dto.price"),
            @Mapping(target = "total", source = "dto.total"),
    })
    OrderEntry toEntity(OrderEntryDto dto);
}
