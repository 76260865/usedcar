package com.jason.usedcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jason.usedcar.AboutActivity;
import com.jason.usedcar.FeedbackActivity;
import com.jason.usedcar.R;

public class MoreFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonFeedback).setOnClickListener(this);
        view.findViewById(R.id.buttonAboutUs).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("更多");
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.buttonFeedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.buttonAboutUs:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }
}
