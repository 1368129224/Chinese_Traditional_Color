package stu.zzc.chinese_traditional_color;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.toHexString;

public class ColorDetailActivity extends AppCompatActivity {

    private String name;
    private String cRGB;
    private int darkColor;
    private MyDatabaseHelper dbHelper;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_detail);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        dbHelper = new MyDatabaseHelper(this, "Colors.db", null, 1);
        View DetailView = findViewById(R.id.DetailView);
        View ColorDetail = findViewById(R.id.ColorDetail);
        TextView TVDColorNam = findViewById(R.id.TVDColorName);
        ImageView DIVFlower = findViewById(R.id.DIVFlower);
        TextView TVColorInfo = findViewById(R.id.TVColorInfo);
        ImageView IVShare = findViewById(R.id.IVShare);
        final ImageView IVLike = findViewById(R.id.IVLike);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        cRGB = bundle.getString("RGB");
        darkColor = bundle.getInt("darkColor");
        if (selectColor()){
            IVLike.setImageResource(R.drawable.ic_heart);
        }
        DetailView.setBackgroundColor(Color.parseColor("#" + cRGB));
        TVDColorNam.setText(name);
        TVDColorNam.setTextColor(darkColor);
        DIVFlower.setColorFilter(darkColor);
        IVShare.setColorFilter(darkColor);
        IVLike.setColorFilter(darkColor);
        TVColorInfo.setText("#" + cRGB.toUpperCase());
        TVColorInfo.setTextColor(darkColor);
        ColorDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData =ClipData.newPlainText(null, "#" + cRGB.toUpperCase());
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(ColorDetailActivity.this, "#" + cRGB.toUpperCase(), Toast.LENGTH_SHORT).show();
                mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                mVibrator.vibrate(30);
                return false;
            }
        });
        IVLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectColor()) {
                    deleteColor();
                    Toast.makeText(ColorDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    IVLike.setImageResource(R.drawable.ic_heart_broken);
                    ScaleAnimatorUtils.setScalse(IVLike);
                }
                else{
                    addColor();
                    Toast.makeText(ColorDetailActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                    IVLike.setImageResource(R.drawable.ic_heart);
                    ScaleAnimatorUtils.setScalse(IVLike);
                }
            }
        });
        IVShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, name + ":#" + cRGB + "\n@中国传统色");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "分享给朋友"));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private Boolean selectColor(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("select * from Colors where name == ?", new String[]{name});
        if (cursor.getCount() == 1){
            return true;
        }
        else {
            return false;
        }
    }

    private void deleteColor(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Colors", "name == ?", new String[]{name});
    }

    private void addColor(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("cRGB", cRGB);
        db.insert("Colors", null, values);
    }
}
