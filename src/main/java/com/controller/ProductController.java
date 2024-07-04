package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dto.CategoryDTO;
import com.dto.LocationDTO;
import com.dto.ProductDTO;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.ProductOneRepository;

@Controller
@RequestMapping(value="/product")
public class ProductController {
	@Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductOneRepository productRepo;

    @Autowired
    CategoryRepository categoryRepo;

    @GetMapping(value="/register")
    public ModelAndView showRegisterForm() {
        ModelAndView mav = new ModelAndView("productRegister");
        mav.addObject("product", new Product());
        List<CategoryDTO> categories = categoryRepo.getAllCategories();		
        mav.addObject("categories", categories);
        return mav;
    }

    @PostMapping(value="/doregister")
    public String registerProduct(@ModelAttribute("product") @Valid Product productDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryRepo.getAllCategories());
            return "productRegister";
        }
        ProductDTO dto = modelMapper.map(productDTO, ProductDTO.class);
        if (dto.getProductCode() == null || dto.getProductCode().trim().isEmpty()
            || dto.getProductName()	==null || dto.getProductName().trim().isEmpty()
            || dto.getDescription()==null || dto.getDescription().trim().isEmpty()) {
            model.addAttribute("error", "Invalid!");
            return "productRegister";
        }

        //ProductDTO dto = modelMapper.map(productDTO, ProductDTO.class);
        int result = productRepo.insertProduct(dto);

        if (result > 0) {
            return "redirect:/product/showproducts";
        } else {
            model.addAttribute("error", "Failed to register warehouse. Please try again.");
            return "productRegister";
        }
    }

    @GetMapping(value="/showproducts")
    public String showAllProducts(Model model) {
        List<ProductDTO> productList = productRepo.getAllProducts();
        model.addAttribute("productList", productList);
        return "productList";
    }

    @GetMapping(value="/editproduct/{id}")
    public String showProductById(@PathVariable("id") int id, Model model) {
        ProductDTO dto = productRepo.getProductById(id);
        if (dto != null) {
            Product product = modelMapper.map(dto, Product.class);
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepo.getAllCategories());
            return "productEdit";
        } else {
            return "redirect:/product/showproducts";
        }
    }

    @PostMapping(value="/doupdate")
    public String updateProduct(@ModelAttribute("product") @Valid Product productDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("categories", categoryRepo.getAllCategories());
            return "productEdit";
        }

        ProductDTO dto = modelMapper.map(productDTO, ProductDTO.class);
        int result = productRepo.updateProduct(dto);

        if (result > 0) {
            return "redirect:/product/showproducts";
        } else {
            model.addAttribute("error", "Failed to update warehouse. Please try again.");
            return "productEdit";
        }
    }
    
    @PostMapping("/search")
    public String searchProducts(@RequestParam(name = "productCode", required = false) String productCode,
                                 @RequestParam(name = "productName", required = false) String productName,
                                 Model model) {
        List<ProductDTO> productList;

        if ((productCode != null && !productCode.isEmpty()) || (productName != null && !productName.isEmpty())) {
            productList = productRepo.searchProductsByCodeAndName(productCode, productName);
        } else {
            productList = productRepo.getAllProducts();
        }

        model.addAttribute("productList", productList);
        model.addAttribute("searchTermCode", productCode);
        model.addAttribute("searchTermName", productName);
        return "productList"; 
    }
    
    @GetMapping(value="/deleteproduct/{id}")
    public String deleteproduct(@PathVariable("id") int id) {
        int result = productRepo.softDeleteProduct(id);
        if(result>0) {
        	return "redirect:/product/showproducts";
        }else {
        	return "error";
        }
        
    }

    

}
