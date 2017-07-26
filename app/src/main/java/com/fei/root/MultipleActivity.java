package com.fei.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fei.root.multi.Cat;
import com.fei.root.multi.Dog;
import com.fei.root.multi.Person;
import com.fei.root.recapter.R;
import com.fei.root.recater.adapter.multi.ItemModule;
import com.fei.root.recater.adapter.multi.MultiAdapter;
import com.fei.root.recater.adapter.multi.WrapperItemModule;
import com.fei.root.recater.viewholder.CommonHolder;
import com.fei.root.viewbinder.Binder;
import com.fei.root.viewbinder.ViewBinder;

import java.util.ArrayList;
import java.util.List;

public class MultipleActivity extends AppCompatActivity {

    @Binder
    private RecyclerView recyclerView;

    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_multiple, null);
        setContentView(view);
        ViewBinder.bindViews(this, view);
        init();
    }

    private void init() {
        List<ItemModule> list = new ArrayList<>();
        list.add(new Dog());
        list.add(new Cat());
        list.add(new Person());
        list.add(new WrapperItemModule<String>("String", R.layout.item_00));
        list.add(new WrapperItemModule<Integer>(123, R.layout.item_01));
        list.add(new WrapperItemModule<Boolean>(true, R.layout.item_02));


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        multiAdapter = new MultiAdapter<ItemModule>(list) {
            @Override
            protected void convert(CommonHolder holder, ItemModule itemModule, int position) {
                if (itemModule instanceof Dog) {
                    holder.setText(R.id.text, ((Dog) itemModule).name);
                }
                if (itemModule instanceof Cat) {
                    holder.setText(R.id.text, ((Cat) itemModule).name);
                }
                if (itemModule instanceof Person) {
                    holder.setText(R.id.text, ((Person) itemModule).name);
                }
            }

            @Override
            protected boolean convert(CommonHolder holder, String data, int position) {
                holder.setText(R.id.text, data + "");
                return true;
            }

            @Override
            protected boolean convert(CommonHolder holder, Integer data, int position) {
                holder.setText(R.id.text, data + "");
                return true;
            }

            @Override
            protected boolean convert(CommonHolder holder, Boolean data, int position) {
                holder.setText(R.id.text, data + "");
                return true;
            }

           /* @Override
            protected boolean convert(CommonHolder holder, WrapperItemModule data, int position) {
                Object o = data.getContent();
                if (o instanceof Integer) {
                    holder.setText(R.id.text, (Integer) o + "");
                }
                if (o instanceof String) {
                    holder.setText(R.id.text, (String) o);
                }
                if (o instanceof Boolean) {
                    holder.setText(R.id.text, (Boolean) o + "");
                }
                return true;
            }*/
        };
        recyclerView.setAdapter(multiAdapter);
    }


}
