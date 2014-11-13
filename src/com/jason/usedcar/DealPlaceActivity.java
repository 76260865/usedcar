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
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.request.CityRequest;
import com.jason.usedcar.request.CountyRequest;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.util.CollectionUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-08-02.
 */
public class DealPlaceActivity extends BaseActivity {

    private ListView provinceListView;

    private ListView cityListView;

    private ListView countyListView;

    private List<Province> provinceList = new ArrayList<Province>();

    private List<City> cityList = new ArrayList<City>();

    private List<County> countyList = new ArrayList<County>();

    private BaseAdapter provinceAdapter;

    private BaseAdapter cityAdapter;

    private BaseAdapter countyAdapter;

    private Province province;

    private City city;

    private County county;

    private int width;

    private int[] openProvince;

    private int[] openCity;

    private boolean isSellingCar;

    private boolean isResellerRegister;

    private int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_place);
        isSellingCar = getIntent().getBooleanExtra("isSellingCar", false);
        isResellerRegister = getIntent().getBooleanExtra("isResellerRegister", false);
        level = getIntent().getIntExtra("level", 3);
        if (isSellingCar || isResellerRegister) {
            openProvince = getResources().getIntArray(R.array.open_province);
            openCity = getResources().getIntArray(R.array.open_city);
        }
        width = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        provinceListView = getView(R.id.listProvince);
        cityListView = getView(R.id.listCity);
        countyListView = getView(R.id.listCounty);
        provinceListView.setAdapter(provinceAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return provinceList.size();
            }

            @Override
            public Province getItem(final int position) {
                return provinceList.get(position);
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
                textView.setText(getItem(position).getProvinceName());
                return convertView;
            }
        });
        provinceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                if (countyListView.getVisibility() == View.VISIBLE) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(countyListView, "translationX", width * 2 / 5, width);
                    animator.setDuration(500);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(final Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(final Animator animation) {
                            countyListView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(final Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(final Animator animation) {
                        }
                    });
                    animator.start();
                    animator = ObjectAnimator.ofFloat(cityListView, "translationX", 0, width * 2 / 5);
                    animator.setDuration(500);
                    animator.start();
//                    animator = ObjectAnimator.ofFloat(provinceListView, "translationX", -width * 3 / 4, 0);
//                    animator.setDuration(500);
//                    animator.start();
                    return;
                }
                if (level == 1) {
                    Intent data = new Intent();
                    StringBuilder builder = new StringBuilder();
                    builder.append(provinceList.get(position).getProvinceId()).append(';');
                    data.putExtra("data", builder.toString());
                    setResult(RESULT_OK, data);
                    finish();
                    return;
                }
                if (position != provinceListView.getSelectedItemPosition()) {
                    province = provinceList.get(position);
                    queryCities(province.getProvinceId());
                }
            }
        });
        queryProvinces();
        cityListView.setAdapter(cityAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return cityList.size();
            }

            @Override
            public City getItem(final int position) {
                return cityList.get(position);
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
                textView.setText(getItem(position).getCityName());
                return convertView;
            }
        });
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                if (level == 2) {
                    Intent data = new Intent();
                    StringBuilder builder = new StringBuilder();
                    builder.append(province.getProvinceId()).append(";");
                    builder.append(cityList.get(position).getCityId());
                    data.putExtra("data", builder.toString());
                    setResult(RESULT_OK, data);
                    finish();
                    return;
                }
                if (position != cityListView.getSelectedItemPosition()) {
                    city = cityList.get(position);
                    queryCounties(city.getCityId());
                }
            }
        });
        countyListView.setAdapter(countyAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return countyList.size();
            }

            @Override
            public County getItem(final int position) {
                return countyList.get(position);
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
                textView.setText(getItem(position).getCountyName());
                return convertView;
            }
        });
        countyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                county = countyList.get(position);
                Intent data = new Intent();
                StringBuilder builder = new StringBuilder();
                builder.append(province.getProvinceId()).append(';');
                builder.append(city.getCityId()).append(';');
                builder.append(county.getCountyId());
                data.putExtra("data", builder.toString());
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
    public void onBackPressed() {
        if (countyListView != null && countyListView.getVisibility() == View.VISIBLE) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(countyListView, "translationX", width * 2 / 5, width);
            animator.setDuration(500);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(final Animator animation) {
                }

                @Override
                public void onAnimationEnd(final Animator animation) {
                    countyListView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(final Animator animation) {
                }

                @Override
                public void onAnimationRepeat(final Animator animation) {
                }
            });
            animator.start();
            animator = ObjectAnimator.ofFloat(cityListView, "translationX", 0, width * 2/ 5);
            animator.setDuration(500);
            animator.start();
//            animator = ObjectAnimator.ofFloat(provinceListView, "translationX", -width * 3 / 4, 0);
//            animator.setDuration(500);
//            animator.start();
            return;
        }
        super.onBackPressed();
    }

    private void queryProvinces() {
        provinceList.clear();
        provinceListView.setAdapter(provinceAdapter);
        List<Province> provinceList2 = new Select().from(Province.class).execute();
        if (CollectionUtils.isNotEmpty(provinceList2)) {
            for (int i = provinceList2.size() - 1; i >= 0; i--) {
                if (openProvince == null || openProvince.length == 0) {
                    break;
                }
                for (int provinceId : openProvince) {
                    if (provinceList2.get(i).getProvinceId() != provinceId) {
                        provinceList2.remove(i);
                        break;
                    }
                }
            }
            provinceList.clear();
            provinceList.addAll(provinceList2);
            Collections.sort(provinceList, new Comparator<Province>() {
                @Override
                public int compare(final Province lhs, final Province rhs) {
                    return lhs.getProvinceId() - rhs.getProvinceId();
                }
            });
            provinceListView.setAdapter(provinceAdapter);
            return;
        }
        Request request = new Request();
        new RestClient().getProvinces(request, new Callback<List<Province>>() {
            @Override
            public void success(List<Province> provinceList1, Response response) {
                Collections.sort(provinceList1, new Comparator<Province>() {
                    @Override
                    public int compare(final Province lhs, final Province rhs) {
                        return lhs.getProvinceId() - rhs.getProvinceId();
                    }
                });
                ActiveAndroid.beginTransaction();
                for (int i = provinceList1.size() - 1; i >= 0; i--) {
                    Province province = provinceList1.get(i);
                    province.save();
                    if (openProvince == null || openProvince.length == 0) {
                        continue;
                    }
                    for (int provinceId : openProvince) {
                        if (province.getProvinceId() != provinceId) {
                            provinceList1.remove(i);
                            break;
                        }
                    }
                }

                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();
                provinceList.clear();
                provinceList.addAll(provinceList1);
                provinceListView.setAdapter(provinceAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                error.getBody();
            }
        });
    }

    private void queryCities(int provinceId) {
        cityList.clear();
        cityListView.setAdapter(cityAdapter);
        if (cityListView.getVisibility() != View.VISIBLE) {
            cityListView.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(cityListView, "translationX", width, width * 2 / 5).setDuration(500).start();
        }
        if (cityListView.getVisibility() != View.VISIBLE) {
            cityListView.setVisibility(View.VISIBLE);
            ObjectAnimator.ofFloat(cityListView, "translationX", width, width * 2 / 5).setDuration(500).start();
        }
        final List<City> cityList1 = new Select().from(City.class).where("province_id = ?", provinceId).execute();
        if (CollectionUtils.isNotEmpty(cityList1)) {
            for (int i = cityList1.size() - 1; i >= 0; i--) {
                if (openCity == null || openCity.length == 0) {
                    break;
                }
                for (int cityId : openCity) {
                    if (cityList1.get(i).getCityId() != cityId) {
                        cityList1.remove(i);
                        break;
                    }
                }
            }
            cityList.clear();
            cityList.addAll(cityList1);
            Collections.sort(cityList, new Comparator<City>() {
                @Override
                public int compare(final City lhs, final City rhs) {
                    return lhs.getCityId() - rhs.getCityId();
                }
            });
            cityListView.setAdapter(cityAdapter);
            return;
        }
        final CityRequest request = new CityRequest();
        request.setProvinceId(provinceId);
        new RestClient().getCities(request, new Callback<List<City>>() {
            @Override
            public void success(final List<City> cityList2, final Response response) {
                Collections.sort(cityList2, new Comparator<City>() {
                    @Override
                    public int compare(final City lhs, final City rhs) {
                        return lhs.getCityId() - rhs.getCityId();
                    }
                });
                ActiveAndroid.beginTransaction();
                for (int i = cityList2.size() - 1; i >= 0; i--) {
                    City city = cityList2.get(i);
                    city.save();
                    if (openCity == null || openCity.length == 0) {
                        continue;
                    }
                    for (int cityId : openCity) {
                        if (city.getCityId() != cityId) {
                            cityList2.remove(i);
                            break;
                        }
                    }
                }
                ActiveAndroid.setTransactionSuccessful();
                ActiveAndroid.endTransaction();
                cityList.clear();
                cityList.addAll(cityList2);
                cityListView.setAdapter(cityAdapter);
            }

            @Override
            public void failure(final RetrofitError error) {

            }
        });
    }

    private void queryCounties(int cityId) {
        if (countyListView.getVisibility() != View.VISIBLE) {
            countyListView.setVisibility(View.VISIBLE);
//            ObjectAnimator.ofFloat(provinceListView, "translationX", 0, -width * 3 / 4).setDuration(500).start();
//            ObjectAnimator.ofFloat(cityListView, "translationX", width / 2, width / 5).setDuration(500).start();
//            ObjectAnimator.ofFloat(countyListView, "translationX", width, width * 3 / 5).setDuration(500).start();
            ObjectAnimator.ofFloat(cityListView, "translationX", width * 2 / 5, 0).setDuration(500).start();
            ObjectAnimator.ofFloat(countyListView, "translationX", width, width * 2 / 5).setDuration(500).start();
        }
        List<County> countyList1 = new Select().from(County.class).where("city_id = ?", cityId).execute();
        if (CollectionUtils.isNotEmpty(countyList1)) {
            countyList.clear();
            countyList.addAll(countyList1);
            countyListView.setAdapter(countyAdapter);
            return;
        }
        final CountyRequest request = new CountyRequest();
        request.setCityId(city.getCityId());
        new RestClient().getCounties(request, new Callback<List<County>>() {
            @Override
            public void success(final List<County> countyList2, final Response response) {
                countyList.clear();
                countyList.addAll(countyList2);
                Collections.sort(countyList, new Comparator<County>() {
                    @Override
                    public int compare(final County lhs, final County rhs) {
                        return lhs.getCountyId() - rhs.getCountyId();
                    }
                });
                countyListView.setAdapter(countyAdapter);
                ActiveAndroid.beginTransaction();
                for (County city : countyList2) {
                    city.save();
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
