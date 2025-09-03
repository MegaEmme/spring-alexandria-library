package org.lessons.java.spring_alexandria_library.controller;

import org.lessons.java.spring_alexandria_library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    public String index(Model model) {
        model.addAttribute("category", categoryRepository.findAll());
        return "categories/index";
    }
}
