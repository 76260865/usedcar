package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jason.usedcar.R;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter;
import com.jason.usedcar.presenter.ShoppingCarFragmentPresenter.CallButtonUi;
import com.jason.usedcar.util.ViewFinder;

public class ShoppingCarFragment extends
        BaseFragment<ShoppingCarFragmentPresenter, ShoppingCarFragmentPresenter.CallButtonUi>
        implements ShoppingCarFragmentPresenter.CallButtonUi {
    private ListView mShoppingCarListView;
    private ShoppingCarListViewAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shoping_car_fragment, null);
        mShoppingCarListView = ViewFinder.findViewById(view, R.id.list_shopping_car);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.txt_shopping_car_str);

        mAdapter = new ShoppingCarListViewAdapter();
        mShoppingCarListView.setAdapter(mAdapter);
    }

    @Override
    public void setEnabled(boolean on) {
        // TODO Auto-generated method stub

    }

    @Override
    public ShoppingCarFragmentPresenter createPresenter() {
        return new ShoppingCarFragmentPresenter();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_shopping_car, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public CallButtonUi getUi() {
        return this;
    }

    @Override
    public void login(String response) {
        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

    }

    private class ShoppingCarListViewAdapter extends BaseAdapter {
        int count = 10;

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View view, ViewGroup parent) {
            return LayoutInflater.from(getActivity()).inflate(R.layout.item_shopping_car_layout,
                    null);
        }
    }
}
