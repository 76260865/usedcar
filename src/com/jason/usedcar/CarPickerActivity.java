package com.jason.usedcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.jason.usedcar.fragment.CountyDialogFragment;
import com.jason.usedcar.fragment.LoadingFragment;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.request.ModelRequest;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.request.SeriesRequest;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-08-02.
 */
public class CarPickerActivity extends BaseActivity implements CountyDialogFragment.Callback {

    private ListView brandListView;

    private ListView seriesListView;

    private ListView modelListView;

    private List<Brand> brandList = new ArrayList<Brand>();

    private List<Series> seriesList = new ArrayList<Series>();

    private List<CarModel> modelList = new ArrayList<CarModel>();

    private BaseAdapter brandAdapter;

    private BaseAdapter seriesAdapter;

    private BaseAdapter modelAdapter;

    private Brand brand;

    private Series series;

    private CarModel model;

    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_picker);
        width = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        brandListView = getView(R.id.listBrand);
        seriesListView = getView(R.id.listSeries);
        modelListView = getView(R.id.listYear);
        setTitle("选择品牌");
        brandListView.setAdapter(brandAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return brandList.size();
            }

            @Override
            public Brand getItem(final int position) {
                return brandList.get(position);
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.text_item_1, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText(getItem(position).getName());
                return convertView;
            }
        });
        brandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                if (modelListView.getVisibility() == View.VISIBLE) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(modelListView, "translationX", width * 2 / 5, width);
                    animator.setDuration(500);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(final Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(final Animator animation) {
                            modelListView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(final Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(final Animator animation) {
                        }
                    });
                    animator.start();
                    animator = ObjectAnimator.ofFloat(seriesListView, "translationX", 0, width * 2 / 5);
                    animator.setDuration(500);
                    animator.start();
                    //animator = ObjectAnimator.ofFloat(brandListView, "translationX", -width * 3 / 4, 0);
                    //animator.setDuration(500);
                    //animator.start();
                    setTitle("选择型号");
                    return;
                }
                if (position != brandListView.getSelectedItemPosition()) {
                    brand = brandList.get(position);
                    querySeries(brand.getBrandId());
                    setTitle("选择型号");
                }
            }
        });
        queryBrands();
        seriesListView.setAdapter(seriesAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return seriesList.size();
            }

            @Override
            public Series getItem(final int position) {
                return seriesList.get(position);
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.text_item_1, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText(getItem(position).getSeriesName());
                return convertView;
            }
        });
        seriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                if (position != seriesListView.getSelectedItemPosition()) {
                    series = seriesList.get(position);
                    queryModels(series.getSeriesId());
                    setTitle("选择款式");
                }
            }
        });
        modelListView.setAdapter(modelAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return modelList.size();
            }

            @Override
            public CarModel getItem(final int position) {
                return modelList.get(position);
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_item_1, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText(getItem(position).getModelName());
                return convertView;
            }
        });
        modelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                model = modelList.get(position);
                Intent data = new Intent();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(brand.getBrandId());
                stringBuilder.append(';');
                stringBuilder.append(series.getSeriesId());
                stringBuilder.append(';');
                stringBuilder.append(model.getModelId());
                data.putExtra("data", stringBuilder.toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new RestClient().cancel("getBrands");
    }

    @Override
    public void itemClicked(final int countyId) {
        model = new Select().from(CarModel.class).where("county_id = ?", countyId).executeSingle();
        Intent data = new Intent();
        if (brand.getName().equalsIgnoreCase(series.getSeriesName())) {
            data.putExtra("data", series.getSeriesName().concat(model.getModelName()));
        } else {
            data.putExtra("data", brand.getName().concat(series.getSeriesName().concat(model.getModelName())));
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (modelListView != null && modelListView.getVisibility() == View.VISIBLE) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(modelListView, "translationX", width * 2 / 5, width);
            animator.setDuration(500);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(final Animator animation) {
                }

                @Override
                public void onAnimationEnd(final Animator animation) {
                    modelListView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(final Animator animation) {
                }

                @Override
                public void onAnimationRepeat(final Animator animation) {
                }
            });
            animator.start();
            animator = ObjectAnimator.ofFloat(seriesListView, "translationX", 0, width * 2/ 5);
            animator.setDuration(500);
            animator.start();
            //animator = ObjectAnimator.ofFloat(brandListView, "translationX", -width * 3 / 4, 0);
            //animator.setDuration(500);
            //animator.start();
            setTitle("选择型号");
            return;
        }
        super.onBackPressed();
    }

    private void queryBrands() {
        List<Brand> brandList1 = new Select().from(Brand.class).orderBy("remote_id ASC").execute();
        if (brandList1 != null && !brandList1.isEmpty()) {
            brandList.clear();
            brandList.addAll(brandList1);
            brandListView.setAdapter(brandAdapter);
            return;
        }
        new RestClient().getBrands(new Request(), new Callback<List<Brand>>() {
            @Override
            public void success(List<Brand> brandList, Response response) {
                CarPickerActivity.this.brandList.clear();
                CarPickerActivity.this.brandList.addAll(brandList);
                brandListView.setAdapter(brandAdapter);
                ActiveAndroid.beginTransaction();
                for (Brand brand : brandList) {
                    String brandName = brand.getBrandName();
                    String[] split = brandName.split(" ");
                    brand.setName(split[1]);
                    brand.setIndex(split[0]);
                    brand.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();
            }

            @Override
            public void failure(RetrofitError error) {
                error.getBody();
            }
        });
    }

    private void querySeries(int brandId) {
        List<Series> seriesList1 = new Select().from(Series.class)
                .where("brand_id = ?", brandId).orderBy("remote_id ASC").execute();
        if (seriesList1 != null && !seriesList1.isEmpty()) {
            seriesList.clear();
            seriesList.addAll(seriesList1);
            seriesListView.setAdapter(seriesAdapter);
            if (seriesListView.getVisibility() != View.VISIBLE) {
                seriesListView.setVisibility(View.VISIBLE);
//            ObjectAnimator.ofFloat(seriesListView, "translationX", width, width / 2).setDuration(500).start();
                ObjectAnimator.ofFloat(seriesListView, "translationX", width, width * 2/ 5).setDuration(500).start();
            }
            return;
        }
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        final SeriesRequest request = new SeriesRequest();
        request.setBrandId(brandId);
        new RestClient().getSeries(request, new Callback<List<Series>>() {
            @Override
            public void success(final List<Series> seriesList, final Response response) {
                loadingFragment.dismiss();
                CarPickerActivity.this.seriesList.clear();
                CarPickerActivity.this.seriesList.addAll(seriesList);
                seriesListView.setAdapter(seriesAdapter);
                if (seriesListView.getVisibility() != View.VISIBLE) {
                    seriesListView.setVisibility(View.VISIBLE);
//            ObjectAnimator.ofFloat(seriesListView, "translationX", width, width / 2).setDuration(500).start();
                    ObjectAnimator.ofFloat(seriesListView, "translationX", width, width * 2/ 5).setDuration(500).start();
                }
                ActiveAndroid.beginTransaction();
                for (Series series : seriesList) {
                    series.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();
            }

            @Override
            public void failure(final RetrofitError error) {

            }
        });
    }

    private void queryModels(int seriesId) {
        List<CarModel> modelList1 = new Select().from(CarModel.class)
                .where("series_id = ?", seriesId).orderBy("remote_id ASC").execute();
        if (modelList1 != null && !modelList1.isEmpty()) {
            modelList.clear();
            modelList.addAll(modelList1);
            modelListView.setAdapter(modelAdapter);
            if (modelListView.getVisibility() != View.VISIBLE) {
                modelListView.setVisibility(View.VISIBLE);
//            ObjectAnimator.ofFloat(brandListView, "translationX", 0, -width * 3 / 4).setDuration(500).start();
//            ObjectAnimator.ofFloat(seriesListView, "translationX", width / 2, width / 5).setDuration(500).start();
//            ObjectAnimator.ofFloat(modelListView, "translationX", width, width * 3 / 5).setDuration(500).start();
                ObjectAnimator.ofFloat(seriesListView, "translationX", width * 2/ 5, 0).setDuration(500).start();
                ObjectAnimator.ofFloat(modelListView, "translationX", width, width * 2/ 5).setDuration(500).start();
            }
            return;
        }
        final LoadingFragment loadingFragment = LoadingFragment.newInstance("");
        loadingFragment.show(getSupportFragmentManager());
        final ModelRequest request = new ModelRequest();
        request.setSeriesId(series.getSeriesId());
        new RestClient().getModels(request, new Callback<List<CarModel>>() {
            @Override
            public void success(final List<CarModel> modelList, final Response response) {
                loadingFragment.dismiss();
                CarPickerActivity.this.modelList.clear();
                CarPickerActivity.this.modelList.addAll(modelList);
                modelListView.setAdapter(modelAdapter);
                if (modelListView.getVisibility() != View.VISIBLE) {
                    modelListView.setVisibility(View.VISIBLE);
//            ObjectAnimator.ofFloat(brandListView, "translationX", 0, -width * 3 / 4).setDuration(500).start();
//            ObjectAnimator.ofFloat(seriesListView, "translationX", width / 2, width / 5).setDuration(500).start();
//            ObjectAnimator.ofFloat(modelListView, "translationX", width, width * 3 / 5).setDuration(500).start();
                    ObjectAnimator.ofFloat(seriesListView, "translationX", width * 2/ 5, 0).setDuration(500).start();
                    ObjectAnimator.ofFloat(modelListView, "translationX", width, width * 2/ 5).setDuration(500).start();
                }
                ActiveAndroid.beginTransaction();
                for (CarModel model : modelList) {
                    model.save();
                }
                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();
            }

            @Override
            public void failure(final RetrofitError error) {
                error.getBody();
            }
        });
    }
}