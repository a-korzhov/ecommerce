package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.VendorDto;

public interface VendorService {

    VendorDto createVendor(VendorDto v);

    VendorDto getByVendorName(String name);

}
