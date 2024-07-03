package com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category {
	private int id;
	private String name;
	private String description;
	private boolean deleted;
}
