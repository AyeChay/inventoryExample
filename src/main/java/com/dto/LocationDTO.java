package com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {
	private int id;
    private String name;
    private String address;
    private boolean deleted;
}
