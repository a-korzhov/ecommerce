package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.domain.Category;
import andrew.korzhov.ecommerce.service.CategoryService;
import andrew.korzhov.ecommerce.web.dto.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CategoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(secure = false)
class CategoryControllerTest {

    private final static String URL = "/api/categories";

    @MockBean
    CategoryService categoryService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    Category category;
    CategoryDto categoryDto;

    @BeforeEach
    public void init() {
        createDtoAndSetToEntity();
    }

    @Test
    void getOneById() throws Exception {
        given(categoryService.getById(categoryDto.getId())).willReturn(categoryDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", 15L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(15L));
    }

    @Test
    void getCategories() {
    }

    @Test
    void getCategoriesByParent() {
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