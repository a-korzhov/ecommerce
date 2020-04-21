package andrew.korzhov.ecommerce.web.api.user;

import andrew.korzhov.ecommerce.EcommerceApplicationTests;
import andrew.korzhov.ecommerce.domain.Activation;
import andrew.korzhov.ecommerce.security.ActivationService;
import andrew.korzhov.ecommerce.security.UserService;
import andrew.korzhov.ecommerce.security.model.Role;
import andrew.korzhov.ecommerce.security.model.Status;
import andrew.korzhov.ecommerce.security.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@Import(EcommerceApplicationTests.ProductControllerTestConfiguration.class)
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserControllerTest extends EcommerceApplicationTests {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    ActivationService activationService;

//    String jwtTokenWithUserRole;

    User user;
    Activation activation;
    String password;

    @BeforeEach
    void setUp() {
        fillUser();
        fillActivation();
        password = passwordEncoder.encode("1712");
        given(activationService.saveActivationCode(user.getId())).willReturn(activation);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void createUser() throws Exception {
        given(userService.createUser(user)).willReturn(false);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON));
        assertThat(passwordEncoder.matches(password, passwordEncoder.encode(password)));
//                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.message", is("User username created successfully")));
    }


    private void fillUser() {
        user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setFirstName("Andrew");
        user.setLastName("Korzhov");
        user.setEmail("123@gmail.com");
        user.setPassword(password);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setStatus(Status.NOT_ACTIVE);
        user.setCreatedAt(LocalDate.now());
    }

    private void fillActivation() {
        activation = new Activation();
        activation.setUserId(user.getId());
        activation.setActivationCode(UUID.randomUUID().toString());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}