package com.example.x2y.englishapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x2y.englishapp.ListeningAndRememberActivity;
import com.example.x2y.englishapp.R;

public class reviewFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_layout,container,false);
        TextView listeningAndRemenber = (TextView)view.findViewById(R.id.listening_remember);
        listeningAndRemenber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ListeningAndRememberActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
