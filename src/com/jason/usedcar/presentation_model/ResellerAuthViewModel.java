package com.jason.usedcar.presentation_model;

import android.text.TextUtils;
import android.view.View;
import com.jason.usedcar.MessageToast;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.request.Request;
import com.jason.usedcar.response.Response;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * @author t77yq @2014-09-27.
 */
@PresentationModel
public class ResellerAuthViewModel {

    private String name;

    private String bankName;

    private String bankAccount;

    /*
     * 0 : 身份证
     * 1 : 护照
     * 2 : 军人证
     */
    private int certificateType;

    private String certificateNumber;

    private ResellerAuthView resellerAuthView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public ResellerAuthViewModel(final ResellerAuthView resellerAuthView) {
        this.resellerAuthView = resellerAuthView;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(final String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setCertificateType(int certificateType) {
        this.certificateType = certificateType;
    }

    public int getNormal() {
        return resellerAuthView.isReseller() ? View.GONE : View.VISIBLE;
    }

    public void authorize() {
        if (!resellerAuthView.isReseller()) {
            if (TextUtils.isEmpty(name)) {
                return;
            }
            if (TextUtils.isEmpty(certificateNumber)) {
                return;
            }
        }
        if (TextUtils.isEmpty(bankName)) {
            return;
        }
        if (TextUtils.isEmpty(bankAccount)) {
            return;
        }
        resellerAuthView.before();
        new RestClient().authenticateUser(name, certificateType, certificateNumber, bankName, bankAccount,
                resellerAuthView.getAccessToken(), new Request().getDeviceId(), new Callback<Response>() {
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
}
