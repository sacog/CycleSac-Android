package org.sacog.cyclesac;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RiderTypeAdapter extends ArrayAdapter<RiderTypeAdapter.RiderType> {
    public static class RiderType {
        private String riderTypeText;
        private String riderTypeSubText;

        public RiderType(String riderTypeText, String riderTypeSubText) {
            this.riderTypeText = riderTypeText;
            this.riderTypeSubText = riderTypeSubText;
        }
    }

    public RiderTypeAdapter(Context context) {
        super(context, R.layout.rider_type_list_item);

        String[] riderTypes = context.getResources().getStringArray(R.array.ridertypeArray);
        String[] riderTypeSubTexts = context.getResources().getStringArray(R.array.ridertypesubtextArray);

        for(int i = 0; i < riderTypes.length; i++) {
            String riderTypeText = riderTypes[i];
            String riderTypeSubText = null;
            if(i < riderTypeSubTexts.length) {
                riderTypeSubText = riderTypeSubTexts[i];
            }
            RiderType riderType = new RiderType(riderTypeText, riderTypeSubText);
            this.add(riderType);
        }
    }

    private static class ViewHolder {
        TextView riderTypeTextView;
        TextView riderTypeSubTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new TextView(getContext());
        }

        RiderType riderType = this.getItem(position);
        TextView textView = (TextView)convertView;
        textView.setText(riderType.riderTypeText);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rider_type_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.riderTypeTextView = (TextView)convertView.findViewById(R.id.riderTypeText);
            viewHolder.riderTypeSubTextView = (TextView)convertView.findViewById(R.id.riderTypeSubText);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        RiderType riderType = this.getItem(position);
        viewHolder.riderTypeTextView.setText(riderType.riderTypeText);
        viewHolder.riderTypeSubTextView.setText(riderType.riderTypeSubText);
        return convertView;
    }
}
