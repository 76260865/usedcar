package com.jason.usedcar.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jason.usedcar.R;

/**
 * @author t77yq @2014-08-21.
 */
public class LoadingFragment extends DialogFragment {

    private static final String MESSAGE_ID = "message_id";

    private static final String MESSAGE = "message";

    public static LoadingFragment newInstance(int messageId) {
        Bundle args = new Bundle();
        args.putInt(MESSAGE_ID, messageId);
        LoadingFragment instance = new LoadingFragment();
        instance.setArguments(args);
        return instance;
    }


    public static LoadingFragment newInstance(String message) {
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        LoadingFragment instance = new LoadingFragment();
        instance.setArguments(args);
        return instance;
    }


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading_dialog, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            TextView messageTextView = (TextView) view.findViewById(R.id.textMessage);
            int messageId = args.getInt(MESSAGE_ID, 0);
            if (messageId != 0) {
                messageTextView.setText(messageId);
            }
            String message = args.getString(MESSAGE, "");
            if (!TextUtils.isEmpty(message)) {
                messageTextView.setText(message);
            }
        }
    }

    public void show(final FragmentManager manager) {
        super.show(manager, null);
    }
}
