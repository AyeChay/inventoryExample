package com.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
	private int id;
	private String productCode;
    private String productName;
    private String description;
    private int categoryId;
    private String categoryName;
    private boolean deleted;  
   
}
