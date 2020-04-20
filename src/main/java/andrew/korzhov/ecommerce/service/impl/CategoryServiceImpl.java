package andrew.korzhov.ecommerce.service.impl;

import andrew.korzhov.ecommerce.domain.repository.CategoryRepository;
import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.service.errors.NotFoundException;
import andrew.korzhov.ecommerce.service.mapper.CategoryMapper;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getById(final long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category %s not found", id)
        ));
    }

    @Override
    public List<CategoryDto> getMainCategories() {
        return categoryRepository.findAllByWhereIdIsNull().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCategoriesSortedByParentId(final long parentId) {
        return categoryRepository.findAllByParentId(parentId).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getByName(String name) {
        return categoryMapper.toDto(categoryRepository.findByName(name));
    }

    /*
        If category already exists - skip creation.
     */
    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto c) {
        if (categoryRepository.existsByName(c.getName())) {
            return getByName(c.getName());
        } else
            return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(c)));
    }

    @Override
    @Transactional
    public GenericResponse renameCategory(final long id, String name) {
        categoryRepository.updateName(id, name);
        return new GenericResponse("Category %s successfully renamed", name);
    }

}
