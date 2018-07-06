package boat.golden.outlife.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import boat.golden.outlife.R;

/**
 * Created by Vipin soni on 04-07-2018.
 */

public class profile extends Fragment {


    Button interest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile,container,false);
        interest=v.findViewById(R.id.interestbtn);
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] multiChoiceItems = {"Mountains","Oceans","Beach","Dark","Bla bla"};
                boolean[] checkedItems = {true, false, false, false, false};
                new AlertDialog.Builder(getContext())
                        .setTitle("Select one")
                        .setMultiChoiceItems(multiChoiceItems, checkedItems, null)
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        return v;
    }
}
