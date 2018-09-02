package zapzap.tccetec.com.aig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zapzap.tccetec.com.aig.Adapter.TabAdapter;
import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.fragment.PrincipalFragment;
import zapzap.tccetec.com.aig.fragment.SegundoFragment;
import zapzap.tccetec.com.aig.helper.SessionManager;
import zapzap.tccetec.com.aig.helper.SlidingTabLayout;

import static zapzap.tccetec.com.aig.app.AppController.TAG;

public class MenuActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private SessionManager sessionManager;
    public static final String PEGARPONTOS_URL = "http://192.168.1.238/delaroy/pegar_pontuacao.php";

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

        pegarPontossql();



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

                sessionManager = new SessionManager(MenuActivity.this);
                sessionManager.setLogin(false);
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
                return true
                ;
        }
        return super.onOptionsItemSelected(item);
    }


    private void pegarPontossql() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, PEGARPONTOS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray jsonArray = null;

                        try {

                            jsonArray = response.getJSONArray("tb_pontos");

                            for(int i=0; i<jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);
                                String pontos = obj.getString("Pontos");
                                String email = obj.getString("Email");
                                Toast.makeText(MenuActivity.this, "VOCE TEM " + pontos + "\nSeu email: " + email, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MenuActivity.this);
        requestQueue.add(req);
    }

}
