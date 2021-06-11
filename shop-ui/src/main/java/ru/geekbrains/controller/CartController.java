package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.LineItem;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductService;

@RequestMapping("/cart")
@Controller
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("lineItems", cartService.getLineItems());
        model.addAttribute("subTotal", cartService.getSubTotal());
        return "cart";
    }

    @PostMapping("/add")
    public String updateCart(LineItem lineItem) {
        lineItem.setProductRepr(productService.findById(lineItem.getProductId()));
        cartService.updateCart(lineItem);
        return "redirect:/shop";
    }

    @DeleteMapping
    public String deleteLineItem(LineItem lineItem) {
        cartService.removeProduct(lineItem);
        return "redirect:/cart";
    }

    @GetMapping("/decrease/{id}/{qty}")
    public String decreaseQty(@PathVariable("id") Long id, @PathVariable("qty") int qty) {
        cartService.removeProductQty(productService.findById(id), qty);
        return "redirect:/cart";
    }

    @GetMapping("/increase/{id}/{qty}")
    public String increaseQty(@PathVariable("id") Long id, @PathVariable("qty") int qty) {
        cartService.addProductQty(productService.findById(id), qty);
        return "redirect:/cart";
    }
}
