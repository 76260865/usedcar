package com.jason.usedcar.presenter;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import com.jason.usedcar.R;
import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.TestFragmentPresenter.CallButtonUi;

/**
 * Logic for call buttons.
 */
public class TestFragmentPresenter extends BasePresenter<CallButtonUi> {
    private static final String TAG = "TestFragmentPresenter";

    public void login(final Context context) {
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bottombg);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bos);
        final byte[] bmp = bos.toByteArray();

    }

    public interface CallButtonUi extends Ui {

        void setEnabled(boolean on);

        void login(String response);
    }
}
