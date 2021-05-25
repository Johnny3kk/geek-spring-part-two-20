package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;

@Controller
public class ShopController {

    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ShopController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/shop")
    public String allShopPage(Model model) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "shop";
    }
    @GetMapping("/shop/{id}")
    public String shopByCategory(Model model, @PathVariable("id") Long id) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("products", productService.findByCategoryId(id));
        model.addAttribute("category", categoryService.findById(id));
        return "shop_category";
    }

    @GetMapping("/product/{id}")
    public String showProduct(Model model, @PathVariable("id") Long id) {
        model.addAttribute("activePage", "Product");
        model.addAttribute("product", productService.findById(id));
        return "shop_product";
    }
}
