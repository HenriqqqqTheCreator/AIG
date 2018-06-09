package zapzap.tccetec.com.aig.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import zapzap.tccetec.com.aig.fragment.PrincipalFragment;
import zapzap.tccetec.com.aig.fragment.SegundoFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"Temas", "Perfil"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new PrincipalFragment();
                break;
            case 1:
                fragment = new SegundoFragment();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
