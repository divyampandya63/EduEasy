package tito.example.com.edueasy.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import tito.example.com.edueasy.Adapter.Courses_Adapter;
import tito.example.com.edueasy.Helper.Common;
import tito.example.com.edueasy.Interface.IversitySErvice;
import tito.example.com.edueasy.Interface.Udacity_Service;
import tito.example.com.edueasy.Modal.General.Final;
import tito.example.com.edueasy.Modal.udacity.CoursesItem;
import tito.example.com.edueasy.Modal.udacity.Response;
import tito.example.com.edueasy.R;

/**
 * Created by tito on 16/3/18.
 */

public class Courses extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<CoursesItem> udacity_response=new ArrayList<>();
    List<tito.example.com.edueasy.Modal.Iversity.CoursesItem> iversity_response=new ArrayList<>();
    List<Final> finalList=new ArrayList<>();
    Udacity_Service udacity_service;
   IversitySErvice iversitySErvice;
    public Courses() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_main, container, false);
            recyclerView = view.findViewById(R.id.recyclerview);
            layoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
           udacity_service=Common.getUdacityCourses();

           udacity_service.getUdacityService().enqueue(new Callback<Response>() {
               @Override
               public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                   udacity_response=response.body().getCourses();
                   for(int i=0;i<5;i++)
                   {
                       Final f=new Final(udacity_response.get(i).getProjectName(),udacity_response.get(i).getProjectDescription(),udacity_response.get(i).getImage(),udacity_response.get(i).getHomepage());
                       finalList.add(f);
                   }
               }

               @Override
               public void onFailure(Call<Response> call, Throwable t) {

               }
           });

        iversitySErvice=Common.getIversityCourses();
       iversitySErvice.getIversityService().enqueue(new Callback<tito.example.com.edueasy.Modal.Iversity.Response>() {
           @Override
           public void onResponse(Call<tito.example.com.edueasy.Modal.Iversity.Response> call, retrofit2.Response<tito.example.com.edueasy.Modal.Iversity.Response> response) {
               iversity_response=response.body().getCourses();
               for(int i=0;i<5;i++)
               {
                   Final f=new Final(iversity_response.get(i).getTitle(),iversity_response.get(i).getDescription(),iversity_response.get(i).getImage(),iversity_response.get(i).getUrl());
                   finalList.add(f);
               }
               Courses_Adapter courses_adapter=new Courses_Adapter(getActivity(),finalList);
               recyclerView.setAdapter(courses_adapter);
           }

           @Override
           public void onFailure(Call<tito.example.com.edueasy.Modal.Iversity.Response> call, Throwable t) {

           }
       });



        return view;
    }
}
