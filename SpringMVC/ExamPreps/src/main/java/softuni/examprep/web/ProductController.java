package softuni.examprep.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.examprep.model.dto.ProductAddBindingModel;
import softuni.examprep.service.ProductService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String add(Model model, HttpSession httpSession){
        if (httpSession.getAttribute("user") == null){
            return "redirect:/users/login";
        }

        if (!model.containsAttribute("productAddBindingModel")){
            model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
        }
        return "product-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("productAddBindingModel",productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel",bindingResult);
            return "redirect:add";
        }

        productService.addProduct(productAddBindingModel);
        return "redirect:/";
    }

    @GetMapping("/buy/{id}")
    public String buyById(@PathVariable String id){
        productService.buyProduct(id);

        return "redirect:/";
    }

    @GetMapping("/buy/all")
    public String buyAll(){
        productService.buyAllProducts();

        return "redirect:/";
    }
}
