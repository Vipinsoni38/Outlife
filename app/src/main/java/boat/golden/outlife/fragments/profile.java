package boat.golden.outlife.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import boat.golden.outlife.MainActivity;
import boat.golden.outlife.R;
import boat.golden.outlife.profile_datatype;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Vipin soni on 04-07-2018.
 */

public class profile extends Fragment {


    Button interest;
    FirebaseDatabase database;
    DatabaseReference reference;
    Spinner statespinner,cityspinner;
    AutoCompleteTextView autocomplete;
    StorageReference storageReference;
    String UID,bio_text,user_name;

    ImageView nameedit,bioedit,profile_image;
    TextView profile_name,bio,place,profession;
    Bitmap bitmap;
    Uri uri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.profile,container,false);
        interest=v.findViewById(R.id.interestbtn);
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
        user_name=sharedPreferences2.getString("name","User Name");
        profile_name.setText(user_name);
        bio_text=sharedPreferences2.getString("bio","HI this is my bio");
        bio.setText(bio_text);


        profile_image=v.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder profileoption=new AlertDialog.Builder(getContext());
                View lo=getActivity().getLayoutInflater().inflate(R.layout.profile_options,null);
                profileoption.setView(lo);
                Button view,edit;
                view=lo.findViewById(R.id.view_pic);
                edit=lo.findViewById(R.id.edit_pic);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i, 101);
                    }
                });
                profileoption.show();
            }
        });




















        place=v.findViewById(R.id.place);
        place.setText(sharedPreferences2.getString("place","-not mentioned-"));
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder placeselector=new AlertDialog.Builder(getContext());
                View dialogview=getActivity().getLayoutInflater().inflate(R.layout.dialog_place,null);
                autocomplete=dialogview.findViewById(R.id.autocomplete);
                placeselector.setView(dialogview);
                placeselector.setTitle("Select City")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    reference.child("place").setValue(autocomplete.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(),"Place Edit Complete",Toast.LENGTH_SHORT).show();
                            editor2.putString("place",autocomplete.getText().toString());
                            editor2.commit();
                            place.setText(autocomplete.getText().toString());
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
                final String[] names={"Student","Businessman","Employee"};
                AlertDialog.Builder proselector=new AlertDialog.Builder(getContext());
                View dialogview=getActivity().getLayoutInflater().inflate(R.layout.dialog_profession,null);
                proselector.setView(dialogview);
                final int[] pos = new int[1];
                Spinner spinner=dialogview.findViewById(R.id.spinner);
                ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,names);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        pos[0] =position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                proselector.setTitle("Select Profession")
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                reference.child("pro").setValue(names[pos[0]]).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        editor2.putString("pro",names[pos[0]]);
                                        editor2.commit();
                                        profession.setText(names[pos[0]]);
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

                final String[] multiChoiceItems = {"Sightseeing","Trekking","Adventures","Concert/Festival","Road Trip","Shopping"};
                final boolean[] checkedones = new boolean[]{
                        false,
                        false,
                        false,
                        false,
                        false,
                        false

                };
                new AlertDialog.Builder(getContext())
                        .setTitle("Select one")
                        .setMultiChoiceItems(multiChoiceItems, checkedones, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedones[which]=isChecked;


                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            String bla="";
                                for (int i = 0; i<checkedones.length; i++){
                                    boolean checked = checkedones[i];
                                    if (checked) {
                                       bla=bla+multiChoiceItems[i]+" ";
                                    }
                                }

                                reference.child("interest").setValue(bla).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        if (!sharedPreferences2.getBoolean("first_login",false))
        {checkforfirsttimeuser();
        }
        return v;
    }

    private void checkforfirsttimeuser() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("profile/"+UID+"/isfirst");
        reference=database.getReference("profile/"+UID);


// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            String is=dataSnapshot.getValue(String.class);
            if (is==null||is.equals("")||is.equals("first")){
                reference.child("isfirst").setValue("notfirst");
                reference.child("name").setValue(user_name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SharedPreferences sharedPreferences2=getContext().getSharedPreferences("user_data",Context.MODE_PRIVATE);
                         SharedPreferences.Editor editor2=sharedPreferences2.edit();
                         editor2.putBoolean("first_login",true);
                         editor2.commit();

                        Toast.makeText(getContext(),"User Name Uploaded",Toast.LENGTH_SHORT).show();
                    }
                });



            }else {
                DatabaseReference reff = database.getReference("profile/"+UID);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        profile_datatype datatype=dataSnapshot.getValue(profile_datatype.class);

                        if (datatype.getName()!=null)
                        {
                            editor2.putString("name", datatype.getName());
                            editor2.commit();
                            profile_name.setText(datatype.getName());
                        }
                        if (datatype.getBio()!=null)
                        {
                            editor2.putString("bio", datatype.getBio());
                            editor2.commit();
                            bio.setText(datatype.getBio());
                        }
                        if (datatype.getPlace()!=null)
                        {
                            editor2.putString("place", datatype.getPlace());
                            editor2.commit();
                            place.setText(datatype.getPlace());
                        }
                        if (datatype.getPro()!=null)
                        {
                            editor2.putString("pro", datatype.getPro());
                            editor2.commit();
                            profession.setText(datatype.getPro());
                        }
                        if (datatype.getInterest()!=null)
                        {
                            editor2.putString("interest", datatype.getInterest());
                            editor2.commit();
                            //othger Stuff
                        }






                        //---
                        sharedPreferences2 = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                        editor2 = sharedPreferences2.edit();
                        editor2.putBoolean("first_login", true);
                        editor2.commit();



                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });










            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"UnExpected Error",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101&&resultCode==RESULT_OK)
        {
            try {
                uri = data.getData();
                ContentResolver resolver = getActivity().getContentResolver();
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);

                profile_image.setImageBitmap(bitmap);
                upload();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }





    }

    private void upload() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Image Uploading...");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref;
        ref = storageReference.child("profile_pic/" + UID  + ".jpg");






        profile_image.setDrawingCacheEnabled(true);
        profile_image.buildDrawingCache();
        //Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 55, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                progressDialog.dismiss();


                Toast.makeText(getContext(), "Image upload Failed !", Toast.LENGTH_SHORT).show();


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();






            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                        .getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int) progress + "%");
            }
        });









    }


}
