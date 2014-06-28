package com.jason.usedcar.model.result;

import android.database.DataSetObserver;

/**
 * @author t77yq @2014.06.14
 */
public interface Model {

    /**
     * Register an observer that is called when changes happen to the data of this model.
     *
     * @param observer the object that gets notified when the data set changes.
     */
    void registerDataSetObserver(DataSetObserver observer);

    /**
     * Unregister an observer that has previously been registered with this
     * model via {@link #registerDataSetObserver}.
     *
     * @param observer the object to unregister.
     */
    void unregisterDataSetObserver(DataSetObserver observer);

    /**
     * Return whether this model is empty.
     */
    boolean isEmpty();
}
