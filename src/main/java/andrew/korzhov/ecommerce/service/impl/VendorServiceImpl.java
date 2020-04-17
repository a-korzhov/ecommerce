package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.repository.VendorRepository;
import andrew.korzhov.ecommerce.service.VendorService;
import andrew.korzhov.ecommerce.service.mapper.VendorMapper;
import andrew.korzhov.ecommerce.web.dto.VendorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public VendorDto getByVendorName(String name) {
        return vendorMapper.toDto(vendorRepository.findByName(name));
    }

    /*
        If vendor exists - skip creation.
     */
    @Override
    @Transactional
    public VendorDto createVendor(VendorDto v) {
        if (vendorRepository.existsByName(v.getName())) {
            return getByVendorName(v.getName());
        } else
            return vendorMapper.toDto(vendorRepository.save(vendorMapper.toEntity(v)));
    }

}
