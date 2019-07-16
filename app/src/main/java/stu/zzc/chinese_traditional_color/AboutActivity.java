package stu.zzc.chinese_traditional_color;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView author = findViewById(R.id.author);
        TextView detail = findViewById(R.id.detail);
        ImageView cola = findViewById(R.id.cola);
        author.setText(Html.fromHtml("开发者：<a href='https://github.com/1368129224'>zooter</a>"));
        author.setMovementMethod(LinkMovementMethod.getInstance());
        detail.setText(Html.fromHtml("颜色来源：<a href='http://zhongguose.com/'>http://zhongguose.com/</a>"));
        detail.setMovementMethod(LinkMovementMethod.getInstance());

        cola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDonateDialog(AboutActivity.this);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.searchView).setVisible(false);
        menu.findItem(R.id.about).setVisible(false);
        menu.findItem(R.id.like).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
        }
        return true;
    }
}
