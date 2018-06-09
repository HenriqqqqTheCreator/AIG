package zapzap.tccetec.com.aig.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.licao.Book;
import zapzap.tccetec.com.aig.licao.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment {

    private View viewV;
    private Context contextoPrincipal;

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
        lstBook.add(new Book("Iniciante", "Iniciante", "Iniciante", R.drawable.cardiniciante, R.color.iniciante));
        lstBook.add(new Book("Intermediario", "Intermediário", "Intermediário", R.drawable.cardintermediario, R.color.intermediario));
        lstBook.add(new Book("Avançado", "Avançado", "Avançado", R.drawable.cardavancado, R.color.avancado));


        RecyclerView myrv = viewV.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(contextoPrincipal, lstBook);
        myrv.setLayoutManager(new GridLayoutManager(contextoPrincipal, 1));
        myrv.setAdapter(myAdapter);

        return viewV;
    }

}
