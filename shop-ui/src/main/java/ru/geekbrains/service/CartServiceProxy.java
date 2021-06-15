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
        CartService cartService = (CartService) httpSession.getAttribute("cartService");
        cartService.removeProductQty(productRepr, qty);
    }

    @Override
    public void removeProduct(LineItem lineItem) {
        CartService cartService = (CartService) httpSession.getAttribute("cartService");
        cartService.removeProduct(lineItem);
    }

    @Override
    public List<LineItem> getLineItems() {
        CartService cartService = (CartService) httpSession.getAttribute("cartService");
        return cartService.getLineItems();
    }

    @Override
    public BigDecimal getSubTotal() {
        CartService cartService = (CartService) httpSession.getAttribute("cartService");
        return cartService.getSubTotal();
    }

    @Override
    public void updateCart(LineItem lineItem) {
        CartService cartService = (CartService) httpSession.getAttribute("cartService");
        cartService.updateCart(lineItem);
    }
}
