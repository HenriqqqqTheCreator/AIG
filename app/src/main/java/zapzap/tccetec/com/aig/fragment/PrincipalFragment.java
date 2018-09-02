package zapzap.tccetec.com.aig.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.licao.Book;
import zapzap.tccetec.com.aig.licao.RecyclerViewAdapter;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment {

    private View viewV;
    private Context contextoPrincipal;
    private RecyclerView myrv;
    private RecyclerViewAdapter myAdapter;

    private List<Book> lstBook;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewV = inflater.inflate(R.layout.fragment_principal, container, false);

        contextoPrincipal = getActivity().getApplicationContext();

        lstBook = new ArrayList<>();
        lstBook.add(new Book("Iniciante", "Iniciante", "Iniciante", R.drawable.inicianteicon, R.color.iniciante));
        lstBook.add(new Book("Intermediario", "Intermediário", "Intermediário", R.drawable.intermediarioicon, R.color.intermediario));
        lstBook.add(new Book("Avançado", "Avançado", "Avançado", R.drawable.avancadoicon, R.color.avancado));
        lstBook.add(new Book("Prova Final", "Verdade", "Quiz", R.drawable.provacinza, R.color.avancado));

        myrv = viewV.findViewById(R.id.recyclerview_id);
        myAdapter = new RecyclerViewAdapter(contextoPrincipal, lstBook);
        myrv.setLayoutManager(new GridLayoutManager(contextoPrincipal, 2));
        myrv.setAdapter(myAdapter);

        SharedPreferences prefse = this.getActivity().getSharedPreferences(SegundoFragment.SHARED_SECONDFRAGPREF, MODE_PRIVATE);
        Boolean restoredOne = prefse.getBoolean("ta liberado", false);

        if(restoredOne == true){
            refazerMYRV();
        }

        return viewV;


    }

    public void refazerMYRV(){

        myrv.removeAllViews();

        lstBook = new ArrayList<>();
        lstBook.add(new Book("Iniciante", "Iniciante", "Iniciante", R.drawable.inicianteicon, R.color.iniciante));
        lstBook.add(new Book("Intermediario", "Intermediário", "Intermediário", R.drawable.intermediarioicon, R.color.intermediario));
        lstBook.add(new Book("Avançado", "Avançado", "Avançado", R.drawable.avancadoicon, R.color.avancado));
        lstBook.add(new Book("Prova Final", "Verdade", "Quiz", R.drawable.provaicon, R.color.avancado));

        myrv = viewV.findViewById(R.id.recyclerview_id);
        myAdapter = new RecyclerViewAdapter(contextoPrincipal, lstBook);

        myrv.setLayoutManager(new GridLayoutManager(contextoPrincipal, 2));
        myrv.setAdapter(myAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefse = this.getActivity().getSharedPreferences(SegundoFragment.SHARED_SECONDFRAGPREF, MODE_PRIVATE);
        Boolean restoredOne = prefse.getBoolean("ta liberado", false);

        if(restoredOne == true){
            refazerMYRV();
        }

    }


    public void onBackPressed() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


}
