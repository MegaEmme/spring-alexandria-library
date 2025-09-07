package org.lessons.java.spring_alexandria_library.controller;

import org.lessons.java.spring_alexandria_library.model.Book;
import org.lessons.java.spring_alexandria_library.model.Category;
import org.lessons.java.spring_alexandria_library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // INDEX
    @GetMapping
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/index";
    }

    // SHOW
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer Id, Model model) {
        model.addAttribute("category", categoryRepository.findById(Id).get());
        return "categories/show";
    }

    // CREATE
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "categories/create-or-edit";
        }

        categoryRepository.save(formCategory);
        return "redirect:/categories";
    }

    // UPDATE
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("edit", true);
        return "categories/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "categories/edit";
        }
        categoryRepository.save(formCategory);
        return "redirect:/categories";
    }

    // DELETE
    @PostMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        // cancellare una categoria => book.getCategories().remove(category)
        Category categoryToDelete = categoryRepository.findById(id).get();
        for (Book linkedBook : categoryToDelete.getBooks()) {
            linkedBook.getCategories().remove(categoryToDelete);
        }
        categoryRepository.delete(categoryToDelete);
        return "redirect:/categories";
    }
}
