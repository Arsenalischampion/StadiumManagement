package com.example.po.stadiummanagement3.Fragment;

/**
 * Created by 13701 on 2017/11/25.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.po.stadiummanagement3.Activity.MainActivity;
import com.example.po.stadiummanagement3.R;
import com.example.po.stadiummanagement3.WebService.HttpService;

import java.io.IOException;
import java.util.concurrent.atomic.LongAdder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by 13701 on 2017/11/23.
 */

public class LoadingFragment extends Fragment {
    private ImageView imageView;
    private final String picUrl = "http://guolin.tech/api/bing_pic";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.loading_fragment,container,false);
        imageView = v.findViewById(R.id.background);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        loadPic();
        return v;
    }

    private void loadPic(){
        HttpService.sendOkHttpRequest(picUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try{
                    final String bingPic = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getActivity()).load(bingPic).into(imageView);
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }
}