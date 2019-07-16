package stu.zzc.chinese_traditional_color;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LikeListActivity extends AppCompatActivity {

    private List<MyColor> myColorList = new ArrayList<>();
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_list);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        flashColorList();
        flashRecyclerView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.like).setVisible(false);
        menu.findItem(R.id.about).setVisible(false);
        menu.findItem(R.id.searchView).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like:
                Toast.makeText(this, "You clicked Like", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "You clicked About", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                this.finish();
                break;
            default:
        }
        return true;
    }

    private void flashColorList(){
        myColorList.clear();
        dbHelper = new MyDatabaseHelper(this, "Colors.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Colors",null,null,null,null,null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String cRGB = cursor.getString(cursor.getColumnIndex("cRGB"));
                MyColor goods = new MyColor(name, cRGB);
                myColorList.add(goods);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void flashRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.ListRView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        ColorAdapter adapter = new ColorAdapter(myColorList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        flashColorList();
        flashRecyclerView();
    }
}
