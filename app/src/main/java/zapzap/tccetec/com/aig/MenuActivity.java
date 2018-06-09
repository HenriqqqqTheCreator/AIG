package zapzap.tccetec.com.aig;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import zapzap.tccetec.com.aig.Adapter.TabAdapter;
import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.fragment.PrincipalFragment;
import zapzap.tccetec.com.aig.helper.SlidingTabLayout;

public class MenuActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;

    PrincipalFragment principalFragment = new PrincipalFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_menu);

        Intent intent = getIntent();

        //Findview TOOLBAR
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("AIG");
        setSupportActionBar(toolbar);

        //Findview TabLayout
        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);

        //Configurar Sliding Tab
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.branco));

        //Configurar Adapter
        tabAdapter = new TabAdapter( getSupportFragmentManager() );
        viewPager.setAdapter( tabAdapter );

        slidingTabLayout.setViewPager( viewPager );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //item.getItemId(); //Identifica qual opção foi selecionada

        switch (item.getItemId()){
            case R.id.item_sair:

                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
                return true
                ;
        }
        return super.onOptionsItemSelected(item);
    }

}
