package com.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class LotDTO {
	private int id;
	private int p_id;
	private String productCode;
    private String productName;
    private String productdescription;
    private int categoryId;
    private String categoryName;
    private String lotNo;
	private double quantity;
	private String uom;
	private Date date;
    private Date ExpiredDate;
    private double price;
    private int locationId;
    private String locationName;
    private boolean deleted;
}
