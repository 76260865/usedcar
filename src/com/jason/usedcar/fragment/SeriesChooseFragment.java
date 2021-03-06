package com.jason.usedcar.fragment;

import android.support.v4.app.FragmentManager;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.adapter.SeriersAdapter;
import com.jason.usedcar.model.data.FilterEntity;
import com.jason.usedcar.request.*;
import com.jason.usedcar.response.SeriesResponse;

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

import com.jason.usedcar.model.UsedCarModel;
import retrofit.*;

public class SeriesChooseFragment extends DialogFragment {
    private static final String TAG = "SeriesChooseFragment";

    // 使用这个接口的实例提供行动的事件
    private SeriersChooseDialogListener mChooseListener;

    private UsedCarModel<FilterEntity> mSeriersModel = new UsedCarModel<FilterEntity>();

    private SeriersAdapter mAdapter;

    public  String facetSelection;

    /**
     * 实现这个接口的类需要实现这两个方法
     */
    public interface SeriersChooseDialogListener {
        void onSeriesChoosed(FilterEntity filter);
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
        mAdapter = new SeriersAdapter(getActivity(), mSeriersModel);
            SeriesRequest seriesRequest = new SeriesRequest();
            seriesRequest.setFacetSelections(facetSelection);
            new RestClient().getSeriesByBrandSelection(seriesRequest, new Callback<SeriesResponse>() {
                @Override
                public void success(SeriesResponse response, retrofit.client.Response response2) {
//                    mSeriersModel.add(series);
                    if (response != null) {
                        if (response.isExecutionResult()) {
                            mSeriersModel.add(response.getSeries());
                            mSeriersModel.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    retrofitError.getBody();
                }
            });
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
                ((SeriersChooseDialogListener) getActivity()).onSeriesChoosed(mSeriersModel.get(position));
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void show(final FragmentManager manager, final String tag) {
        mSeriersModel.setData(null);
        super.show(manager, tag);
    }
}
