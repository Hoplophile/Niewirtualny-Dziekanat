package com.example.piotr.niewirtualnydziekanat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.piotr.niewirtualnydziekanat.OpenNewsActivity;
import com.example.piotr.niewirtualnydziekanat.R;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<String> {

    private List<String> content;
    private Context context;

    public NewsAdapter(Context context, List<String> content) {
        super(context, 0, content);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView header = (TextView) convertView.findViewById(R.id.header);
        Button more = (Button) convertView.findViewById(R.id.more);

        date.setText(StringUtils.substringBetween(item, "-link\">", "<span style"));
        header.setText(StringUtils.substringBetween(item, "</span>", "<a h"));

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appendix = StringUtils.substringBetween(item, "ref = \"", "\"><em>");
                String url = "https://www.eaiib.agh.edu.pl/" + appendix.replaceAll("amp;", "");
                Intent intent = new Intent(context, OpenNewsActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

}
