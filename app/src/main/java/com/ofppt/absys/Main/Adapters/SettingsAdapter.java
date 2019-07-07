package com.ofppt.absys.Main.Adapters;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ofppt.absys.Main.UI.Items;
import com.ofppt.absys.R;

import static androidx.core.content.res.ResourcesCompat.getColor;

public class SettingsAdapter extends ArrayAdapter<Items> {

        public Context context;
        public int layoutResourceId;
        public Items[] data = null;

            public SettingsAdapter(Context context, int layoutResourceId, Items[] data) {
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
                holder.layout = row.findViewById(R.id.listXmen);

                row.setTag(holder);
            }
            else
            {
                holder = (ItemsHolder)row.getTag();
            }

            Items weather = data[position];
            holder.txtTitle.setText(weather.title);
            holder.imgIcon.setImageResource(weather.icon);
            if(weather.Sec){
                holder.layout.setBackgroundColor(Color.rgb(93, 118, 206));
                ViewGroup.LayoutParams params = holder.layout.getLayoutParams();
                // Changes the height and width to the specified *pixels*
                params.height = 100;
                holder.layout.setLayoutParams(params);
                holder.txtTitle.setTextSize(18);
                holder.txtTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                holder.imgIcon.setImageResource(0);
            }
            return row;
        }

        static class ItemsHolder
        {
            LinearLayout layout;
            ImageView imgIcon;
            TextView txtTitle;
        }
    }
