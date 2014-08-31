package com.jason.usedcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.jason.usedcar.Action;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.interfaces.viewmodel.ViewModel;
import com.jason.usedcar.presenter.Presenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.jason.usedcar.request.PublishUsedCarRequest;

/**
 * @author t77yq @2014-07-13.
 */
public class ResellerInfoFragment extends BaseFragment implements Ui, OnClickListener {

    private static final int ID_PHOTO = 1;

    private static final int ID_PHOTO2 = 2;

    private ImageView idPhotoImage;

    private ImageView idPhotoImage2;

    private CheckBox checkBox;

    private Bitmap[] licenseImages = new Bitmap[2];

    private Bitmap[] certificateImages = new Bitmap[2];

    private EditText contact;

    private EditText contactPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        View view = getView();
        idPhotoImage = getView(view, R.id.car_info_id_photo);
        idPhotoImage.setOnClickListener(this);
        idPhotoImage2 = getView(view, R.id.car_info_id_photo2);
        checkBox = getView(view, R.id.car_info_agreement_check);
        idPhotoImage2.setOnClickListener(this);
        contact = getView(view, R.id.carContact);
        contactPhone = getView(view, R.id.carContactPhone);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ID_PHOTO:
                    certificateImages[0] = (Bitmap) data.getExtras().get("data");
                    idPhotoImage.setImageBitmap(certificateImages[0]);
                    break;
                case ID_PHOTO2:
                    certificateImages[1] = (Bitmap)data.getExtras().get("data");
                    idPhotoImage2.setImageBitmap(certificateImages[1]);
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_framgent_sell_car_3, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                if (certificateImages[0] == null) {
                    Toast.makeText(getActivity(), "请上传车辆登记证图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (!checkBox.isChecked()) {
                    Toast.makeText(getActivity(), "请阅读并同意《服务条款》", Toast.LENGTH_SHORT).show();
                    return true;
                }
                PublishUsedCarRequest publishUsedCarRequest = new PublishUsedCarRequest();
                publishUsedCarRequest.setCarContact(String.valueOf(contact.getText()));
                publishUsedCarRequest.setContactPhone(String.valueOf(contactPhone.getText()));
                ((Action) getActivity()).action(this, certificateImages, publishUsedCarRequest);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_info_id_photo:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), ID_PHOTO);
                break;
            case R.id.car_info_id_photo2:
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), ID_PHOTO2);
                break;
        }
    }

    @Override
    public Presenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return this;
    }
}
