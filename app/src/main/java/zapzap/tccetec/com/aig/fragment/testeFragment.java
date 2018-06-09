package zapzap.tccetec.com.aig.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import zapzap.tccetec.com.aig.R;

public class testeFragment extends Fragment {

    private View viewV;

    public testeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewV = inflater.inflate(R.layout.fragment_teste, container, false);

        return viewV;
    }

}
