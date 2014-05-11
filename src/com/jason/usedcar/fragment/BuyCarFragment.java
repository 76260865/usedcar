package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.usedcar.R;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter;
import com.jason.usedcar.presenter.BuyCarFragmentPresenter.CallButtonUi;

public class BuyCarFragment extends
        BaseFragment<BuyCarFragmentPresenter, BuyCarFragmentPresenter.CallButtonUi> implements
        BuyCarFragmentPresenter.CallButtonUi {
    private ListView mListCar;
    private View mFooterLoadingView;
    private ListViewAdapter mListViewAdapter = new ListViewAdapter();
    private final Handler mHandler = new Handler();
    private int mVisibleItemCount = 0;// 当前窗口可见项总数
    private int mVisibleLastIndex = 0; // 最后的可视项索引

    private int mCount = 41;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getPresenter().login(getActivity());
        View view = inflater.inflate(R.layout.buy_car_fragment_layout, null);
        mFooterLoadingView = inflater.inflate(R.layout.footer_loading_layout, null);
        mListCar = (ListView) view.findViewById(R.id.listCar);
        mListCar.addFooterView(mFooterLoadingView);
        mListCar.setAdapter(mListViewAdapter);
        mListCar.setOnScrollListener(mOnScrollListener);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.buy_car_text);
    }

    @Override
    public void setEnabled(boolean on) {
        // TODO Auto-generated method stub

    }

    @Override
    public BuyCarFragmentPresenter createPresenter() {
        return new BuyCarFragmentPresenter();
    }

    @Override
    public CallButtonUi getUi() {
        return this;
    }

    @Override
    public void login(String reponse) {
        Toast.makeText(getActivity(), reponse, Toast.LENGTH_LONG).show();

    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int mScrollState) {
            int itemsLastIndex = mListViewAdapter.getCount() - 1; // 数据集最后一项的索引
            int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项

            /**
             * 当ListView滑动到最后一条记录时这时，我们会看到已经被添加到ListView的"加载项"布局， 这时应该加载剩余数据。
             */
            if (mVisibleLastIndex == lastIndex
                    && mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                if (mListViewAdapter.count <= mCount) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListViewAdapter.count += 10;
                            mListViewAdapter.notifyDataSetChanged();
//                            mListCar.setSelection(mVisibleLastIndex - mVisibleItemCount + 1);
                        }
                    }, 1000);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                int totalItemCount) {
            mVisibleItemCount = visibleItemCount;
            mVisibleLastIndex = firstVisibleItem + mVisibleItemCount - 1;
            if (mListViewAdapter.count > mCount) {
                mListCar.removeFooterView(mFooterLoadingView);
            }
        }
    };

    class ListViewAdapter extends BaseAdapter {
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
            TextView mTextView;
            if (view == null) {
                mTextView = new TextView(getActivity());
            } else {
                mTextView = (TextView) view;
            }
            mTextView.setText("Item " + position);
            mTextView.setTextSize(20f);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setHeight(60);
            return mTextView;
        }
    }
}
