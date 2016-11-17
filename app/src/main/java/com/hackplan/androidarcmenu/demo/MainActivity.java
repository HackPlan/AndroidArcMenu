package com.hackplan.androidarcmenu.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hackplan.androidarcmenu.ArcButton;
import com.hackplan.androidarcmenu.ArcMenu;

public class MainActivity extends AppCompatActivity implements ArcMenu.OnClickBtnListener, View.OnLongClickListener{
    private ArcMenu arcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        arcMenu = new ArcMenu.Builder(MainActivity.this)
                .addBtn(R.drawable.a, 0)
                .addBtn(R.drawable.r, 1)
                .addBtns(new ArcButton.Builder(R.drawable.w, 2))
                .setListener(MainActivity.this)
                .showOnLongClick(btn1)
                .showOnTouch(btn2)
                .hideOnTouchUp(false)
                .build();

        btn3.setOnLongClickListener(this);
    }

    @Override
    public void onClickArcMenu(View menuView, int id) {
        Toast.makeText(this, String.format("Click #%s", id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        arcMenu.showOn(v);
        return true;
    }
}