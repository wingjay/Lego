package com.wingjay.lego.sample;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wingjay.lego.LegoItem;
import com.wingjay.lego.LegoRecyclerAdapter;
import com.wingjay.lego.sample.bean.Cat;
import com.wingjay.lego.sample.bean.Dog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LegoRecyclerAdapter adapter = new LegoRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        List<Object> magicData = new ArrayList<>();

        // animal data
        magicData.add(new Cat());
        magicData.add(new Dog());

        // fruit data
        magicData.add(LegoItem.create("orange", null));
        magicData.add(LegoItem.create("apple", null));

        adapter.swapData(magicData);
    }
}
