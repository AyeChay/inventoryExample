package com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Location {
	private int id;
    private String name;
    private String address;
    private boolean deleted;
}
