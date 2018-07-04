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

public class profile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile,container,false);


        return v;
    }
}
