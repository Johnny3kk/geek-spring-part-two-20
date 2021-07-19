package ru.geekbrains.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @MockBean
    private EurekaClient eurekaClient;

    @BeforeEach
    public void init() {
        InstanceInfo instanceInfo = mock(InstanceInfo.class);
        when(instanceInfo.getHomePageUrl()).thenReturn("mock-homepage-url");
        when(eurekaClient.getNextServerFromEureka(anyString(), anyBoolean())).thenReturn(instanceInfo);
    }

    @Test
    public void testProductDetails() throws Exception {
        Category category = categoryRepository.save(new Category("Super New"));
        Brand brand = brandRepository.save(new Brand("Best Buy"));
        Product product = productRepository.save(new Product("PopIt", new BigDecimal(15), category, brand));

        mvc.perform(get("/product/" + product.getId()))
            .andExpect(status().is2xxSuccessful())
            .andExpect(view().name("shop_product"))
            .andExpect(model().attributeExists("product"))
            .andExpect(model().attribute("product", new BaseMatcher<Product>() {

                @Override
                public void describeTo(Description description) {

                }

                @Override
                public boolean matches(Object o) {
                    if (o instanceof ProductRepr) {
                        ProductRepr productRepr = (ProductRepr) o;
                        return productRepr.getId().equals(product.getId());
                    }
                    return false;
                }
            }));
    }
}
