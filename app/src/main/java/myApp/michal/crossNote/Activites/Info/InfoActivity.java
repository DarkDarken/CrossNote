package myApp.michal.crossNote.Activites.Info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;

public class InfoActivity extends BaseActivity {

    private DrawerLayout drawer;
    private DbPrHelper dbPrHelper;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolbarAndWindow(toolbar, getString(R.string.title_activity_info));
        initGUI();
        setToggle(drawer, toolbar);
        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        dbPrHelper = new DbPrHelper(this);
        dbHelper = new DbHelper(this);
        drawer = findViewById(R.id.drawer_layout);
    }

    public void sendEmail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse(getString(R.string.email_address)));
        startActivity(Intent.createChooser(emailIntent, "Send email via..."));
    }
}
