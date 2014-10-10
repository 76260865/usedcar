package com.jason.usedcar.model.data;

import java.io.Serializable;

public class FilterEntity implements Serializable {
	private String name;
	private int count;
	private String facetSelection;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getFacetSelection() {
		return facetSelection;
	}

	public void setFacetSelection(String facetSelection) {
		this.facetSelection = facetSelection;
	}

}
