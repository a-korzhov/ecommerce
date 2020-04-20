package andrew.korzhov.ecommerce.service.mapper;

import andrew.korzhov.ecommerce.domain.Vendor;
import andrew.korzhov.ecommerce.web.dto.VendorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mappings({
            @Mapping(target = "id", source = "vendor.id"),
            @Mapping(target = "name", source = "vendor.name"),
            @Mapping(target = "country", source = "vendor.country")
    })
    VendorDto toDto(Vendor vendor);


    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "country", source = "dto.country")
    })
    Vendor toEntity(VendorDto dto);
}
