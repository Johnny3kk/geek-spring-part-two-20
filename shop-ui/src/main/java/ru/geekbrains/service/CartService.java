package ru.geekbrains.service;

import ru.geekbrains.model.LineItem;
import ru.geekbrains.repr.ProductRepr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface CartService extends Serializable {

    void addProductQty(ProductRepr productRepr, int qty);

    void removeProductQty(ProductRepr productRepr, int qty);

    void removeProduct(LineItem lineItem);

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

    void updateCart(LineItem lineItem);
}
