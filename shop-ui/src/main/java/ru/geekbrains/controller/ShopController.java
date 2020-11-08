package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.geekbrains.repo.ProductRepository;

@Controller
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private final ProductRepository productRepository;

    @Autowired
    public ShopController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/shop")
    public String allShopPage(Model model) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("products", productRepository.findAll());
        return "shop";
    }
}
