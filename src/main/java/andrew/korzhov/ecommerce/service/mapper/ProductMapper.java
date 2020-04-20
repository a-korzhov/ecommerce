package andrew.korzhov.ecommerce.service.mapper;

import andrew.korzhov.ecommerce.domain.Product;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import andrew.korzhov.ecommerce.web.dto.FullyProductDto;
import andrew.korzhov.ecommerce.web.dto.ProductDto;
import andrew.korzhov.ecommerce.web.dto.VendorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "id", source = "product.id"),
            @Mapping(target = "name", source = "product.name"),
            @Mapping(target = "price", source = "product.price"),
            @Mapping(target = "categoryId", source = "product.categoryId"),
            @Mapping(target = "vendorId", source = "product.vendorId"),
    })
    ProductDto toDto(Product product);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "price", source = "dto.price"),
            @Mapping(target = "category.id", source = "dto.categoryId"),
            @Mapping(target = "vendor.id", source = "dto.vendorId"),
    })
    Product toEntity(ProductDto dto);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "price", source = "dto.price"),
            @Mapping(target = "categoryId", source = "categoryDto.id"),
            @Mapping(target = "vendorId", source = "vendorDto.id")
    })
    ProductDto toDtoFromOtherDto(FullyProductDto dto, VendorDto vendorDto, CategoryDto categoryDto);
}
