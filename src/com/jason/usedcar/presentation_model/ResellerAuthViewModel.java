package com.jason.usedcar.presentation_model;

import android.text.TextUtils;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.property.PropertyChangeSupport;

/**
 * @author t77yq @2014-09-27.
 */
@PresentationModel
public class ResellerAuthViewModel {

    private String accountName;

    private String name;

    private String bankName;

    private String bankAccount;

    private int year;

    private int month;

    private int day;

    private String foundationTime;

    private ResellerAuthView resellerAuthView;

    private PresentationModelChangeSupport presentationModelChangeSupport;

    public ResellerAuthViewModel(final ResellerAuthView resellerAuthView) {
        this.resellerAuthView = resellerAuthView;
        this.presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public void setYear(final int year) {
        this.year = year;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public void setDay(final int day) {
        this.day = day;
    }

    public String getFoundationTime() {
        return foundationTime;
    }

    public void updateFoundationTime() {
        foundationTime = String.format("%d年%02d月", year, month, day);
        presentationModelChangeSupport.firePropertyChange("foundationTime");
    }

    public void pickTime() {
        resellerAuthView.openDatePicker();
    }

    public void getPic() {
        resellerAuthView.launchCamera();
    }

    public void authorize() {
        //
        if (TextUtils.isEmpty(accountName)) {
            return;
        }
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (TextUtils.isEmpty(bankName)) {
            return;
        }
        if (TextUtils.isEmpty(bankAccount)) {
            return;
        }
    }
}
