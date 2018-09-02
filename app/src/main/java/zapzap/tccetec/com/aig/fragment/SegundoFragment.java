package zapzap.tccetec.com.aig.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zapzap.tccetec.com.aig.CadastrarActivity;
import zapzap.tccetec.com.aig.LoginActivity;
import zapzap.tccetec.com.aig.MenuActivity;
import zapzap.tccetec.com.aig.ProvaFinalHEHEActivity;
import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.helper.SessionManager;
import zapzap.tccetec.com.aig.licao.QuestActivity;

import static android.content.Context.MODE_PRIVATE;
import static zapzap.tccetec.com.aig.app.AppController.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SegundoFragment extends Fragment {

    private TextView text_view_cardiniciante;
    private TextView text_view_cardintermediario;
    private TextView text_view_cardavancado;
    private String emailPontos;
    private TextView tvIni, tvInter, tvAvan;
    private ImageView imageView_iniciante;
    private ImageView imageView_intermediario;
    private ImageView imageView_avancado;

    private String restoredOne;
    private String restoredTwo;
    private String restoredThree;

    public static final String SHARED_SECONDFRAGPREF = "sharedFragment";
    public static final String KEY_GET_SCOREONE = "scoreOneF";
    public static final String KEY_GET_SCORETWO = "scoreTwoF";
    public static final String KEY_GET_SCORETHREE = "scoreThreeF";

    public SegundoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences prefsLogin = this.getActivity().getSharedPreferences(LoginActivity.SHARED_LOGIN_EMAIL, MODE_PRIVATE);
        emailPontos = prefsLogin.getString(LoginActivity.KEY_LOGINAC, "nada");

        parseJson(emailPontos);
        return inflater.inflate(R.layout.fragment_segundo, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences(SHARED_SECONDFRAGPREF, MODE_PRIVATE).edit();
        editor.putBoolean("ta liberado", false);
        editor.apply();

        SharedPreferences prefsLogin = this.getActivity().getSharedPreferences(LoginActivity.SHARED_LOGIN_EMAIL, MODE_PRIVATE);
        emailPontos = prefsLogin.getString(LoginActivity.KEY_LOGINAC, "nada");

        SharedPreferences prefse = this.getActivity().getSharedPreferences(SHARED_SECONDFRAGPREF, MODE_PRIVATE);
        String restoredOne = prefse.getString("goldenONE", "b");
        String restoredTwo = prefse.getString("goldenTWO", "b");
        String restoredThree = prefse.getString("goldenTHREE", "b");

        imageView_iniciante = view.findViewById(R.id.imageview_iniciante);
        imageView_intermediario = view.findViewById(R.id.imageview_intermediario);
        imageView_avancado = view.findViewById(R.id.imageview_avancado);

        text_view_cardiniciante = view.findViewById(R.id.text_view_pontos_iniciante);
        text_view_cardintermediario = view.findViewById(R.id.text_view_pontos_intermediario);
        text_view_cardavancado = view.findViewById(R.id.text_view_pontos_avancado);

        tvIni = view.findViewById(R.id.text_view_pontos_iniciante);
        tvInter = view.findViewById(R.id.text_view_pontos_intermediario);
        tvAvan = view.findViewById(R.id.text_view_pontos_avancado);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        int data = prefs.getInt(QuestActivity.KEY_SAVED_SCORE, 0); //no id: default value

        parseJson(emailPontos);/*
        goldenAgeA(restoredOne);
        goldenAgeB(restoredTwo);
        goldenAgeC(restoredThree);*/
    }

    public void parseJson(String email) {

        final String mEmail = email;

        String url = "http://192.168.1.238/delaroy/json_get_data.php";

//        String url = "http://10.0.2.2/delaroy/json_get_data.php";
        //String url = "http://aigdevelopment.000webhostapp.com/json_get_data.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("server_response");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject server_response = jsonArray.getJSONObject(i);

                        String tb_email = server_response.getString("tb_email");
                        String tb_pontos = server_response.getString("tb_pontos");
                        String tb_pontosinter = server_response.getString("tb_pontosinter");
                        String tb_pontosavan = server_response.getString("tb_pontosavan");

                        if (tb_email.equals(mEmail)) {
                            if (tb_pontos != null) {
                                tvIni.setText(tb_pontos + " Pontos");
                            } else {
                                tvIni.setText("0 Pontos");
                            }
                            if (tb_pontosinter != null) {
                                tvInter.setText(tb_pontosinter + " Pontos");
                            } else {
                                tvInter.setText("0 Pontos");
                            }
                            if (tb_pontosavan != null) {
                                tvAvan.setText(tb_pontosavan + " Pontos");
                            } else {
                                tvAvan.setText("0 Pontos");
                            }

                            SharedPreferences.Editor editor = getActivity().getSharedPreferences(SHARED_SECONDFRAGPREF, MODE_PRIVATE).edit();
                            editor.putString(KEY_GET_SCOREONE, tb_pontos);
                            editor.putString(KEY_GET_SCORETWO, tb_pontosinter);
                            editor.putString(KEY_GET_SCORETHREE, tb_pontosavan);
                            editor.apply();

                            goldenAgeA(tb_pontos);
                            goldenAgeB(tb_pontosinter);
                            goldenAgeC(tb_pontosavan);
                            goldenAgeHasCome(tb_pontos, tb_pontosinter, tb_pontosavan);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(request);

    }

    private void goldenAgeA(String kibeA) {

        if (kibeA != null) {
            if (kibeA.equals("5")) {
                imageView_iniciante.setImageResource(R.drawable.inicianteicongold);
            } else {
                imageView_iniciante.setImageResource(R.drawable.inicianteicon);
            }
        }

    }

    private void goldenAgeB(String kibeB) {

        if (kibeB != null){
            if (kibeB.equals("5")) {
                imageView_intermediario.setImageResource(R.drawable.intermediarioicongold);
            }else {
                imageView_intermediario.setImageResource(R.drawable.intermediarioicon);
            }
        }

    }

    private void goldenAgeC(String kibeC) {

        if (kibeC != null) {
            if (kibeC.equals("5")) {
                imageView_avancado.setImageResource(R.drawable.avancadoicongold);
            } else{
                imageView_avancado.setImageResource(R.drawable.avancadoicon);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        parseJson(emailPontos);
    }

    public void goldenAgeHasCome(String a, String b, String c){

        if(a.equals("5") && b.equals("5") && c.equals("5")){
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(SHARED_SECONDFRAGPREF, MODE_PRIVATE).edit();
            editor.putBoolean("ta liberado", true);
            editor.apply();
        }
    }

}
