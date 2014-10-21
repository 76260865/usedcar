package com.jason.usedcar.fragment;

import com.jason.usedcar.Action;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.FacetSeries;
import com.jason.usedcar.model.data.FilterEntity;
import com.jason.usedcar.request.*;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jason.usedcar.adapter.SeriersAdapter;
import com.jason.usedcar.db.DBHelper;
import com.jason.usedcar.model.UsedCarModel;
import com.jason.usedcar.model.data.Series;
import retrofit.*;

public class SeriesChooseFragment extends DialogFragment {
    private static final String TAG = "SeriesChooseFragment";

    // 使用这个接口的实例提供行动的事件
    private SeriersChooseDialogListener mChooseListener;

    private DBHelper mDbHelper;

    private UsedCarModel<Series> mSeriersModel = new UsedCarModel<Series>();

    private SeriersAdapter mAdapter;

    public  String facetSelection;

    /**
     * 实现这个接口的类需要实现这两个方法
     */
    public interface SeriersChooseDialogListener {
        void onSeriersChoosed(FilterEntity filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_Fullscreen);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("车型");
        return dialog;
    }

    /**
     * 覆盖Fragment.onAttach()这个函数来处理NoticeDialogListener实例
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDbHelper = new DBHelper(getActivity());
        mAdapter = new SeriersAdapter(getActivity(), mSeriersModel);
        if (mSeriersModel.isEmpty()) {
            SeriesRequest seriesRequest = new SeriesRequest();
            seriesRequest.setFacetSelections(facetSelection);
            new RestClient().getSeriesByBrandSelection(seriesRequest, new Callback<List<FacetSeries>>() {
                @Override
                public void success(List<FacetSeries> series, retrofit.client.Response response) {
//                    mSeriersModel.add(series);
                    mSeriersModel.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError retrofitError) {

                }
            });
        }
        // 校验主Activity实现回调接口
        try {
            // 获得NoticeDialogListener实例，这样我们就能将事件发送到主Activity
            mChooseListener = (SeriersChooseDialogListener) activity;
        } catch (ClassCastException e) {
            // activity没有实现这个接口则抛出异常
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO change the layout to seriers select layout
        View view = inflater.inflate(R.layout.fragment_seriers_choose,
                container, false);
        ListView listViewBrands = (ListView) view
                .findViewById(R.id.listView_seriers);
        listViewBrands.setAdapter(mAdapter);
        listViewBrands.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ((Action) getActivity()).action(SeriesChooseFragment.this,
                        mAdapter.getItem(position).getSeriesName(),
                        mAdapter.getItem(position).getSeriesId());
                dismiss();
            }
        });
        return view;
    }
}
