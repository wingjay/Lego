# Lego
一个强大的针对多Type RecyclerView构建的库，像搭积木一样构建你的RecyclerView列表。 


# Lego做了什么
当前的移动开发里，有越来越多的场景下需要构建多Type列表（多种ViewHolder），无需维护一个复杂Adapter、在每个position去计算ViewType，基于Lego你只要做三件事就可以搭建：

```
1. 创建一个空的数据列表List<Object>；
2. 把你要显示的数据源都放进去；
3. 把这个数据列表传给RecyclerView
```

结束了! Lego会自动帮助RecyclerView找到每个数据对应的ViewHolder类型并显示出来。

## 如何使用Lego
假设我们希望在RecyclerView显示三种ViewHolder: `AppleViewHolder`, `OrangeViewHolder`, `BananaViewHolder`. 他们各自对应的数据是：`Apple`, `Orange`, `Banana`.

Lego提供给你两种方式：

## 1. 使用String Id来标识每一个ViewHolder
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

就这样! 然后按下面方式添加到RecyclerView里：

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

运行这段代码，就可以看到RecyclerView里显示了三种不同类型的ViewHolder。[试试sample!](https://github.com/wingjay/Lego/tree/master/sample)

## 2. 更简单的方式：使用数据源本身来标识ViewHolder
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

Bingo! 这个能达到和上面一样的效果。


## 运行时获取LegoViewHolder实例
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

## ViewHolder缓存
如果你知道你的页面至少会显示2个OrangeViewHolder和2个AppleViewHolder，你可以提前告诉Lego，他会帮你立即创建并渲染好这几个ViewHolder，等你的数据回来（比如服务端返回的数据）后，你就无需再去初始化ViewHolder，而是可以立即显示出来。
```
LegoCache legoCache =
    new LegoCache.Builder(recyclerView)
    .cache(OrangeViewHolder.class, 2)
    .cache(AppleViewHolder.class, 2)
    .build();
adapter.setLegoCache(legoCache);
```

这个主要用在页面第一次显示时的加速，利用网络请求的间隙提前预判并渲染好ViewHolder。

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
