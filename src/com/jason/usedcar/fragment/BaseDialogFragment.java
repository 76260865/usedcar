package com.jason.usedcar.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.jason.usedcar.R;

/**
 * @author t77yq @2014.06.07
 */
public class BaseDialogFragment extends DialogFragment {

    private static final String CONTENT = "content";

    public static BaseDialogFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(CONTENT, content);
        BaseDialogFragment instance = new BaseDialogFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_base_dialog, container, false);
        TextView dialogContentView = (TextView) contentView.findViewById(R.id.dialog_content);
        Button buttonOk = (Button) contentView.findViewById(R.id.dialog_button_confirm);
        Bundle args = getArguments();
        if (args != null) {
            dialogContentView.setText(args.getString(CONTENT));
        }
        buttonOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return contentView;
    }

    public void show(FragmentManager manager) {
        super.show(manager, "通知");
    }
}
