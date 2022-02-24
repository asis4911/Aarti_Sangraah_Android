package ashish.xdroid.aartisangraah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGrid extends BaseAdapter {

    private final String[] data;
    private final int[] Imageid;
    private Context mContext;

    public CustomGrid(Context c, String[] data, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_single, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.grid_text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_image);
        textView.setText(data[position]);
        imageView.setImageResource(Imageid[position]);

        return convertView;
    }
}
