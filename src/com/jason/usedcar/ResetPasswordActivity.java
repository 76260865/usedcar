package com.jason.usedcar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.jason.usedcar.fragment.ObtainCodeFragment;

/**
 * @author t77yq @14-06-28.
 */
public class ResetPasswordActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content,
            new ObtainCodeFragment()).commit();
    }
}
