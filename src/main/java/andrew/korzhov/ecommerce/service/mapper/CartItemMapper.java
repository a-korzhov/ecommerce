package andrew.korzhov.ecommerce.service.mapper;

import andrew.korzhov.ecommerce.domain.CartItem;
import andrew.korzhov.ecommerce.web.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mappings({
            @Mapping(target = "userId", source = "item.userId"),
            @Mapping(target = "productId", source = "item.productId"),
            @Mapping(target = "total", source = "item.total"),
            @Mapping(target = "productQuantity", source = "item.productQuantity"),
    })
    CartItemDto toDto(CartItem item);

    @Mappings({
            @Mapping(target = "userId", source = "dto.userId"),
            @Mapping(target = "productId", source = "dto.productId"),
            @Mapping(target = "total", source = "dto.total"),
            @Mapping(target = "productQuantity", source = "dto.productQuantity"),
    })
    CartItem toEntity(CartItemDto dto);


}
