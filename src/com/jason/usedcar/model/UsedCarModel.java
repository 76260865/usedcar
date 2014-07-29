package com.jason.usedcar.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class UsedCarModel<T> extends BaseModel {
	@Getter
	@Setter
	private boolean loading;

	@Getter
	@Setter
	private List<T> data;

	public UsedCarModel() {
	}

	public UsedCarModel(List<T> data) {
		this.data = data;
	}

	public void add(List<T> newData) {
		if (data == null) {
			this.data = newData;
		} else {
			this.data.addAll(newData);
		}
	}

	@Override
	public boolean isEmpty() {
		return data == null || data.isEmpty();
	}

	public int size() {
		return data == null ? 0 : data.size();
	}

	public T get(int position) {
		return (position < 0 || position >= size()) ? null : data.get(position);
	}
}
