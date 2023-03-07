package com.example.recycle2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    //1.宣告等等要控制的Recyclerview
    private RecyclerView recyclerView;
    //3.1.宣告mLayoutManager
    private LinearLayoutManager mLayoutManager;
    //11.建立一些資料做使用

    private LinkedList<HashMap<String,String>> data;
    //14.1 宣告一個adapter
    private Myadapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.1連結
        recyclerView = findViewById(R.id.recyclerView);
        //2.設定大小(會透過linear layout manager進行內部配置)
        recyclerView.setHasFixedSize(true);

        //3+4使用一個linear layout manager
        //3.New一個linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //4.set(在recyclerview設定名為mLayoutManager的LayoutManager)
        recyclerView.setLayoutManager(mLayoutManager);


        doData();

        //14.new一個adapter 但如果之後要異動adapter會不方便，所以要在一開始宣告

        //recyclerView.setAdapter(new Myadapter()); 這行是在這邊new

        myAdapter = new Myadapter();
        recyclerView.setAdapter(myAdapter);

    }

    //11.1創建一個doData的class，然後在onCreate呼叫
    public void doData(){
        data = new LinkedList<>();

        //11.2創建資料的內容(做完後可以給getItemCount)
        for(int i=0;i<100;i++){
            HashMap<String,String> row = new HashMap<>();
            int random = (int)(Math.random()*100);
            row.put("title","Title"+random);
            row.put("date","Date"+random);
            data.add(row);
        }
    }

    //5.宣告一個Adapter的類別(繼承RecyclerView.Adapter泛型holder(中間的介面介接))
    //6.宣告一個實作方法(ctrl+i implement methods)
    //8.定義完holder才去泛型(此時需要實作裡面的方法，且是實作這個類別中的viewholder)
    private class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{

        //7.建立一個要泛型的viewholder
        class MyViewHolder extends RecyclerView.ViewHolder{
            //7.1屬性想控制的中間介接
            public View itemView;
            //10.宣告控制項給onblind使用(然後去holder內bind)
            public TextView title, date;
            public MyViewHolder(View v) {
                super(v);
                itemView = v;

                //10.1在此處設定(之後可以去onblind中bind資料)
                title = itemView.findViewById(R.id.item_title);
                date = itemView.findViewById(R.id.item_date);
            }
        }
        @NonNull
        @Override
        public Myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //9.創建一個新的view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            //請viewholer new出來介紹給holder認識
            MyViewHolder vh = new MyViewHolder(itemView);
            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, int position) {
            //13.請holder裡面的元素來set
            //13.1holder的title指的是item_title中的那個title，將此settext
            //13.2此時adapter設定完成，就可以去onCreate設定adapter
            holder.title.setText(data.get(position).get("title"));
            holder.date.setText(data.get(position).get("date"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("LinLi", "itemview: "+position);
                }
            });

            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("LinLi", "title: "+position);
                }
            });
        }

        @Override
        public int getItemCount() {
            //12.傳回data的size，之後就可以去設定onBind
            return data.size();
        }
    }


    public void test1(View view) {
        data.get(3).put("title","Brad");
        data.get(3).put("date","2023.03.06");
        myAdapter.notifyDataSetChanged();
    }

    public void test2(View view) {
        data.removeFirst();
        myAdapter.notifyDataSetChanged();
    }
}

//註解