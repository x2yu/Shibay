package com.example.x2y.englishapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.Word;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    private List<Word> mWordList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View wordView;
        TextView wordName;
        TextView wordMean;
        public ViewHolder(View view){
            super(view);
            wordView = view;
            wordName = (TextView)view.findViewById(R.id.word_name);
            wordMean = (TextView)view.findViewById(R.id.word_mean);
        }
    }
    public WordAdapter(List<Word>wordList){
        mWordList = wordList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        //设置item点击事件
        holder.wordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                //获取被点击的对象
                Word word = mWordList.get(positon);
                Toast.makeText(v.getContext(),"点击了第"+positon+"个苹果",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Word word = mWordList.get(position);
       holder.wordName.setText(word.getName());
       holder.wordMean.setText(word.getMean());
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
