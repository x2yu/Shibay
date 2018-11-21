package com.example.x2y.englishapp.schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.x2y.englishapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ScheduleModify extends RecyclerView.OnScrollListener{
    public static final  int itemLayout = R.layout.study_schedule_modify_layout;
    public static final int itemBlanks = 3;

    private String scdelueDateString = null;
    private TextView scheduleDateTextView = null;
    private int needDays = 0;
    private int wordCount = 0;
    private Book book = null;
    private Context context = null;
    private ScheduleModifyAdapter scheduleModifyAdapter = null;
    private RecyclerView recyclerView = null;
    private int[] wordCountsArray = new int[]{5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 60, 70, 80, 90, 100, 125, 150, 175, 200, 250, 300, 350, 400, 450, 500, 600, 700, 800, 900, 1000};;

    private boolean isScrolled = false;


    public ScheduleModify(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
        this.recyclerView.addOnScrollListener(this);
    }

    public void setScheduleDateTextView(TextView scheduleDateTextView){
        this.scheduleDateTextView = scheduleDateTextView;
    }

    public void setBook(Book book){
        this.book = book;

        scheduleModifyAdapter  = new ScheduleModifyAdapter();
        recyclerView.setAdapter(scheduleModifyAdapter);
    }

    public void saveBookSchedule(){
        /**
         *
         */
    }

    private void scrollItem(){
        int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        int movePosition = 0;
        int top = recyclerView.getChildAt(movePosition).getTop();
        recyclerView.smoothScrollBy(0, top);

        if(firstItem < scheduleModifyAdapter.getItemCount() - itemBlanks - itemBlanks){
            wordCount = wordCountsArray[firstItem];
            needDays = (int) Math.ceil(book.getWorldCount() / (double)wordCount);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            calendar.add(Calendar.DAY_OF_MONTH, needDays);

            scdelueDateString = new SimpleDateFormat("yyyy年MM月dd日").format(calendar.getTime());
            scheduleDateTextView.setText("预计完成时间：" + scdelueDateString);
        }

    }


    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState){
            case RecyclerView.SCROLL_STATE_IDLE:
                scrollItem();
                isScrolled = false;
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                isScrolled = true;
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                isScrolled = true;
                break;
        }
    }

    public class ScheduleModifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private int startIndex = itemBlanks;
        private int endIndex = wordCountsArray.length + startIndex;
        private int itemCount = endIndex + itemBlanks;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(itemLayout, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            ViewHolder innerViewHolder = (ViewHolder)viewHolder;

            if(i >= startIndex && i < endIndex){
                int innerWordCount = wordCountsArray[i - startIndex];
                int innerDays = book.getWorldCount() / innerWordCount;

                if(innerDays * innerWordCount < book.getWorldCount()){
                    innerDays++;
                }
                innerViewHolder.wordCount.setText(innerWordCount + "");
                innerViewHolder.days.setText(innerDays + "");
            }else{
                innerViewHolder.wordCount.setText("");
                innerViewHolder.days.setText("");
            }

        }

        @Override
        public int getItemCount() {
            return itemCount;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView wordCount = null;
            TextView days = null;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                wordCount = (TextView)itemView.findViewById(R.id.schedule_modify_wordCount);
                days = (TextView)itemView.findViewById(R.id.schedule_modify_days);
            }
        }
    }
}
