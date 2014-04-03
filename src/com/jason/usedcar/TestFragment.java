package com.jason.usedcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jason.usedcar.fragment.BaseFragment;
import com.jason.usedcar.presenter.TestFragmentPresenter;
import com.jason.usedcar.presenter.TestFragmentPresenter.CallButtonUi;

public class TestFragment extends
		BaseFragment<TestFragmentPresenter, TestFragmentPresenter.CallButtonUi>
		implements TestFragmentPresenter.CallButtonUi {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getPresenter().login(getActivity());
		return inflater.inflate(R.layout.fragment_1, null);
	}

	@Override
	public void setEnabled(boolean on) {
		// TODO Auto-generated method stub

	}

	@Override
	public TestFragmentPresenter createPresenter() {
		return new TestFragmentPresenter();
	}

	@Override
	public CallButtonUi getUi() {
		return this;
	}

	@Override
	public void login(String reponse) {
		Toast.makeText(getActivity(), reponse, Toast.LENGTH_LONG).show();

	}
}
