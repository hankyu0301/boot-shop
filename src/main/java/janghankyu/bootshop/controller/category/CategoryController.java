package janghankyu.bootshop.controller.category;

import janghankyu.bootshop.entity.category.CategoryCreateRequest;
import janghankyu.bootshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categoryCreateRequest", new CategoryCreateRequest());
        return "/category/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute CategoryCreateRequest req, Errors errors) {

        if(errors.hasErrors()) {
            return "/category/create";
        }

        categoryService.create(req);

        return "redirect:/";
    }
}
