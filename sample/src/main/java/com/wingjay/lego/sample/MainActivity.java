package com.wingjay.lego.sample;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.wingjay.lego.ILegoViewHolder;
import com.wingjay.lego.LegoCache;
import com.wingjay.lego.LegoItem;
import com.wingjay.lego.LegoRecyclerAdapter;
import com.wingjay.lego.LegoRecyclerAdapter.OnLegoViewHolderListener;
import com.wingjay.lego.sample.bean.Cat;
import com.wingjay.lego.sample.bean.Dog;
import com.wingjay.lego.sample.viewholder.AppleViewHolder;
import com.wingjay.lego.sample.viewholder.CatViewHolder;
import com.wingjay.lego.sample.viewholder.DogViewHolder;
import com.wingjay.lego.sample.viewholder.OrangeViewHolder;

public class MainActivity extends AppCompatActivity {

    private LegoRecyclerAdapter adapter;
    private List<Object> magicData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LegoRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        displayFruit();
        //displayAnimal();
        //enableLegoCache(recyclerView);
        //handleViewHolderInstance();
    }

    private void displayFruit() {
        magicData.clear();

        magicData.add(LegoItem.create("orange", null));
        magicData.add(LegoItem.create("apple", null));

        adapter.swapData(magicData);
    }

    private void displayAnimal() {
        magicData.clear();

        magicData.add(new Cat());
        magicData.add(new Dog());

        adapter.swapData(magicData);
    }

    private void displayBothAnimalAndFruit() {
        magicData.clear();

        magicData.add(LegoItem.create("orange", null));
        magicData.add(LegoItem.create("apple", null));
        magicData.add(new Cat());
        magicData.add(new Dog());

        adapter.swapData(magicData);
    }

    private void enableLegoCache(RecyclerView recyclerView) {
        LegoCache legoCache =
            new LegoCache.Builder(recyclerView)
            .cache(OrangeViewHolder.class, 2)
            .cache(AppleViewHolder.class, 2)
            .build();
        adapter.setLegoCache(legoCache);
        displayFruit();
    }

    private void handleViewHolderInstance() {
        adapter.setOnLegoViewHolderListener(new OnLegoViewHolderListener() {
            @Override
            public void onCreate(@NonNull ILegoViewHolder viewHolder) {
                if (viewHolder instanceof AppleViewHolder) {
                    makeToast("Create AppleViewHolder");
                } else if (viewHolder instanceof OrangeViewHolder) {
                    makeToast("Create OrangeViewHolder");
                } else if (viewHolder instanceof CatViewHolder) {
                    makeToast("Create CatViewHolder");
                } else if (viewHolder instanceof DogViewHolder) {
                    makeToast("Create DogViewHolder");
                }
            }
        });
        displayFruit();
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
