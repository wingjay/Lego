# Lego
A powerful Android library for building complex RecyclerView. Building a list is as simple as playing Lego game. 

List&lt;Model> =>Lego=> List&lt;View>. 像搭积木一样构建你的RecyclerView列表。

[中文介绍](https://github.com/wingjay/Lego/blob/master/README_CN.md)

# What Lego does
When you need to build a RecyclerView which contains `different types of ViewHolder`, you may always need to maintain a huge Adapter and calculate which type should be displayed on each position.

Now, forget those different types and viewTypeOfPosition() calculation in your terriable Adapter. 

When using `Lego`, you just need to do:
```
1. Build a empty List<Object>;
2. Put all your data into this List<Object>;
3. Take this List<Object> to RecyclerView. 
```

It's done! RecyclerView will auto find the ViewHolder based on List<Object> and display.

## How to use Lego
For example, we have three viewHolders to display in a RecyclerView: `AppleViewHolder`, `OrangeViewHolder`, `BananaViewHolder`. Their own data class are `Apple`, `Orange`, `Banana`.

You have two ways to build with `Lego`.

## 1. Use String Id to identify each ViewHolder
```
@LegoViewHolder(id = "apple_view_holder")
class AppleViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) { ... }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) { ... }
}

@LegoViewHolder(id = "orange_view_holder")
class OrangeViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) { ... }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) { ... }
}

@LegoViewHolder(id = "banana_view_holder")
class BananaViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) { ... }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) { ... }
}
```

That's it! In your RecyclerView, you can do as below:

```
RecyclerView recyclerView = findViewById(R.id.recycler_view);

// 1. create a LegoRecyclerAdapter
LegoRecyclerAdapter adapter = new LegoRecyclerAdapter();

// 2. create a data list of Object
List<Object> data = new ArrayList<>();

// 3. add LegoItem into data
data.add(LegoItem.create("apple_view_holder"));
data.add(LegoItem.create("orange_view_holder"));
data.add(LegoItem.create("banana_view_holder"));

// 4. It's done
adapter.swapData(data);
recyclerView.setAdapter(adapter)
```

Run this code, the RecyclerView will show these three viewHolders, try it or [run our sample](https://github.com/wingjay/Lego/tree/master/sample)!

## 2. Simpler Way: use Data object to identify each ViewHolder
```
@LegoViewHolder(bean = Apple.class)
class AppleViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) { ... }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) { 
      Apple apple = (Apple) data;
      ...
    }
}

@LegoViewHolder(bean = Orange.class)
class OrangeViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) { ... }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) { 
      Orange orange = (Orange) data;
      ...
    }
}

@LegoViewHolder(bean = Banana.class)
class BananaViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) { ... }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) {   
      Banana orange = (Banana) data;
      ...
    }
}
```

In your RecyclerView, do as below:
```
RecyclerView recyclerView = findViewById(R.id.recycler_view);

// 1. create a LegoRecyclerAdapter
LegoRecyclerAdapter adapter = new LegoRecyclerAdapter();

// 2. create a data list of Object
List<Object> data = new ArrayList<>();

// 3. add LegoItem into data
data.add(new Apple());
data.add(new Orange());
data.add(new Banana());

// 4. It's done
adapter.swapData(data);
recyclerView.setAdapter(adapter)
```

Bingo! This will display same three ViewHolders as above, but this way is easier to use.

## Get LegoViewHolder instance
```
adapter.setOnLegoViewHolderListener(new OnLegoViewHolderListener() {
    @Override
    public void onCreate(@NonNull ILegoViewHolder viewHolder) {
        if (viewHolder instanceof AppleViewHolder) {
            makeToast("Create AppleViewHolder");
        } else if (viewHolder instanceof OrangeViewHolder) {
            makeToast("Create OrangeViewHolder");
        }
    }
});
```

## ViewHolder cache
If you know your page need at least 2 AppleViewHolder and 3 OrangeViewHolder, you can tell Lego to generate ViewHolder instance and inflate. When data(maybe from server) arrives, you can display immediately and no need to do the ViewHolder Inflate.
```
LegoCache legoCache =
    new LegoCache.Builder(recyclerView)
    .cache(OrangeViewHolder.class, 2)
    .cache(AppleViewHolder.class, 2)
    .build();
adapter.setLegoCache(legoCache);
```


# Sample
Here is our [sample code](https://github.com/wingjay/Lego/tree/master/sample) you can try.

# Install
```
implementation 'com.wingjay.lego:library:0.9.2'
annotationProcessor 'com.wingjay.lego:lego-processor:0.9.2'
```

if you use Kotlin, use as below
```
implementation 'com.wingjay.lego:library:0.9.2'
kapt 'com.wingjay.lego:lego-processor:0.9.2'
```

# Proguard
```
-keep @interface com.wingjay.lego.LegoBean
-keep @interface com.wingjay.lego.LegoViewHolder

-keep @com.wingjay.lego.LegoBean class *
-keepclassmembers class * {
    @com.wingjay.lego.LegoBean *;
}
-keep @com.wingjay.lego.LegoViewHolder class *
-keepclassmembers class * {
    @com.wingjay.lego.LegoViewHolder *;
}
```