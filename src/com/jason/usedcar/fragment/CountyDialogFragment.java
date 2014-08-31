package com.jason.usedcar.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.jason.usedcar.R;
import com.jason.usedcar.RestClient;
import com.jason.usedcar.model.data.County;
import com.jason.usedcar.request.CountyRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author t77yq @2014-08-03.
 */

@SuppressWarnings("unchecked")
public class CountyDialogFragment extends DialogFragment {

    public interface Callback {
        public void itemClicked(int countyId);
    }

    public static CountyDialogFragment newInstance(int cityId) {
        Bundle args = new Bundle();
        args.putInt("city_id", cityId);
        CountyDialogFragment instance = new CountyDialogFragment();
        instance.setArguments(args);
        return instance;
    }

    private List<County> countyList = new ArrayList<County>();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_ratio, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView ratioList = getView(view, R.id.ratioList);
        List<County> countyList1 = new Select().from(County.class)
                .where("city_id = ?", getArguments().getInt("city_id", 0)).execute();
        if (countyList1 != null && !countyList1.isEmpty()) {
            countyList.clear();
            countyList.addAll(countyList1);
        } else {
        }
        ratioList.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return countyList.size();
            }

            @Override
            public County getItem(final int position) {
                return countyList.get(position);
            }

            @Override
            public long getItemId(final int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.ratio_grid_item, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.radio_item);
                textView.setText(getItem(position).getCountyName());
                return convertView;
            }
        });
        ratioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                ((Callback) getActivity()).itemClicked(countyList.get(position).getCountyId());
                dismiss();
            }
        });
    }

    protected <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }
}
