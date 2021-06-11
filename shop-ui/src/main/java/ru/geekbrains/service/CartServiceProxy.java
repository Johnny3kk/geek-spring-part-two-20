package ru.geekbrains.service;

import ru.geekbrains.model.LineItem;
import ru.geekbrains.repr.ProductRepr;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class CartServiceProxy implements CartService {

    HttpSession httpSession;

    @Override
    public void addProductQty(ProductRepr productRepr, int qty) {
        CartService cartService = (CartService) httpSession.getAttribute("cartService");
        cartService.addProductQty(productRepr, qty);
    }

    @Override
    public void removeProductQty(ProductRepr productRepr, int qty) {

    }

    @Override
    public void removeProduct(LineItem lineItem) {

    }

    @Override
    public List<LineItem> getLineItems() {
        return null;
    }

    @Override
    public BigDecimal getSubTotal() {
        return null;
    }

    @Override
    public void updateCart(LineItem lineItem) {

    }
}
