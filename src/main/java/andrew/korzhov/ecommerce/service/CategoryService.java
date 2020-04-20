package andrew.korzhov.ecommerce.service;

import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import andrew.korzhov.ecommerce.web.response.GenericResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto getById(long id);

    List<CategoryDto> getMainCategories();

    List<CategoryDto> getCategoriesSortedByParentId(long pId);

    CategoryDto createCategory(CategoryDto c);

    GenericResponse renameCategory(long id, String name);

    CategoryDto getByName(String name);

}
