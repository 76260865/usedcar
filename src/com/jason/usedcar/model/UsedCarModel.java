package com.jason.usedcar.model;

import java.util.List;

public class UsedCarModel<T> extends BaseModel {

	private boolean loading;

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

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
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
