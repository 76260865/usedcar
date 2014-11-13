package com.jason.usedcar;

import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.response.CarImage;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.CertificateImage;
import com.jason.usedcar.response.LicenseImage;
import com.jason.usedcar.util.CollectionUtils;
import java.util.List;

/**
 * @author t77yq @2014-11-09.
 */
public final class CarInfo {

    private static final String URL = Constants.URL_BASE;

    private Brand brand;
    private Series series;
    private CarModel model;
    private Province province;
    private City city;
    private County county;
    private String[] carImageUrls = new String[6];
    private String[] licenseImageUrls = new String[2];
    private String certificateImageUrl;

    public String getBrandName() {
        return brand == null ? "" : brand.getBrandName();
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getSeriesName() {
        return series == null ? "" : series.getSeriesName();
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public String getModelName() {
        return model == null ? "" : model.getModelName();
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public String getProvinceName() {
        return province == null ? "" : province.getProvinceName();
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getCityName() {
        return city == null ? "" : city.getCityName();
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCountyName() {
        return county == null ? "" : county.getCountyName();
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getCarImageUrl(int position) {
        String url = carImageUrls[position];
        return url == null || url.equals("") ? "" : URL + url;
    }

    public String getLicenseImageUrl(int position) {
        String url = licenseImageUrls[position];
        return url == null || url.equals("") ? "" : URL + url;
    }

    public String getCertificateImageUrl() {
        return certificateImageUrl == null || certificateImageUrl.equals("") ? "" : URL + certificateImageUrl;
    }

    public String street;
    public String purchaseDate;
    public double odometer;
    public double listPrice;
    public int priceType;
    public int paymentMethod;
    public String carVin;

    public final String[] imageIds = new String[6];

    public final String[] licenseImageIds = new String[2];
    public String certificateImageId;
    public String carContact;
    public String contactPhone;
    public boolean acceptTerm;

    public int getBrandId() {
        return brand == null ? 0 : brand.getBrandId();
    }

    public int getSeriesId() {
        return series == null ? 0 : series.getSeriesId();
    }

    public int getModelId() {
        return model == null ? 0 : model.getModelId();
    }

    public int getProvinceId() {
        return province == null ? 0 : province.getProvinceId();
    }

    public int getCityId() {
        return city == null ? 0 : city.getCityId();
    }

    public int getCountyId() {
        return county == null ? 0 : county.getCountyId();
    }

    public String getImageIdString() {
        String idStr = "";
        for (String id : imageIds) {
            if (id == null) {
                continue;
            }
            idStr += "," + id;
        }
        if (!idStr.equals("")) {
            idStr = idStr.replaceFirst(",", "");
        }
        return idStr;
    }

    public String getLicenseImageIdStr() {
        return licenseImageIds[0] + "," + licenseImageIds[1];
    }

    public static void convert(CarResponse response, CarInfo info) {
        Province province = new Province();
        province.setProvinceId(response.getProvinceId());
        province.setProvinceName(response.getProvince());
        info.setProvince(province);
        City city = new City();
        city.setCityId(response.getCityId());
        city.setCityName(response.getCity());
        County county = new County();
        county.setCountyId(response.getCountyId());
        county.setCountyName(response.getCounty());
        info.setCounty(county);
        Brand brand = new Brand();
        brand.setBrandId(response.getBrandId());
        brand.setBrandName(response.getBrandName());
        info.setBrand(brand);
        Series series = new Series();
        series.setSeriesId(response.getSeriesId());
        series.setSeriesName(response.getSeriesName());
        info.setSeries(series);
        CarModel model = new CarModel();
        model.setModelId(response.getModelId());
        model.setModelName(response.getModelDisplayName());
        info.setModel(model);
        info.street = response.getStreet();
        info.purchaseDate = response.getPurchaseDate();
        info.odometer = response.getOdometer();
        info.listPrice = response.getListPrice();
        info.priceType = response.getPriceType();
        info.paymentMethod = response.getPaymentMethod();
        info.carVin = response.getCarVin();
        List<String> imageIds = response.getImageIds();
        if (CollectionUtils.isNotEmpty(imageIds)) {
            for (int i = imageIds.size() - 1; i >= 0; i--) {
                info.imageIds[i] = imageIds.get(i);
            }
        }
        List<CarImage> carImages = response.getCarImages();
        if (CollectionUtils.isNotEmpty(carImages)) {
            for (int i = carImages.size() - 1; i >= 0; i--) {
                info.carImageUrls[i] = carImages.get(i).getSizeRegular();
            }
        }
        List<String> licenseImageIds = response.getLicenseImageIds();
        if (CollectionUtils.isNotEmpty(licenseImageIds)) {
            for (int i = licenseImageIds.size() - 1; i >= 0; i--) {
                info.licenseImageIds[i] = licenseImageIds.get(i);
            }
        }
        List<LicenseImage> licenseImages = response.getLicenseImages();
        if (CollectionUtils.isNotEmpty(licenseImages)) {
            for (int i = licenseImages.size() - 1; i >= 0; i--) {
                info.licenseImageUrls[i] = licenseImages.get(i).getSizeRegular();
            }
        }
        info.certificateImageId = response.getCertificateImageId();
        CertificateImage certificateImage = response.getCertificateImage();
        if (certificateImage != null) {
            info.certificateImageUrl = certificateImage.getSizeRegular();
        }
        info.carContact = response.getCarContact();
        info.contactPhone = response.getContactPhone();
        info.acceptTerm = true;
    }
}
