package andrew.korzhov.ecommerce.service.mapper;

import andrew.korzhov.ecommerce.domain.Category;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(target = "id", source = "category.id"),
            @Mapping(target = "name", source = "category.name"),
            @Mapping(target = "parentId", source = "category.parentId"),
    })
    CategoryDto toDto(Category category);


    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "parentId", source = "dto.parentId"),
    })
    Category toEntity(CategoryDto dto);
}
