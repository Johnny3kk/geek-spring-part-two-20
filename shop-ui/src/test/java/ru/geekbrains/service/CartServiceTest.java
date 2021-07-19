package ru.geekbrains.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.model.LineItem;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.CartServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testEmptyCart() {
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddOneProduct() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Big stuff");
        expectedProduct.setPrice(new BigDecimal(999));

        cartService.addProductQty(expectedProduct, 0);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals(1, lineItem.getQty());
        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductRepr());
        assertEquals(expectedProduct.getName(), lineItem.getProductRepr().getName());
        assertEquals(expectedProduct.getPrice(), lineItem.getProductRepr().getPrice());
    }

    @Test
    public void testUpdateCart() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Big stuff");
        expectedProduct.setPrice(new BigDecimal(999));
        cartService.addProductQty(expectedProduct, 0);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals(1, lineItem.getQty());
        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductRepr());

        ProductRepr nextProduct = new ProductRepr();
        nextProduct.setId(2L);
        nextProduct.setName("Next stuff");
        nextProduct.setPrice(new BigDecimal(555));
        cartService.addProductQty(nextProduct, 0);

        lineItems = cartService.getLineItems();
        assertEquals(2, lineItems.size());

        LineItem secondItem = lineItems.get(1);
        assertEquals(1, secondItem.getQty());

        cartService.addProductQty(expectedProduct, 1);
        cartService.addProductQty(nextProduct, 1);
        lineItems = cartService.getLineItems();
        LineItem updLineItem = lineItems.get(0);
        LineItem updSecondItem = lineItems.get(1);

        assertEquals(2, lineItem.getQty());
        assertEquals(2, secondItem.getQty());
    }

    @Test
    public void testRemoveProductFromCart() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("Big stuff");
        expectedProduct.setPrice(new BigDecimal(999));
        cartService.addProductQty(expectedProduct, 0);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        cartService.removeProduct(lineItem);
        lineItems = cartService.getLineItems();
        assertEquals(0, lineItems.size());
    }

}
