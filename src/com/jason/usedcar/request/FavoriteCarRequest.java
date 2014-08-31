package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */

public class FavoriteCarRequest extends CarRequest {

    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }
}
