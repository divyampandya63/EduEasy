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
import tito.example.com.edueasy.Adapter.BooksAdapter;
import tito.example.com.edueasy.Helper.Common;
import tito.example.com.edueasy.Interface.Book_Service;
import tito.example.com.edueasy.Modal.Books.BooksItem;
import tito.example.com.edueasy.Modal.Books.Response;
import tito.example.com.edueasy.R;

/**
 * Created by tito on 16/3/18.
 */

public class Books extends Fragment {
   RecyclerView recyclerView;
   RecyclerView.LayoutManager layoutManager;
Book_Service book_service;
List<BooksItem> responses=new ArrayList<>();

   public Books() {
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
         book_service=Common.getBooks();
          book_service.getBooksService().enqueue(new Callback<Response>() {
              @Override
              public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                  Log.d("error",response.raw()+"");
                  responses=response.body().getBooks();
                  BooksAdapter adapter=new BooksAdapter(getActivity(),responses);
                  recyclerView.setAdapter(adapter);
              }

              @Override
              public void onFailure(Call<Response> call, Throwable t) {
        Log.d("divyam",t.getMessage());
              }
          });
        return view;
    }
}
