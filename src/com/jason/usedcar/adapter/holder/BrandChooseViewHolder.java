package com.jason.usedcar.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.jason.usedcar.R;
//import com.jason.usedcar.util.ViewFinder;

public class BrandChooseViewHolder extends ViewHolder {

	public final TextView txtBrandName;

	public BrandChooseViewHolder(View view) {
		txtBrandName = (TextView)view.findViewById(R.id.txt_brand_name);
	}
}