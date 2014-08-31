package com.jason.usedcar.model;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

/**
 * @author t77yq @2014.06.14
 */
public class BaseModel implements Model {

    private final DataSetObservable dataSetObservable = new DataSetObservable();

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        dataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        dataSetObservable.unregisterObserver(observer);
    }

    public void unregisterAll() {
        dataSetObservable.unregisterAll();
    }

    /**
     * Notifies the attached observers that the underlying data has been changed.
     */
    public void notifyDataSetChanged() {
        dataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid or available.
     */
    public void notifyDataSetInvalidated() {
        dataSetObservable.notifyInvalidated();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
