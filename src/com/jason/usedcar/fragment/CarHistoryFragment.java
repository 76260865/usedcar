package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.jason.usedcar.R;
import com.jason.usedcar.adapter.ShoppingCarAdapter;
import com.jason.usedcar.model.ShoppingCar;
import com.jason.usedcar.model.ShoppingCarModel;
import com.jason.usedcar.util.ViewFinder;

/**
 * Created by t77yq on 14-6-27.
 */
public class CarHistoryFragment extends Fragment implements OnItemClickListener {

    private ShoppingCarModel shoppingCarModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_car_bought, container, false);
        ListView listView = ViewFinder.findViewById(view, R.id.history_car_list);
        listView.setOnItemClickListener(this);
        shoppingCarModel = new ShoppingCarModel();
        listView.setAdapter(new ShoppingCarAdapter(getActivity(), shoppingCarModel));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ShoppingCar[] data = new ShoppingCar[10];
        for (int i = 0; i < 10; i++) {
            ShoppingCar item = new ShoppingCar();
            item.setOdometer("100" + i);
            item.setListPrice("" + i);
            item.setPurchaseDate("" + SystemClock.currentThreadTimeMillis());
            item.setPriceType("一口价");
            item.setPriceType(item.getPurchaseDate());
            data[i] = item;
        }
        shoppingCarModel.setData(data);
        shoppingCarModel.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
