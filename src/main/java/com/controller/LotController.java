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
import org.springframework.web.servlet.ModelAndView;

import com.dto.LotDTO;
import com.model.Lot;
import com.repository.CategoryRepository;
import com.repository.LocationRepository;
import com.repository.LotRepository;
import com.repository.ProductOneRepository;

@Controller
@RequestMapping(value="/lot")
public class LotController {
	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LotRepository lotRepo;

    @Autowired
    private ProductOneRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private LocationRepository locationRepo;

    @GetMapping("/register")
    public ModelAndView showRegisterForm() {
        ModelAndView mav = new ModelAndView("lotRegister");
        mav.addObject("lot", new Lot());
        mav.addObject("products", productRepo.getAllProducts());
        mav.addObject("categories", categoryRepo.getAllCategories());
        mav.addObject("locations", locationRepo.getAllLocations());
        return mav;
    }

    @PostMapping("/doregister")
    public String registerLot(@ModelAttribute("lot") @Valid Lot lot, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("products", productRepo.getAllProducts());
            model.addAttribute("categories", categoryRepo.getAllCategories());
            model.addAttribute("locations", locationRepo.getAllLocations());
            return "lotRegister";
        }
        
        LotDTO dto = modelMapper.map(lot, LotDTO.class);
        if ( dto.getQuantity() <= 0
                || dto.getExpiredDate() == null
                || dto.getDate() == null
                || dto.getPrice() <= 0) {
                model.addAttribute("error", "Invalid input. Please check all fields.");
                
                return "lotRegister";
           }
        
        //boolean lotExists = lotRepo.checkExprireDate(dto.getExpiredDate().toString());
        //double totalQuantity = 0;

//        if (lotExists) {
//        	model.addAttribute("error", "Expire date already exists. please choose a different name");
//        	return "lotRegister";
//        }
        
        int result = lotRepo.insertLot(dto);

        if (result > 0) {
            return "redirect:/lot/showlots";
        } else {
            model.addAttribute("error", "Failed to register lot. Please try again.");
            return "lotRegister";
        }
    }

    @GetMapping("/showlots")
    public String showAllLots(Model model) {
        List<LotDTO> lotList = lotRepo.getAllLots();
        model.addAttribute("lotList", lotList);
        return "lotList";
    }

    @GetMapping("/editlot/{id}")
    public String showLotById(@PathVariable("id") int id, Model model) {
        LotDTO dto = lotRepo.getLotById(id);
        if (dto != null) {
            Lot lot = modelMapper.map(dto, Lot.class);
            model.addAttribute("lot", lot);
            model.addAttribute("products", productRepo.getAllProducts());
            model.addAttribute("categories", categoryRepo.getAllCategories());
            model.addAttribute("locations", locationRepo.getAllLocations());
            return "lotEdit";
        } else {
            return "redirect:/lot/showlots";
        }
    }

    @PostMapping("/doupdate")
    public String updateLot(@ModelAttribute("lot") @Valid Lot lot, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("products", productRepo.getAllProducts());
            model.addAttribute("categories", categoryRepo.getAllCategories());
            model.addAttribute("locations", locationRepo.getAllLocations());
            return "lotEdit";
        }

        LotDTO dto = modelMapper.map(lot, LotDTO.class);
        int result = lotRepo.updateLot(dto);

        if (result > 0) {
            return "redirect:/lot/showlots";
        } else {
            model.addAttribute("error", "Failed to update lot. Please try again.");
            return "lotEdit";
        }
    }

    @GetMapping("/deletelot/{id}")
    public String deleteLot(@PathVariable("id") int id) {
        int result = lotRepo.softDeleteLot(id);
        if(result > 0) {
            return "redirect:/lot/showlots";
        } else {
            return "error";
        }
    }
    
  @ModelAttribute("UOMList") 
	public List<String> getUOMList()
	{
		List<String> UOMList = new ArrayList<String>();
		UOMList.add("KG"); 
		UOMList.add("BAG");
		UOMList.add("BOTTLE"); 
		UOMList.add("EA");
		
		
		return UOMList;
	}
}
