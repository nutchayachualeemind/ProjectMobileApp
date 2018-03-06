package project.appgame.lettryquiz.Functions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.appgame.lettryquiz.R;

public class Score extends BaseAdapter {

    private Context context;
    private List<Rank> lstRanking;

    public Score(Context context, List<Rank> lstRanking) {
        this.context = context;
        this.lstRanking = lstRanking;
    }

    @Override
    public int getCount() {
        return lstRanking.size();
    }

    @Override
    public Object getItem(int position) {
        return lstRanking.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row, null);

        ImageView imgTop = (ImageView)view.findViewById(R.id.imgTop);
        TextView txtTop = (TextView)view.findViewById(R.id.txtTop);

        if(position == 0 ) {
            imgTop.setImageResource(R.drawable.top1);
        } else if(position == 1) {
            imgTop.setImageResource(R.drawable.top2);
        } else {
            imgTop.setImageResource(R.drawable.top3);
        }
        txtTop.setText(String.format("%d",lstRanking.get(position).getScore()));
        return view;
    }
}

