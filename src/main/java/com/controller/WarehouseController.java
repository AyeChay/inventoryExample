package com.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dto.LotDTO;
import com.repository.LotRepository;

@Controller
public class WarehouseController {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	LotRepository lotRepo;

	@GetMapping(value = "/warehouse/showInformation")
	  public String showWarehouseInformation(Model model) {
	      List<LotDTO> lotList = lotRepo.getAllLots();
	      model.addAttribute("lotList", lotList);
	      return "showWarehousesInfo"; 
	  }
}
