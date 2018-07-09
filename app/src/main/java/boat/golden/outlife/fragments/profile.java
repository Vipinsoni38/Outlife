package boat.golden.outlife.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import boat.golden.outlife.MainActivity;
import boat.golden.outlife.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Vipin soni on 04-07-2018.
 */

public class profile extends Fragment {


    Button interest,update;
    FirebaseDatabase database;
    DatabaseReference reference;
    Spinner statespinner,cityspinner;
    String UID,bio_text;
    String[] objects={"Rajasthan","UP","Bla bla"};

    ImageView nameedit,bioedit;
    TextView profile_name,bio,place,profession;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile,container,false);
        interest=v.findViewById(R.id.interestbtn);
        update=v.findViewById(R.id.update);
        nameedit=v.findViewById(R.id.nameedit);
        bioedit=v.findViewById(R.id.bioedit);
        profile_name=v.findViewById(R.id.profile_name);
        bio=v.findViewById(R.id.bio);
        database=FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        UID=sharedPreferences.getString("UID","Null");
        if (UID.equals("Null"))
        {
            UID=FirebaseAuth.getInstance().getUid();
        }

        reference=database.getReference("profile/"+UID);
        SharedPreferences sharedPreferences2=getContext().getSharedPreferences("user_data",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor2=sharedPreferences2.edit();
        profile_name.setText(sharedPreferences2.getString("name","User Name"));
        bio_text=sharedPreferences2.getString("bio","HI this is my bio");
        bio.setText(bio_text);
        place=v.findViewById(R.id.place);
        place.setText(sharedPreferences2.getString("place","-not mentioned-"));
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder placeselector=new AlertDialog.Builder(getContext());
                View dialogview=getActivity().getLayoutInflater().inflate(R.layout.dialog_place,null);
                placeselector.setView(dialogview);
                placeselector.setTitle("Select City")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    reference.child("place").setValue("something Something").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(),"Place Edit Complete",Toast.LENGTH_SHORT).show();
                        }
                    });


                    }
                })
                .setNegativeButton("Cancel",null);
                placeselector.show();


            }
        });



        profession=v.findViewById(R.id.pro);
        profession.setText(sharedPreferences2.getString("pro","-not mentioned-"));
        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder proselector=new AlertDialog.Builder(getContext());
                View dialogview=getActivity().getLayoutInflater().inflate(R.layout.dialog_place,null);
                proselector.setView(dialogview);
                proselector.setTitle("Select Profession")
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                reference.child("pro").setValue("some bla bla").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(),"Profession Edit Complete",Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        })
                        .setNegativeButton("Cancel",null);
                proselector.show();


            }
        });









        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] multiChoiceItems = {"Mountains","Oceans","Beach","Dark","Bla bla"};
                boolean[] checkedItems = {true, false, false, false, false};
                new AlertDialog.Builder(getContext())
                        .setTitle("Select one")
                        .setMultiChoiceItems(multiChoiceItems, checkedItems, null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reference.child("interest").setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(getContext(),"Interest Edit Complete",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });



        nameedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext() );
                alertDialog.setTitle("Edit Your Name");

                final EditText input = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);


                alertDialog.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            reference.child("name").setValue(input.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                    Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();
                                        editor2.putString("name",input.getText().toString());
                                        editor2.commit();
                                        profile_name.setText(input.getText().toString());
                                    }
                                    else
                                        Toast.makeText(getContext(),"Error Updating",Toast.LENGTH_SHORT).show();

                                }



                            });

                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });






        bioedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext() );
                alertDialog.setTitle("Edit Bio");

                final EditText input = new EditText(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                input.setText(bio_text);

                alertDialog.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                reference.child("bio").setValue(input.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            editor2.putString("bio",input.getText().toString());
                                        editor2.commit();
                                        bio.setText(input.getText().toString());
                                        bio_text=input.getText().toString();
                                            Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();}
                                        else
                                            Toast.makeText(getContext(),"Error Updating",Toast.LENGTH_SHORT).show();

                                    }



                                });

                            }
                        });

                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });


























        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                reference.child("about").setValue("");

                reference.child("name").setValue("Vipin").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"OK",Toast.LENGTH_SHORT).show();
                    }
                });















            }
        });













        return v;
    }
}
