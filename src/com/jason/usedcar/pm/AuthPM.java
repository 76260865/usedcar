package com.jason.usedcar.pm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.constants.Constants;
import com.jason.usedcar.pm.item.SpinnerItemViewModel;
import com.jason.usedcar.pm.item.SpinnerItemViewModel2;
import com.jason.usedcar.pm.view.ResellerAuthView;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.Response;
import com.jason.usedcar.response.UserAuthInfo;
import java.util.Arrays;
import java.util.List;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-27.
 */
@PresentationModel
public class AuthPM {

    private int selectedIdIndex;

    private int selectedBankIndex;

    private int selectedBankTypeIndex;

    private List<String> bankList;

    private List<String> bankTypeList;

    private ResellerAuthView resellerAuthView;

    private Bitmap bitmap;

    private UserAuthInfo userAuthInfo = new UserAuthInfo();

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public AuthPM(final ResellerAuthView resellerAuthView) {
        this.resellerAuthView = resellerAuthView;
        bankList = Arrays.asList(resellerAuthView.getContext().getResources().getStringArray(R.array.bank_list));
        bankTypeList = Arrays.asList(resellerAuthView.getContext().getResources().getStringArray(R.array.bank_type_list));
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(value = SpinnerItemViewModel.class)
    public List<String> getIdList() {
        return Arrays.asList(resellerAuthView.getContext().getResources().getStringArray(R.array.id_type_list));
    }

    public String getPicUrl() {
        String imageUrl = userAuthInfo.getImageUrl();
        if (imageUrl == null || imageUrl.equals("")) {
            return null;
        }
        return Constants.URL_BASE + userAuthInfo.getImageUrl();
    }

    @ItemPresentationModel(value = SpinnerItemViewModel2.class)
    public List<String> getBankList() {
        return bankList;
    }

    @ItemPresentationModel(value = SpinnerItemViewModel2.class)
    public List<String> getBankTypeList() {
        return bankTypeList;
    }

    public String getUsername() {
        return userAuthInfo.getRealName();
    }

    public void setUsername(final String username) {
        userAuthInfo.setRealName(username);
    }

    public String getCertificateNumber() {
        return userAuthInfo.getCertificateNumber();
    }

    public void setCertificateNumber(String certificateNumber) {
        userAuthInfo.setCertificateNumber(certificateNumber);
    }

    public String getBankName() {
        return userAuthInfo.getBankName();
    }

    public void setBankName(final String bankName) {
        userAuthInfo.setBankName(bankName);
    }

    public String getBankAccount() {
        return userAuthInfo.getBankAccount();
    }

    public void setBankAccountName(final String bankAccountName) {
        userAuthInfo.setBankAccountName(bankAccountName);
    }

    public String getBankAccountName() {
        return userAuthInfo.getBankAccountName();
    }

    public void setBankAccount(final String bankAccount) {
        userAuthInfo.setBankAccount(bankAccount);
    }

    public void setSelectedIdIndex(int index) {
        this.selectedIdIndex = index;
        userAuthInfo.setCertificateType(index + 1);
    }

    public int getSelectedIdIndex() {
        return selectedIdIndex;
    }

    public void setSelectedBankIndex(int index) {
        selectedBankIndex = index;
        userAuthInfo.setBankName(bankList.get(index));
    }

    public int getSelectedBankIndex() {
        return selectedBankIndex;
    }

    public void setSelectedBankTypeIndex(int index) {
        selectedBankTypeIndex = index;
        userAuthInfo.setBankType(index == 0 ? 11 : 12);
    }

    public int getSelectedBankTypeIndex() {
        return selectedBankTypeIndex;
    }

    public void loadPic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((ActionBarActivity) resellerAuthView.getContext()).startActivityForResult(intent, 100);
    }

    public void setPic(Bitmap bitmap) {
        this.bitmap = bitmap;
        userAuthInfo.setImageUrl(null);
        presentationModelChangeSupport.firePropertyChange("pic");
    }

    public Bitmap getPic() {
        return bitmap;
    }

    public String getOriginBankAddress() {
        String province = userAuthInfo.getBankProvince();
        String city = userAuthInfo.getBankCity();
        if (province == null) {
            province = "";
        }
        if (city == null) {
            city = "";
        }
        if (province.equals(city)) {
            return city;
        }
        return province + city;
    }

    public void setOriginBankAddress(String bankProvince, String bankCity) {
        userAuthInfo.setBankProvince(bankProvince);
        userAuthInfo.setBankCity(bankCity);
        presentationModelChangeSupport.firePropertyChange("originBankAddress");
    }

    public void pickBankAddress() {
        resellerAuthView.pickOriginBankAddress();
    }

    public int getNormal() {
        return resellerAuthView.isReseller() ? View.GONE : View.VISIBLE;
    }

    public void authorize() {
        if (!resellerAuthView.isReseller()) {
            if (TextUtils.isEmpty(userAuthInfo.getRealName())) {
                return;
            }
            if (TextUtils.isEmpty(userAuthInfo.getCertificateNumber())) {
                return;
            }
            if (bitmap == null) {
                return;
            }
        }
        if (TextUtils.isEmpty(userAuthInfo.getBankAccount())) {
            return;
        }
        if (TextUtils.isEmpty(userAuthInfo.getBankProvince())) {
            return;
        }
        if (TextUtils.isEmpty(userAuthInfo.getBankCity())) {
            return;
        }
        resellerAuthView.before();
        new RestClient().authenticateUser(userAuthInfo, resellerAuthView.getAccessToken(), new Request().getDeviceId(),
                new Callback<Response>() {
                    @Override
                    public void success(final Response response, final retrofit.client.Response response2) {
                        resellerAuthView.after();
                        if (response != null && response.isExecutionResult()) {
                            MessageToast.makeText(resellerAuthView.getContext(), response.getMessage()).show();
                            resellerAuthView.finish();
                        }
                    }

                    @Override
                    public void failure(final RetrofitError error) {
                        resellerAuthView.after();
                    }
                });
    }

    public void loadData() {
        resellerAuthView.before();
        new RestClient().getUserAuthInfo(resellerAuthView.getAccessToken(), new Request().getDeviceId(), new Callback<UserAuthInfo>() {
            @Override
            public void success(UserAuthInfo info, retrofit.client.Response response) {
                if (info != null) {
                    userAuthInfo = info;
                    selectedIdIndex = userAuthInfo.getCertificateType() - 1;
                    selectedBankTypeIndex = userAuthInfo.getBankType() == 11 ? 0 : 1;
                    for (int i = bankList.size() - 1; i >= 0; i--) {
                        if (bankList.get(i).equals(userAuthInfo.getBankName())) {
                            selectedBankIndex = i;
                        }
                    }
                }
                presentationModelChangeSupport.refreshPresentationModel();
                resellerAuthView.after();
            }

            @Override
            public void failure(RetrofitError error) {
                resellerAuthView.after();
            }
        });
    }

}
