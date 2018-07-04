package boat.golden.outlife.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import boat.golden.outlife.R;

/**
 * Created by Vipin soni on 04-07-2018.
 */

public class home_page extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.home_page,container,false);


        return v;
    }
}
