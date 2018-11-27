package com.example.x2y.englishapp.Fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.ListeningAndRememberActivity;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.JavaBean;

import java.io.IOException;
import java.util.zip.Inflater;

public class ListeningAndRememberFragment extends Fragment {
    View view;
    TextView test;
    JavaBean javaBean;
    MediaPlayer mediaPlayer;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_wordinfor,container,false);
        test=(TextView)view.findViewById(R.id.test);
        javaBean = ListeningAndRememberActivity.currentJavaBean;
        test.setText(parseJavabean(javaBean));
        button=(Button)view.findViewById(R.id.player);
        return view;
    }
    public static String parseJavabean(JavaBean javaBean)
    {
        StringBuffer buf = new StringBuffer();
        buf.append(javaBean.getQuery()+":\nbasic:\n美式音标:"+javaBean.getBasic().getUsphonetic()+"\n英式音标:"+javaBean.getBasic().getUkphonetic()+"\n解释：\n");
        for (int i=0;i<javaBean.getBasic().getExplains().size();i++)
        {
            buf.append(javaBean.getBasic().getExplains().get(i)+"\n");
        }
        buf.append("网络词义:\n");
        for(int j=0;j<javaBean.getWeb().size();j++)
        {
            buf.append(javaBean.getWeb().get(j).getKey()+":  "+javaBean.getWeb().get(j).getValue()+"\n");
        }
          return buf.toString();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = new MediaPlayer();
                StringBuilder builder = new StringBuilder(javaBean.getSpeakUrl());
                builder.insert(4,"s");

                try {

                    mediaPlayer.setDataSource(getActivity(),Uri.parse(builder.toString()));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
