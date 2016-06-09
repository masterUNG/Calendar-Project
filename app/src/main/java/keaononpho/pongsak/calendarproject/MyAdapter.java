package keaononpho.pongsak.calendarproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterUNG on 6/9/16 AD.
 */
public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] timeStrings, detailStrings;

    public MyAdapter(Context context,
                     String[] timeStrings,
                     String[] detailStrings) {
        this.context = context;
        this.timeStrings = timeStrings;
        this.detailStrings = detailStrings;
    }

    @Override
    public int getCount() {
        return timeStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.my_listview, viewGroup, false);

        TextView timeTextView = (TextView) view1.findViewById(R.id.textView15);
        timeTextView.setText(timeStrings[i]);

        TextView detailTextView = (TextView) view1.findViewById(R.id.textView16);
        detailTextView.setText(detailStrings[i]);

        return view1;
    }
}   // Main Class
