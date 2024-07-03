package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.model.Location;
import com.repository.ConnectionClass;
import com.repository.LocationRepository;

@Controller
@RequestMapping(value="/location")
public class LocationController {
	@Autowired
    ModelMapper modelMapper;

    @Autowired
    LocationRepository locationRepo;

    
    @GetMapping(value="/locationregister")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("locationRegister", "location", new Location());
    }

    @PostMapping(value="/doregister")
    public String registerLocation(@ModelAttribute("location") @Valid Location locationDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "locationRegister";
        }
        
        
        boolean exists = locationRepo.checkWarehousename(locationDTO.getName());
        if(exists) {
        	model.addAttribute("error", "Warehouse name already exists. please choose a different name");
        	return "locationRegister";
        }
        LocationDTO dto = modelMapper.map(locationDTO, LocationDTO.class);
        if (dto.getName() == null || dto.getName().trim().isEmpty()
            || dto.getAddress()	==null || dto.getAddress() .trim().isEmpty()) {
            model.addAttribute("error", "Location name and address is required.");
            return "locationRegister";
        }
        
        int result = locationRepo.insertLocation(dto);
    	if (result > 0) {
            return "redirect:/location/showlocations";
        } else {
            model.addAttribute("error", "Failed to register location. Please try again.");
            return "locationRegister";
        }	    
    }

    
    @GetMapping(value="/showlocations")
    public String showAllLocations(Model model) {
        List<LocationDTO> locationList = locationRepo.getAllLocations();
        model.addAttribute("locationList", locationList);
        return "locationList";
    }
    
//    @PostMapping("/searchlocation")
//    public String searchCategories(@RequestParam(name = "name", required = false) String locationName,
//                                   @RequestParam(name = "address", required = false) String locationAddress,
//                                   Model model) {
//        List<LocationDTO> locationList;
//        
//        if ((locationName != null && !locationName.isEmpty()) || (locationAddress != null && !locationAddress.isEmpty())) {
//            locationList = locationRepo.searchLocationByNameAndDescription(locationName, locationAddress);
//            		//categoryRepo.searchCategoriesByNameAndDescription(categoryName, categoryDescription);
//        } else {
//        	locationList = locationRepo.getAllLocations();
//        }
//
//        model.addAttribute("locationList", locationList);
//        model.addAttribute("searchTermName", locationName); 
//        model.addAttribute("searchTermAddress", locationAddress);  
//        return "locationList"; 
//    }

    @PostMapping("/search")
    public String searchLocations(@RequestParam(name = "name", required = false) String locationName,
                                  @RequestParam(name = "address", required = false) String locationAddress,
                                  Model model) {
        List<LocationDTO> locationList;

        if ((locationName != null && !locationName.isEmpty()) || (locationAddress != null && !locationAddress.isEmpty())) {
            locationList = locationRepo.searchLocationsByNameAndAddress(locationName, locationAddress);
        } else {
            locationList = locationRepo.getAllLocations();
        }

        model.addAttribute("locationList", locationList);
        model.addAttribute("searchTermName", locationName);
        model.addAttribute("searchTermAddress", locationAddress);
        return "locationList"; // Assuming locationList.jsp displays search results
    }
    
    @GetMapping(value="/editlocation/{id}")
    public String showLocationById(@PathVariable("id") int id, Model model) {
        LocationDTO dto = locationRepo.getLocationById(id);
        if (dto != null) {
            Location location = modelMapper.map(dto, Location.class);
            model.addAttribute("location", location);
            return "locationEdit";
        } else {
            return "redirect:/location/showlocations";
        }
    }

    @PostMapping(value="/doupdate")
    public String updateLocation(@ModelAttribute("location") @Valid Location locationDTO, BindingResult br, Model model) {
        if (br.hasErrors()) {
            return "locationEdit";
        }

        LocationDTO dto = modelMapper.map(locationDTO, LocationDTO.class);
        int result = locationRepo.updateLocation(dto);

        if (result > 0) {
            return "redirect:/location/showlocations";
        } else {
            model.addAttribute("error", "Failed to update location. Please try again.");
            return "locationEdit";
        }
    }

    
    @GetMapping(value="/deletelocation/{id}")
    public String deleteLocation(@PathVariable("id") int id) {
        int result = locationRepo.softDeleteLocation(id);
        return "redirect:/location/showlocations";
    }
}
