package com.jason.usedcar.db;

import android.provider.BaseColumns;

/**
 * The CarTablesInfo contains tables information related to UsedCar application.
 */
public class CarTablesInfo {
    public static final class CarBrand implements BaseColumns {
        public static final String TABLE_NAME = "car_brand";

        public static final String BRAND_ID = "brand_id";

        public static final String BRAND_NAME = "brand_name";

        public static final String[] PROJECTION = new String[] { BaseColumns._ID, BRAND_ID,
                BRAND_NAME };

        /**
         * Projection column indices
         */
        public static final int INDEX_ID = 0;
        public static final int INDEX_BRAND_ID = 1;
        public static final int INDEX_BRAND_NAME = 2;
    }

    public static final class CarSeries implements BaseColumns {
        public static final String TABLE_NAME = "car_series";

        public static final String SERIES_ID = "series_id";

        public static final String SERIES_NAME = "series_name";

        // Foreign key of CarBrand
        public static final String BRAND_ID = "brand_id";

        public static final String[] PROJECTION = new String[] { BaseColumns._ID, SERIES_ID,
                SERIES_NAME, BRAND_ID };

        /**
         * Projection column indices
         */
        public static final int INDEX_ID = 0;
        public static final int INDEX_SERIES_ID = 1;
        public static final int INDEX_SERIES_NAME = 2;
        public static final int INDEX_BRAND_ID = 3;
    }

    public static final class CarModels implements BaseColumns {
        public static final String TABLE_NAME = "car_models";

        public static final String MODEL_ID = "model_id";

        public static final String MODEL_NAME = "model_name";

        // Foreign key of CarSeries
        public static final String SERIES_ID = "series_id";

        public static final String[] PROJECTION = new String[] { BaseColumns._ID, MODEL_ID,
                MODEL_NAME, SERIES_ID };

        /**
         * Projection column indices
         */
        public static final int INDEX_ID = 0;
        public static final int INDEX_MODEL_ID = 1;
        public static final int INDEX_MODEL_NAME = 2;
        public static final int INDEX_SERIES_ID = 3;
    }

    public static final class CarProvince implements BaseColumns {
        public static final String TABLE_NAME = "car_province";

        public static final String PROVINCE_ID = "province_id";

        public static final String PROVINCE_NAME = "province_name";
        public static final String[] PROJECTION = new String[] { BaseColumns._ID, PROVINCE_ID,
                PROVINCE_NAME };

        /**
         * Projection column indices
         */
        public static final int INDEX_ID = 0;
        public static final int INDEX_PROVINCE_ID = 1;
        public static final int INDEX_PROVINCE_NAME = 2;
    }

    public static final class City implements BaseColumns {
        public static final String TABLE_NAME = "car_city";

        public static final String CITY_ID = "city_id";

        public static final String CITY_NAME = "city_name";

        // Foreign key of Province
        public static final String PROVINCE_ID = "province_id";

        public static final String[] PROJECTION = new String[] { BaseColumns._ID, CITY_ID,
                CITY_NAME, PROVINCE_ID };

        /**
         * Projection column indices
         */
        public static final int INDEX_ID = 0;
        public static final int INDEX_CITY_ID = 1;
        public static final int INDEX_CITY_NAME = 2;
        public static final int INDEX_PROVINCE_ID = 2;
    }

    public static final class Country implements BaseColumns {
        public static final String TABLE_NAME = "car_country";

        public static final String COUNTRY_ID = "country_id";

        public static final String COUNTRY_NAME = "country_name";

        // Foreign key of City
        public static final String CITY_ID = "city_id";

        public static final String[] PROJECTION = new String[] { BaseColumns._ID, COUNTRY_ID,
                COUNTRY_NAME, CITY_ID };

        /**
         * Projection column indices
         */
        public static final int INDEX_ID = 0;
        public static final int INDEX_COUNTRY_ID = 1;
        public static final int INDEX_COUNTRY_NAME = 2;
        public static final int INDEX_CITY_ID = 3;
    }
}
