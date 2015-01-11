package com.jason.usedcar.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jason.usedcar.R;
import com.jason.usedcar.model.data.FilterEntity;
import java.util.List;

public class CarAgeChooseFragment extends SearchConditionChooseFragment {
	public CarAgeChooseFragment(List<FilterEntity> filters) {
		super(filters);
	}

	private CarAgeChooseDialogListener mChooseListener;

	public interface CarAgeChooseDialogListener {
		void onCarAgeChoosed(FilterEntity filter);
	}
	public void setCarAgeChooseDialogListener(CarAgeChooseDialogListener listener){
		mChooseListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setTitle("车龄");
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ListView listView = (ListView) view.findViewById(R.id.listView_seriers);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mChooseListener != null) {
					mChooseListener.onCarAgeChoosed((FilterEntity) mAdapter
							.getItem(position));
				}
			}
		});
		return view;
	}
}
