package com.example.x2y.englishapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.CardMode;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context mContext;
    private List<CardMode> mCardList;

    public CardAdapter(Context mContext, List<CardMode> mCardList) {
        this.mContext = mContext;
        this.mCardList = mCardList;
    }

    @Override
    public int getCount() {
        return mCardList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.learningcard_item, parent, false);
            holder = new ViewHolder();
            holder.mCardImageView = (ImageView) convertView.findViewById(R.id.helloText);
            holder.mCardWord = (TextView)convertView.findViewById(R.id.learn_word);
            holder.mCardWordMean = (TextView)convertView.findViewById(R.id.learn_wordmean);
            holder.mCardSentence = (TextView)convertView.findViewById(R.id.learn_sentence);
            holder.mCardName = (TextView) convertView.findViewById(R.id.card_name);
            holder.mCardYear = (TextView) convertView.findViewById(R.id.card_year);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext)
                .load(mCardList.get(position).getImages().get(0))
                .into(holder.mCardImageView);
        holder.mCardWord.setText(mCardList.get(position).getWord());
        holder.mCardWordMean.setText(mCardList.get(position).getWordMean());
        holder.mCardSentence.setText(mCardList.get(position).getSentence());
        holder.mCardName.setText(mCardList.get(position).getName());
        holder.mCardYear.setText(String.valueOf(mCardList.get(position).getYear()));
        return convertView;
    }

    class ViewHolder {
        TextView mCardName;
        TextView mCardYear;
        TextView mCardWord;
        TextView mCardWordMean;
        TextView mCardSentence;
        ImageView mCardImageView;
    }
}
