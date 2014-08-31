package com.jason.usedcar;

import com.jason.usedcar.model.UsedCar;
import com.jason.usedcar.model.data.Brand;
import com.jason.usedcar.model.data.CarModel;
import com.jason.usedcar.model.data.City;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.model.data.Province;
import com.jason.usedcar.model.data.Series;
import com.jason.usedcar.request.CarRequest;
import com.jason.usedcar.request.CityRequest;
import com.jason.usedcar.request.CountyRequest;
import com.jason.usedcar.request.FavoriteCarRequest;
import com.jason.usedcar.request.ForgetPasswordRequest;
import com.jason.usedcar.request.ImageUploadRequest;
import com.jason.usedcar.request.LoginRequest;
import com.jason.usedcar.request.ModelRequest;
import com.jason.usedcar.request.ObtainCodeRequest;
import com.jason.usedcar.request.PagedRequest;
import com.jason.usedcar.request.PhoneRequest;
import com.jason.usedcar.request.PublishUsedCarRequest;
import com.jason.usedcar.request.RegisterRequest;
import com.jason.usedcar.request.RegisterResellerRequest;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.request.ResetPasswordByPhoneRequest;
import com.jason.usedcar.request.SeriesRequest;
import com.jason.usedcar.request.ShoppingCarOperationRequest;
import com.jason.usedcar.request.SuggestionRequest;
import com.jason.usedcar.request.TokenGenerateRequest;
import com.jason.usedcar.request.UpdatePasswordRequest;
import com.jason.usedcar.request.Upgrade2Request;
import com.jason.usedcar.request.UpgradeRequest;
import com.jason.usedcar.request.UserInfoRequest;
import com.jason.usedcar.response.CarListResponse;
import com.jason.usedcar.response.CarResponse;
import com.jason.usedcar.response.LoginResponse;
import com.jason.usedcar.response.ObtainCodeResponse;
import com.jason.usedcar.response.PasswordResponse;
import com.jason.usedcar.response.Response;
import com.jason.usedcar.response.TokenGenerateResponse;
import com.jason.usedcar.response.Upgrade2Response;
import com.jason.usedcar.response.UpgradeResponse;
import com.jason.usedcar.response.UploadImageResponse;
import com.jason.usedcar.response.UserInfoResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.android.MainThreadExecutor;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedString;

/**
 * @author t77yq @2014-08-01.
 */
public class RestClient {

    private static final String URL = "http://112.124.62.114:80";

    private static final Map<String, ExecutorService> POOL = new HashMap<String, ExecutorService>(24);

    public void cancel(String key) {
        ExecutorService executorService = POOL.get(key);
        if (executorService != null && !executorService.isTerminated() && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private <T> T createService(String key, Map<String, ExecutorService> pool, Class<T> service) {
        return createBuilder(key, pool).build().create(service);
    }

    private Builder createBuilder(String key, Map<String, ExecutorService> pool) {
        ExecutorService executorService = pool.get(key);
        if (executorService == null || executorService.isTerminated() || executorService.isShutdown()) {
            executorService = Executors.newCachedThreadPool();
            pool.put(key, executorService);
        }
        return new Builder()
                .setEndpoint(URL)
                .setExecutors(executorService, new MainThreadExecutor())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("User-Agent", Config.USER_AGENT);
                    }
                }).setLogLevel(RestAdapter.LogLevel.FULL);
    }

    interface ILogin {
        @FormUrlEncoded
        @POST("/login")
        void login(
                @Field("phoneOrEmail") String phoneOrEmail,
                @Field("password") String password,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<LoginResponse> callback);
    }

    public void login(LoginRequest request, Callback<LoginResponse> callback) {
        createService("login", POOL, ILogin.class)
                .login(request.getPhoneOrEmail(), request.getPassword(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IRegister {
        @FormUrlEncoded
        @POST("/signon")
        void register(
                @Field("phone") String phone,
                @Field("phoneVerifyCode") String phoneVerifyCode,
                @Field("acceptTerm") boolean acceptTerm,
                @Field("password") String password,
                @Field("repassword") String confirmPassword,
                @Field("accountType") int accountType,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void register(RegisterRequest request, Callback<Response> callback) {
        createService("register", POOL, IRegister.class)
                .register(request.getPhone(), request.getPhoneVerifyCode(),
                        request.isAcceptTerm(), request.getPassword(),
                        request.getConfirmPassword(), request.getAccountType(),
                        request.getDeviceId(), callback);
    }

    interface IRegisterReseller {
        @FormUrlEncoded
        @POST("/reselleSignon")
        void registerReseller(
                @Field("phone") String phone,
                @Field("phoneVerifyCode") String phoneVerifyCode,
                @Field("resellerName") String resellerName,
                @Field("resellerType") int resellerType,
                @Field("adress") String address,
                @Field("acceptTerm") boolean acceptTerm,
                @Field("password") String password,
                @Field("repassword") String confirmPassword,
                @Field("accountType") int accountType,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void registerReseller(RegisterResellerRequest request, Callback<Response> callback) {
        createService("registerReseller", POOL, IRegisterReseller.class)
                .registerReseller(request.getPhone(), request.getPhoneVerifyCode(),
                        request.getResellerName(), request.getResellerType(), request.getAddress(),
                        request.isAcceptTerm(), request.getPassword(),
                        request.getConfirmPassword(), request.getAccountType(),
                        request.getDeviceId(), callback);
    }

    interface IObtainCode {
        @FormUrlEncoded
        @POST("/account/obtainCode")
        void obtainCode(
                @Field("phoneNumber") String phoneNumber,
                @Field("type") int type,
                @Field("deviceId") String deviceId,
                Callback<ObtainCodeResponse> callback);
    }

    public void obtainCode(ObtainCodeRequest request, Callback<ObtainCodeResponse> callback) {
        createService("obtainCode", POOL, IObtainCode.class)
                .obtainCode(request.getPhoneNumber(), request.getType(),
                        request.getDeviceId(), callback);
    }

    interface IGetUsedCarList {
        @POST("/product/getUsedCarList.json")
        void getUsedCarList(
                @Body PagedRequest request,
                Callback<CarListResponse> callback);
        @Multipart
        @POST("/product/getUsedCarList.json")
        void getUsedCarList(
                @Part("pageIndex") Integer pageIndex,
                @Part("pageSize") Integer pageSize,
                @Part("accessToken") String accessToken,
                @Part("deviceId") String deviceId,
                Callback<CarListResponse> callback);
    }

    public void getUsedCarList(final PagedRequest request, final Callback<CarListResponse> callback) {
//        createService("getUsedCarList", POOL, IGetUsedCarList.class)
//                .getUsedCarList(request, callback);
        createService("getUsedCarList", POOL, IGetUsedCarList.class).getUsedCarList(
                request.getPageIndex(),
                request.getPageSize(),
                request.getAccessToken(),
                request.getDeviceId(),
                callback);
    }

    interface IGetUsedCar {
        @POST("/product/getUsedCar.json")
        void getUsedCar(
                @Body CarRequest request,
                Callback<CarResponse> callback);
        @Multipart
        @POST("/product/getUsedCar.json")
        void getUsedCar(
                @Part("productId") String productId,
                @Part("accessToken") String accessToken,
                @Part("deviceId") String deviceId,
                Callback<CarResponse> callback);
    }

    public void getUsedCar(CarRequest request, Callback<CarResponse> callback) {
//        createService("getUsedCar", POOL, IGetUsedCar.class)
//                .getUsedCar(request, callback);
        createService("getUsedCar", POOL, IGetUsedCar.class).getUsedCar(
                request.getProductId(),
                request.getAccessToken(),
                request.getDeviceId(),
                callback);
    }

    interface IUsedCarFavorite {
        @FormUrlEncoded
        @GET("/product/UsedCarFavority")
        void usedCarFavorite(
                @Field("productId") String productId,
                @Field("favority") boolean favorite,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void usedCarFavorite(FavoriteCarRequest request, Callback<Response> callback) {
        createService("usedCarFavorite", POOL, IUsedCarFavorite.class)
                .usedCarFavorite(request.getProductId(), request.isFavorite(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IShoppingCarOperation {
        @FormUrlEncoded
        @GET("/product/ShoppingCarOperation")
        void shoppingCarOperation(
                @Field("productId") String productId,
                @Field("addShoppingCar") boolean addShoppingCar,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void shoppingCarOperation(ShoppingCarOperationRequest request, Callback<Response> callback) {
        createService("shoppingCarOperation", POOL, IShoppingCarOperation.class)
                .shoppingCarOperation(request.getProductId(), request.isAddShoppingCar(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IGetBrands {
        @FormUrlEncoded
        @POST("/product/getBrands")
        void getBrands(
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<List<Brand>> callback);
    }

    public void getBrands(Request request, Callback<List<Brand>> callback) {
        createService("getBrands", POOL, IGetBrands.class)
                .getBrands(request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IGetSeries {
        @FormUrlEncoded
        @POST("/product/getSeries")
        void getSeries(
                @Field("brandId") int brandId,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<List<Series>> callback);
    }

    public void getSeries(SeriesRequest request, Callback<List<Series>> callback) {
        createService("getSeries", POOL, IGetSeries.class)
                .getSeries(request.getBrandId(), request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface IGetModels {
        @FormUrlEncoded
        @POST("/product/getModels")
        void getModels(
                @Field("seriesId") int seriesId,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<List<CarModel>> callback);
    }

    public void getModels(ModelRequest request, Callback<List<CarModel>> callback) {
        createService("getModels", POOL, IGetModels.class)
                .getModels(request.getSeriesId(), request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface IGetProvinces {
        @FormUrlEncoded
        @POST("/address/getProvinces")
        void getProvinces(
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<List<Province>> callback);
    }

    public void getProvinces(Request request, Callback<List<Province>> callback) {
        createService("getProvinces", POOL, IGetProvinces.class)
                .getProvinces(request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IGetCities {
        @FormUrlEncoded
        @POST("/address/getCities")
        void getCities(
                @Field("provinceId") int provinceId,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<List<City>> callback);
    }

    public void getCities(CityRequest request, Callback<List<City>> callback) {
        createService("getCities", POOL, IGetCities.class)
                .getCities(request.getProvinceId(), request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface IGetCounties {
        @FormUrlEncoded
        @POST("/address/getCounties")
        void getCounties(
                @Field("cityId") int cityId,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<List<County>> callback);
    }

    public void getCounties(CountyRequest request, Callback<List<County>> callback) {
        createService("getCounties", POOL, IGetCounties.class)
                .getCounties(request.getCityId(), request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface IImageUpload {
        @Multipart
        @POST("/product/imageUpload.json")
        void uploadImage(
                @Part("image") TypedByteArray image,
                @Part("accessToken") String accessToken,
                @Part("deviceId") String deviceId,
                Callback<UploadImageResponse> callback);
        @Multipart
        @POST("/product/imageUpload.json")
        UploadImageResponse uploadImage(
                @Part("image") TypedByteArray image,
                @Part("accessToken") String accessToken,
                @Part("deviceId") String deviceId);
    }

    public UploadImageResponse uploadImage(ImageUploadRequest request) {
        TypedByteArray image = new TypedByteArray("image/png", request.getImage()) {
            @Override
            public String fileName() {
                return "test.png";
            }
        };
        return createService("uploadImage", POOL, IImageUpload.class)
                .uploadImage(image, request.getAccessToken(),
                        request.getDeviceId());
    }

    public void uploadImage(ImageUploadRequest request, Callback<UploadImageResponse> callback) {
        TypedByteArray image = new TypedByteArray("image/png", request.getImage()) {
            @Override
            public String fileName() {
                return "test.png";
            }
        };
        createService("uploadImage", POOL, IImageUpload.class)
                .uploadImage(image, request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface IPublishUsedCar {
        @Multipart
        @POST("/product/publishUsedCar.json")
        void publishUsedCar(
                @Part("modelId") Integer modelId,
                @Part("seriesId") Integer seriesId,
                @Part("brandId") Integer brandId,
                @Part("imageIds") String imageIds,
                @Part("licenseImageIds") String licenseImageIds,
                @Part("certificateImageId") String certificateImageId,
                @Part("purchaseDate") String purchaseDate,
                @Part("odometer") Double odometer,
                @Part("listPrice") Double listPrice,
                @Part("priceType") Integer priceType,
                @Part("paymentMethod") Integer paymentMethod,
                @Part("carVin") String carVin,
                @Part("carContact") String carContact,
                @Part("contactPhone") String contactPhone,
                @Part("provinceId")Integer provinceId,
                @Part("cityId") Integer cityId,
                @Part("countyId") Integer countyId,
                @Part("street") String street,
                @Part("acceptTerm") Boolean acceptTerm,
                @Part("accessToken") String accessToken,
                @Part("deviceId") String deviceId,
                Callback<Response> callback);

        @POST("/product/publishUsedCar.json")
        void publishUsedCar(
                @Body PublishUsedCarRequest request,
                Callback<Response> callback);
    }

    public void publishUsedCar(PublishUsedCarRequest request, Callback<Response> callback) {
//        createService("publishUsedCar", POOL, IPublishUsedCar.class)
//            .publishUsedCar(request, callback);
        createService("publishUsedCar", POOL, IPublishUsedCar.class)
                .publishUsedCar(
                        request.getModelId(),
                        request.getSeriesId(),
                        request.getBrandId(),
                        request.getImageIds(),
                        request.getLicenseImageIds(),
                        request.getCertificateImageId(),
                        request.getPurchaseDate(),
                        request.getOdometer(),
                        request.getListPrice(),
                        request.getPriceType(),
                        request.getPaymentMethod(),
                        request.getCarVin(),
                        request.getCarContact(),
                        request.getContactPhone(),
                        request.getProvinceId(),
                        request.getCityId(),
                        request.getCountyId(),
                        request.getStreet(),
                        request.getAcceptTerm(),
                        request.getAccessToken(),
                        request.getDeviceId(),
                        callback);
    }

    interface IShoppingCarList {
        @FormUrlEncoded
        @GET("/product/ShoppingCarList")
        void shoppingCarList(
                @Field("pageIndex") int pageIndex,
                @Field("pageSize") int pageSize,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<CarListResponse> callback);
    }

    public void shoppingCarList(PagedRequest request, Callback<CarListResponse> callback) {
//        createService("shoppingCarList", POOL, IShoppingCarList.class)
//            .shoppingCarList(request.getPageIndex(), request.getPageSize(),
//                request.getAccessToken(), request.getDeviceId(), callback);
        final List<UsedCar> carList = new ArrayList<UsedCar>(request.getPageSize());
        if (request.getPageIndex() < 5) {
            for (int i = 0; i < request.getPageSize(); i++) {
                UsedCar car = new UsedCar();
                car.setListPrice((int) (System.currentTimeMillis() % 10000));
                carList.add(car);
            }
        }
        CarListResponse response = new CarListResponse();
        response.setExecutionResult(true);
        response.setUsedCars(carList);
        callback.success(response, null);

    }

    interface IViewUserInfo {
        @Multipart
        @POST("/account/viewUserInfo.json")
        void viewUserInfo(
                @Part("accessToken") String accessToken,
                @Part("deviceId") String deviceId,
                Callback<UserInfoResponse> callback);
    }

    public void viewUserInfo(Request request, Callback<UserInfoResponse> callback) {
        createService("viewUserInfo", POOL, IViewUserInfo.class)
                .viewUserInfo(request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IUpdateUserInfo {
        @FormUrlEncoded
        @POST("/account/updateUserInfo")
        void updateUserInfo(
                @Field("nickname") String nickname,
                @Field("realName") String realName,
                @Field("sex") boolean sex,
                @Field("birthyear") String birthYear,
                @Field("birthmonth") String birthMonth,
                @Field("birthday") String birthDay,
                @Field("certificateNumber") String certificateNumber,
                @Field("province") String province,
                @Field("city") String city,
                @Field("county") String county,
                @Field("street") String street,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void updateUserInfo(UserInfoRequest request, Callback<Response> callback) {
        createService("updateUserInfo", POOL, IUpdateUserInfo.class)
                .updateUserInfo(request.getNickname(), request.getRealName(), request.isSex(),
                        request.getBirthYear(), request.getBirthMonth(), request.getBirthDay(),
                        request.getCertificateNumber(), request.getProvince(), request.getCity(),
                        request.getCounty(), request.getStreet(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IForgetPassword {
        @FormUrlEncoded
        @POST("/forgetPassword")
        void forgetPassword(
                @Field("loginName") String loginName,
                @Field("deviceId") String deviceId,
                Callback<PasswordResponse> callback);
    }

    public void forgetPassword(ForgetPasswordRequest request, Callback<PasswordResponse> callback) {
        createService("forgetPassword", POOL, IForgetPassword.class)
                .forgetPassword(request.getLoginName(), request.getDeviceId(), callback);
    }

    interface IResetPasswordByPhone {
        @FormUrlEncoded
        @POST("/resetPasswordByPhone")
        void resetPasswordByPhone(
                @Field("principle") String principle,
                @Field("activeCode") String activeCode,
                @Field("newPassword") String newPassword,
                @Field("confirmPassword") String confirmPassword,
                @Field("deviceId") String deviceId,
                Callback<PasswordResponse> callback);
    }

    public void resetPasswordByPhone(ResetPasswordByPhoneRequest request, Callback<PasswordResponse> callback) {
        createService("resetPasswordByPhone", POOL, IResetPasswordByPhone.class)
                .resetPasswordByPhone(request.getPrinciple(), request.getActiveCode(),
                        request.getNewPassword(), request.getConfirmPassword(),
                        request.getDeviceId(), callback);
    }

    interface IUpdatePassword {
        @FormUrlEncoded
        @GET("/account/updatePassword")
        void updatePassword(
                @Field("oldPWD") String oldPassword,
                @Field("newPWD") String newPassword,
                @Field("confirmPWD") String confirmPassword,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void updatePassword(UpdatePasswordRequest request, Callback<Response> callback) {
        createService("updatePassword", POOL, IUpdatePassword.class)
                .updatePassword(request.getOldPassword(), request.getNewPassword(), request.getConfirmPassword(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IValidBindPhone {
        @FormUrlEncoded
        @GET("/account/validBindedPhone")
        void validBindPhone(
                @Field("phoneNumber") String phoneNumber,
                @Field("code") String verifyCode,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void validBindPhone(PhoneRequest request, Callback<Response> callback) {
        createService("validBindPhone", POOL, IValidBindPhone.class)
                .validBindPhone(request.getPhoneNumber(), request.getCode(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IBindNewPhone {
        @FormUrlEncoded
        @POST("/account/bindNewPhone")
        void bindNewPhone(
                @Field("phoneNumber") String phoneNumber,
                @Field("code") String verifyCode,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void bindNewPhone(PhoneRequest request, Callback<Response> callback) {
        createService("bindNewPhone", POOL, IBindNewPhone.class)
                .bindNewPhone(request.getPhoneNumber(), request.getCode(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IFavoriteList {
        @FormUrlEncoded
        @POST("/product/FavorityList")
        void favoriteList(
                @Field("pageIndex") int pageIndex,
                @Field("pageSize") int pageSize,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<CarListResponse> callback);
    }

    public void favoriteList(PagedRequest request, Callback<CarListResponse> callback) {
        createService("favoriteList", POOL, IFavoriteList.class)
                .favoriteList(request.getPageIndex(), request.getPageSize(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface ISaleUsedCarList {
        @FormUrlEncoded
        @POST("/product/SaleUsedCarList")
        void saleUsedCarList(
                @Field("pageIndex") int pageIndex,
                @Field("pageSize") int pageSize,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<CarListResponse> callback);
    }

    public void saleUsedCarList(PagedRequest request, Callback<CarListResponse> callback) {
        createService("saleUsedCarList", POOL, ISaleUsedCarList.class)
                .saleUsedCarList(request.getPageIndex(), request.getPageSize(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IBuyUsedCarList {
        @FormUrlEncoded
        @POST("/product/BuyUsedCarList")
        void buyUsedCarList(
                @Field("pageIndex") int pageIndex,
                @Field("pageSize") int pageSize,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<CarListResponse> callback);
    }

    public void buyUsedCarList(PagedRequest request, Callback<CarListResponse> callback) {
        createService("buyUsedCarList", POOL, IBuyUsedCarList.class)
                .buyUsedCarList(request.getPageIndex(), request.getPageSize(),
                        request.getAccessToken(), request.getDeviceId(), callback);
    }

    interface IDeleteUsedCar {
        @FormUrlEncoded
        @POST("/product/deleteusedcar")
        void deleteUsedCar(
                @Field("productId") String productId,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void deleteUsedCar(CarRequest request, Callback<Response> callback) {
        createService("deleteUsedCar", POOL, IDeleteUsedCar.class)
                .deleteUsedCar(request.getProductId(), request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface ISuggestion {
        @FormUrlEncoded
        @POST("/more/Suggestion")
        void suggestion(
                @Field("suggestion") String suggestion,
                @Field("accessToken") String accessToken,
                @Field("deviceId") String deviceId,
                Callback<Response> callback);
    }

    public void suggestion(SuggestionRequest request, Callback<Response> callback) {
        createService("suggestion", POOL, ISuggestion.class)
                .suggestion(request.getMessage(), request.getAccessToken(),
                        request.getDeviceId(), callback);
    }

    interface IUpgrade {
        @FormUrlEncoded
        @POST("/more/Upgrade")
        void upgrade(
                @Field("message") String message,
                @Field("version") int version,
                @Field("deviceId") String deviceId,
                Callback<UpgradeResponse> callback);
    }

    public void upgrade(UpgradeRequest request, Callback<UpgradeResponse> callback) {
        createService("upgrade", POOL, IUpgrade.class)
                .upgrade(request.getMessage(), request.getVersion(),
                        request.getDeviceId(), callback);
    }

    interface IUpgrade2 {
        @FormUrlEncoded
        @POST("/more/Upgrade")
        void upgrade(
                @Field("citySyncLastUpdate") String citySyncLastUpdate,
                @Field("carSyncLastUpdate") String carSyncLastUpdate,
                @Field("message") String message,
                @Field("deviceId") String deviceId,
                Callback<Upgrade2Response> callback);
    }

    public void upgrade(Upgrade2Request request, Callback<Upgrade2Response> callback) {
        createService("upgrade2", POOL, IUpgrade2.class)
                .upgrade(request.getCitySyncLastUpdate(), request.getCarSyncLastUpdate(),
                        request.getMessage(), request.getDeviceId(), callback);
    }

    interface IGenerateAccessToken {
        @FormUrlEncoded
        @POST("/generateAccessToken")
        void generateAccessToken(
                @Field("userId") String userId,
                @Field("accessToken") String accessToken, Callback<TokenGenerateResponse> callback);
    }

    public void generateAccessToken(TokenGenerateRequest request, Callback<TokenGenerateResponse> callback) {
        createService("generateAccessToken", POOL, IGenerateAccessToken.class)
                .generateAccessToken(request.getUserId(), request.getAccessToken(), callback);
    }
}
