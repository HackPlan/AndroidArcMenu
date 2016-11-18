package com.hackplan.androidarcmenu.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hackplan.androidarcmenu.ArcButton;
import com.hackplan.androidarcmenu.ArcMenu;

public class MainActivity extends AppCompatActivity implements ArcMenu.OnClickMenuListener,
        View.OnLongClickListener{
    private ArcMenu arcMenu, arcMenu2;
    public static final int ARC_MENU_ID_1 = 1;
    public static final int ARC_MENU_ID_2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        final Button btn4 = (Button) findViewById(R.id.btn4);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        Button menuBtn = new Button(this);
        menuBtn.setText("TEST");
        arcMenu = new ArcMenu.Builder(MainActivity.this)
                .setId(ARC_MENU_ID_1)
                .addBtn(R.drawable.a, 0)
                .addBtn(R.drawable.r, 1)
                .setListener(MainActivity.this)
                .showOnLongClick(btn1)
                .hideOnTouchUp(false)
                .build();

        arcMenu2 = new ArcMenu.Builder(MainActivity.this)
                .setId(ARC_MENU_ID_2)
                .addBtn(R.drawable.w, 6)
                .addBtns(new ArcButton.Builder(menuBtn, 2))
                .setListener(MainActivity.this)
                .showOnTouch(btn2)
                .hideOnTouchUp(true)
                .build();

        btn3.setOnLongClickListener(this);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arcMenu.showOn(v);
            }
        });


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        MyAdapter mAdapter = new MyAdapter(new String[]{"Pomotodo", "hackplan", "http://one.hackplan.com/", "Dacer"}, this, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClickArcMenu(ArcMenu arcMenu, int viewId) {
        Toast.makeText(this, String.format("Click #%s, arcMenu id: %s", viewId, arcMenu.getId()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        arcMenu.showOn(v);
        return true;
    }

}
