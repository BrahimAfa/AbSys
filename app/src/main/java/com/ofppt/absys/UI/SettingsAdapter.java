package com.ofppt.absys.UI;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ofppt.absys.R;

public class SettingsAdapter extends ArrayAdapter<Items> {

        private Context context;
        private int layoutResourceId;
        private Items[] data = null;

            SettingsAdapter(Context context, int layoutResourceId, Items[] data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemsHolder holder = null;

            if(row == null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ItemsHolder();
                holder.imgIcon = row.findViewById(R.id.imgIcon);
                holder.txtTitle = row.findViewById(R.id.txtTitle);

                row.setTag(holder);
            }
            else
            {
                holder = (ItemsHolder)row.getTag();
            }

            Items weather = data[position];
            holder.txtTitle.setText(weather.title);
            holder.imgIcon.setImageResource(weather.icon);

            return row;
        }

        static class ItemsHolder
        {
            ImageView imgIcon;
            TextView txtTitle;
        }
    }
