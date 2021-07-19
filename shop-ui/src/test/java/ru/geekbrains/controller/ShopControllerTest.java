package ru.geekbrains.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.model.*;
import ru.geekbrains.repo.BrandRepository;
import ru.geekbrains.repo.CategoryRepository;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repr.ProductRepr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ShopControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @MockBean
    private EurekaClient eurekaClient;

    @BeforeEach
    public void init() {
        InstanceInfo instanceInfo = mock(InstanceInfo.class);
        when(instanceInfo.getHomePageUrl()).thenReturn("mock-homepage-url");
        when(eurekaClient.getNextServerFromEureka(anyString(), anyBoolean())).thenReturn(instanceInfo);
    }

    @AfterEach
    public void dropTestData() {
        categoryRepository.deleteAll();
        brandRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testShowProduct() throws Exception {
        Category category = categoryRepository.save(new Category("Best"));
        Brand brand = brandRepository.save(new Brand("Zuhr"));
        Product product = productRepository.save(new Product("Uss Get", new BigDecimal(12345), category, brand));

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

    @Test
    public void testAllShopPage() throws Exception {
        Category category = categoryRepository.save(new Category("Best"));
        categoryRepository.save(new Category("C1"));
        categoryRepository.save(new Category("C2"));
        categoryRepository.save(new Category("C3"));
        categoryRepository.save(new Category("C4"));
        Brand brand = brandRepository.save(new Brand("Zuhr"));
        Product product1 = productRepository.save(new Product("Uss Get", new BigDecimal(12345), category, brand));
        Product product2 = productRepository.save(new Product("Iss Net", new BigDecimal(456123), category, brand));

        List<Picture> pictureList = new ArrayList<>();
        pictureList.add(new Picture("pop", "jpg", new PictureData("4a2aea8e-c49e-4141-a995-4d466c0a0497")));
        pictureList.add(new Picture("muse", "jpg", new PictureData("fd842b0f-b5dc-4f8f-8df1-ebde46f1077b")));
        product1.setPictures(pictureList);
        product2.setPictures(pictureList);

        productRepository.save(product1);
        productRepository.save(product2);

        mvc.perform(get("/shop"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("shop"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "Products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("categories"));

        List<Product> productList = productRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();

        assertTrue(productList.size() > 1);
        assertEquals("Uss Get", productList.get(0).getName());
        assertEquals("Best", productList.get(0).getCategory().getName());
        assertEquals("Zuhr", productList.get(0).getBrand().getName());
        assertEquals(new BigDecimal(12345).setScale(2, RoundingMode.UNNECESSARY), productList.get(0).getPrice().setScale(2, RoundingMode.UNNECESSARY));

        assertEquals("Iss Net", productList.get(1).getName());
        assertEquals("Best", productList.get(1).getCategory().getName());
        assertEquals("Zuhr", productList.get(1).getBrand().getName());
        assertEquals(new BigDecimal(456123).setScale(2, RoundingMode.UNNECESSARY), productList.get(1).getPrice().setScale(2, RoundingMode.UNNECESSARY));

        assertTrue(categoryList.size() > 3);
        assertEquals("Best", categoryList.get(0).getName());
        assertEquals("C2", categoryList.get(2).getName());
    }

    @Test
    public void testShopByCategory() throws Exception {
        Category category = categoryRepository.save(new Category("Best"));
        Brand brand = brandRepository.save(new Brand("Zuhr"));
        Product product1 = productRepository.save(new Product("Uss Get", new BigDecimal(12345), category, brand));
        Product product2 = productRepository.save(new Product("Iss Net", new BigDecimal(456123), category, brand));

        List<Picture> pictureList = new ArrayList<>();
        pictureList.add(new Picture("pop", "jpg", new PictureData("4a2aea8e-c49e-4141-a995-4d466c0a0497")));
        pictureList.add(new Picture("muse", "jpg", new PictureData("fd842b0f-b5dc-4f8f-8df1-ebde46f1077b")));
        product1.setPictures(pictureList);
        product2.setPictures(pictureList);

        productRepository.save(product1);
        productRepository.save(product2);

        mvc.perform(get("/shop/" + category.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("shop_category"))
                .andExpect(model().attributeExists("activePage"))
                .andExpect(model().attribute("activePage", "Products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("category"));

        List<Product> productList = productRepository.findAll();

        assertTrue(productList.size() > 1);
        assertEquals("Uss Get", productList.get(0).getName());
        assertEquals("Best", productList.get(0).getCategory().getName());
        assertEquals("Zuhr", productList.get(0).getBrand().getName());
        assertEquals(new BigDecimal(12345).setScale(2, RoundingMode.UNNECESSARY), productList.get(0).getPrice().setScale(2, RoundingMode.UNNECESSARY));

        assertEquals("Iss Net", productList.get(1).getName());
        assertEquals("Best", productList.get(1).getCategory().getName());
        assertEquals("Zuhr", productList.get(1).getBrand().getName());
        assertEquals(new BigDecimal(456123).setScale(2, RoundingMode.UNNECESSARY), productList.get(1).getPrice().setScale(2, RoundingMode.UNNECESSARY));
    }

}
