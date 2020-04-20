package andrew.korzhov.ecommerce.web.api.admin;

import andrew.korzhov.ecommerce.domain.Category;
import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdminCategoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
class AdminCategoryControllerTest {
    private final static String URL = "/api/admin/categories";

    @MockBean
    CategoryService categoryService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    CategoryDto categoryDto;
    Category category;

    @BeforeEach
    public void init() {
        createDtoAndSetToEntity();
    }


    @Test
    void createCategory() throws Exception {
        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);
        mockMvc.perform(post(URL + "/create")
                .content(mapper.writeValueAsString(categoryDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryDto.getId()))
                .andExpect(jsonPath("$.name").value(categoryDto.getName()))
                .andExpect(jsonPath("$.parentId").value(categoryDto.getParentId()));

    }

    public void createDtoAndSetToEntity() {
        categoryDto = new CategoryDto();
        categoryDto.setId(15L);
        categoryDto.setName("name");
        categoryDto.setParentId(2L);

        category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setParentId(categoryDto.getParentId());

    }
}