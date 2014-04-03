package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jason.usedcar.interfaces.Ui;
import com.jason.usedcar.presenter.Presenter;

/**
 * Parent for all fragments that use Presenters and Ui design.
 */
public abstract class BaseFragment<T extends Presenter<U>, U extends Ui>
		extends Fragment {

	private T mPresenter;

	/**
	 * M: change to public
	 */
	public abstract T createPresenter();

	/**
	 * M: change to public
	 */
	public abstract U getUi();

	protected BaseFragment() {
		mPresenter = createPresenter();
	}

	/**
	 * Presenter will be available after onActivityCreated().
	 * 
	 * @return The presenter associated with this fragment.
	 */
	public T getPresenter() {
		return mPresenter;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPresenter.onUiReady(getUi());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mPresenter.onUiDestroy(getUi());
	}
}