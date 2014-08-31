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
import android.widget.ImageView;
import android.widget.Toast;
import com.jason.usedcar.Action;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.Presenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;

/**
 * @author t77yq @2014-07-13.
 */
public class CarBaseInfoFragment extends BaseFragment implements Ui, OnClickListener {

    private static final int FRONT_PHOTO = 1;

    private static final int LEFT_PHOTO = 2;

    private static final int RIGHT_PHOTO = 3;

    private static final int OTHER_PHOTO_1 = 4;

    private static final int OTHER_PHOTO_2 = 5;

    private static final int OTHER_PHOTO_3 = 6;

    private ImageView frontPhotoImage;

    private ImageView leftPhotoImage;

    private ImageView rightPhotoImage;

    private ImageView otherPhotoImage1;

    private ImageView otherPhotoImage2;

    private ImageView otherPhotoImage3;

    private Bitmap[] carBaseArray = new Bitmap[6];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
            View view = getView();
            frontPhotoImage = getView(view, R.id.car_info_front_photo);
            frontPhotoImage.setOnClickListener(this);
            leftPhotoImage = getView(view, R.id.car_info_left_photo);
            leftPhotoImage.setOnClickListener(this);
            rightPhotoImage = getView(view, R.id.car_info_right_photo);
            rightPhotoImage.setOnClickListener(this);
            otherPhotoImage1 = getView(view, R.id.car_info_other_photo_1);
            otherPhotoImage1.setOnClickListener(this);
            otherPhotoImage2 = getView(view, R.id.car_info_other_photo_2);
            otherPhotoImage2.setOnClickListener(this);
            otherPhotoImage3 = getView(view, R.id.car_info_other_photo_3);
            otherPhotoImage3.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            switch (requestCode) {
                case FRONT_PHOTO:
                    carBaseArray[0] = bitmap;
                    frontPhotoImage.setImageBitmap(bitmap);
                    break;
                case LEFT_PHOTO:
                    carBaseArray[1] = bitmap;
                    leftPhotoImage.setImageBitmap(bitmap);
                    break;
                case RIGHT_PHOTO:
                    carBaseArray[2] = bitmap;
                    rightPhotoImage.setImageBitmap(bitmap);
                    break;
                case OTHER_PHOTO_1:
                    carBaseArray[3] = bitmap;
                    otherPhotoImage1.setImageBitmap(bitmap);
                    otherPhotoImage2.setVisibility(View.VISIBLE);
                    break;
                case OTHER_PHOTO_2:
                    carBaseArray[4] = bitmap;
                    otherPhotoImage2.setImageBitmap(bitmap);
                    otherPhotoImage3.setVisibility(View.VISIBLE);
                    break;
                case OTHER_PHOTO_3:
                    carBaseArray[5] = bitmap;
                    otherPhotoImage3.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_framgent_sell_car_1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                if (carBaseArray[0] == null) {
                    Toast.makeText(getActivity(), "请上传正面图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (carBaseArray[1] == null) {
                    Toast.makeText(getActivity(), "请上传左前45度图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (carBaseArray[2] == null) {
                    Toast.makeText(getActivity(), "请上传右前45度图片", Toast.LENGTH_SHORT).show();
                    return true;
                }
                ((Action) getActivity()).action(this, carBaseArray);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Presenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public Ui getUi() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int requestCode = 0;
        switch (v.getId()) {
            case R.id.car_info_front_photo:
                requestCode = FRONT_PHOTO;
                break;
            case R.id.car_info_left_photo:
                requestCode = LEFT_PHOTO;
                break;
            case R.id.car_info_right_photo:
                requestCode = RIGHT_PHOTO;
                break;
            case R.id.car_info_other_photo_1:
                requestCode = OTHER_PHOTO_1;
                break;
            case R.id.car_info_other_photo_2:
                requestCode = OTHER_PHOTO_2;
                break;
            case R.id.car_info_other_photo_3:
                requestCode = OTHER_PHOTO_3;
                break;
        }
        if (requestCode != 0) {
            startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), requestCode);
        }
    }
}
