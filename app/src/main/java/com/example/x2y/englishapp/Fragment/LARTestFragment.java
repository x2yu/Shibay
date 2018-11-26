package com.example.x2y.englishapp.Fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.x2y.englishapp.ListeningAndRememberActivity;
import com.example.x2y.englishapp.R;
import com.example.x2y.englishapp.bean.JavaBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LARTestFragment extends Fragment  implements View.OnClickListener{
    View view;
    JavaBean javaBean;
    MediaPlayer mediaPlayer;
    TextView test;
    List<JavaBean> radomList =new ArrayList<>() ;
    List<JavaBean> copyJavaBeanList = new ArrayList<>();
    int radomListSize=3;
    CheckBox checkBox0;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    List<CheckBox> checkBoxes = new ArrayList<>();
    Button buttonPlayer;
    TextView showInfor;
    ListeningAndRememberActivity lara;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.lartest_fragment,container,false);
        javaBean= ListeningAndRememberActivity.currentJavaBean;
        lara=(ListeningAndRememberActivity)getActivity();
        copyJavaBeanList=(List<JavaBean>) deepCopy(lara.javaBeanslist);
        test= (TextView)view.findViewById(R.id.test);
        showInfor=(TextView)view.findViewById(R.id.show_infor);
        String showInforText=ListeningAndRememberFragment.parseJavabean(javaBean);
        showInfor.setText(showInforText);
        buttonPlayer= (Button)view.findViewById(R.id.player);
        checkBox0=(CheckBox) view.findViewById(R.id.check_0);
        checkBox1 =(CheckBox) view.findViewById(R.id.check_1);
        checkBox2 =(CheckBox) view.findViewById(R.id.check_2);
        checkBox3 =(CheckBox) view.findViewById(R.id.check_3);
        checkBoxes.add(checkBox0);
        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        CompoundButton.OnCheckedChangeListener cb = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    for (int i = 0; i < checkBoxes.size(); i++)
                    {
                        //不等于当前选中的就变成false
                        if (checkBoxes.get(i).getText().toString().equals(compoundButton.getText().toString()))
                        {

                            checkBoxes.get(i).setChecked(true);
                        }
                        else
                        checkBoxes.get(i).setChecked(false);
                    }

                }
        }



    };
        checkBox0.setOnCheckedChangeListener(cb);
        checkBox1.setOnCheckedChangeListener(cb);
        checkBox2.setOnCheckedChangeListener(cb);
        checkBox3.setOnCheckedChangeListener(cb);

        checkBox0.setOnClickListener(this);
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
                return view;

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.check_0:
                lara.checkedArray[lara.count4-1]=1;
                if(checkBox0.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                    showInfor.setVisibility(View.VISIBLE);
                else
                    showInfor.setVisibility(View.GONE);
                break;
            case R.id.check_1:
                lara.checkedArray[lara.count4-1]=2;
                if(checkBox1.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                    showInfor.setVisibility(View.VISIBLE);
                else
                    showInfor.setVisibility(View.GONE);
                break;
            case R.id.check_2:
                lara.checkedArray[lara.count4-1]=3;
                if(checkBox2.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                    showInfor.setVisibility(View.VISIBLE);
                else
                    showInfor.setVisibility(View.GONE);
                break;
            case R.id.check_3:
                lara.checkedArray[lara.count4-1]=4;
                if(checkBox3.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                    showInfor.setVisibility(View.VISIBLE);
                else
                    showInfor.setVisibility(View.GONE);
                break;
                default:
                    lara.checkedArray[lara.count4-1]=0;
                    break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        test.setText(javaBean.getBasic().getExplains().get(0));

        showOptions();
        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getActivity(),Uri.parse(javaBean.getSpeakUrl()));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void showOptions()
    {
        List<JavaBean> temp = deepCopy(copyJavaBeanList);
        Random random = new Random();
        int count4 = lara.count4-1;
        if(lara.LARTestFragmentJavaBeanListTemp.size()<=lara.count4)
        {

            for(int k=0;k<copyJavaBeanList.size();k++)
            {
                if(javaBean.getQuery().equals(copyJavaBeanList.get(k).getQuery()))
                {
                    copyJavaBeanList.remove(k);
                    break;
                }
            }
            for(int i=0;i<radomListSize;i++)
            {

                int target = random.nextInt(copyJavaBeanList.size());
                radomList.add(copyJavaBeanList.get(target));
                copyJavaBeanList.remove(target);

            }
            radomList.add(javaBean);
            Collections.shuffle(radomList);
            lara.LARTestFragmentJavaBeanListTemp.add(radomList);

        }
        radomList=lara.LARTestFragmentJavaBeanListTemp.get(count4);
        for(int j=0;j<radomList.size();j++)
        {
            checkBoxes.get(j).setText(radomList.get(j).getBasic().getExplains().get(0));
        }
            if(lara.checkedArray[count4]!=0)
            {
                switch (lara.checkedArray[count4]){
                    case 1:
                    checkBox0.setChecked(true);
                        if(checkBox0.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                            showInfor.setVisibility(View.VISIBLE);
                    break;
                    case 2:
                        checkBox1.setChecked(true);
                        if(checkBox1.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                            showInfor.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        checkBox2.setChecked(true);
                        if(checkBox2.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                            showInfor.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        checkBox3.setChecked(true);
                        if(checkBox3.getText().toString().equals(javaBean.getBasic().getExplains().get(0)))
                            showInfor.setVisibility(View.VISIBLE);
                        break;
                        default:
                            break;
                }
            }




    }
    //解析内容出来
    public String parseJavabean()
    {
        StringBuffer buf = new StringBuffer();
        buf.append(javaBean.getQuery()+":\nbasic:\n美式音标:"+javaBean.getBasic().getUsphonetic()+"\n英式音标:"+javaBean.getBasic().getUkphonetic()+"\n解释：\n");
        for (int i=0;i<javaBean.getBasic().getExplains().size();i++)
        {
            buf.append(javaBean.getBasic().getExplains().get(i)+"\n  ");
        }
        buf.append("网络词义:\n");
        for(int j=0;j<javaBean.getWeb().size();j++)
        {
            buf.append(javaBean.getWeb().get(j).getKey()+":  "+javaBean.getWeb().get(j).getValue()+"\n");
        }
        return buf.toString();
    }
    public static <T> List<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
