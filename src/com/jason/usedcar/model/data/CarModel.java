package com.jason.usedcar.model.data;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "model")
public class CarModel extends com.activeandroid.Model{

    @Column(name = "remote_id")
	private int modelId;

    @Column(name = "name")
	private String modelName;

    @Column(name = "series_id")
    private int seriesId;

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(final int seriesId) {
        this.seriesId = seriesId;
    }
}
