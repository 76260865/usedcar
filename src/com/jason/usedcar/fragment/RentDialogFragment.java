package com.jason.usedcar.fragment;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import com.jason.usedcar.R;

/**
 * @author t77yq @2014-08-03.
 */

@SuppressWarnings("unchecked")
public class RentDialogFragment extends DialogFragment {

    public interface Callback {
        public void itemClicked(int type, int number);
    }

    public static final int RENT = 1;

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
        ratioList.setAdapter(new BaseAdapter() {

            private final int[] DATA = new int[]{
                    1, 2, 3, 6, 9, 12, 15, 18, 24
            };

            @Override
            public int getCount() {
                return DATA.length;
            }

            @Override
            public Object getItem(final int position) {
                return DATA[position];
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
                textView.setText(String.valueOf(DATA[position]).concat("个月"));
                return convertView;
            }
        });
        ratioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                ((Callback) getActivity()).itemClicked(RENT, (Integer) ratioList.getAdapter().getItem(position));
                dismiss();
            }
        });
    }

    protected <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }
}
