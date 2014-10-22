package com.jason.usedcar.response;

import com.jason.usedcar.model.data.FacetSeries;
import com.jason.usedcar.model.data.FilterEntity;
import java.util.List;

/**
 * @author t77yq @2014-10-21.
 */
public class SeriesResponse extends Response {

    private List<FilterEntity> series;

    public List<FilterEntity> getSeries() {
        return series;
    }

    public void setSeries(final List<FilterEntity> series) {
        this.series = series;
    }
}
