package com.example.admin.miniproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //private  SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;

    private FirebaseAuth firebaseAuth;

    //    private  FirebaseAuth.AuthStateListener authStateListener;

    private NavigationView navigationView;
    private FirebaseUser user;
    private TextView emailtextView;
    private Uri path;
    static Uri painting;
    TextView nav_user,nav_email;
    private FirebaseStorage storage;
    private StorageReference mstorageref;
    private CircleImageView profilephoto;
    private ProgressDialog progressDialog;
    View hv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.w("Create","Create");
        progressDialog=new ProgressDialog(Main2Activity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.nav_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent();
                it.setAction(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(Intent.createChooser(it,"Select A image"),2);
                //Snackbar.make(v, "Retry", Snackbar.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer= (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


        storage=FirebaseStorage.getInstance();
        mstorageref=storage.getReference("Profile Photos/");
        firebaseAuth=FirebaseAuth.getInstance();

//        authStateListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser()==null)
//                {
//                    Toast.makeText(getApplicationContext(),"Logout Successfully",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(Main2Activity.this,MainActivity.class));
//                    //finish();
//                }
//            }
//        };

        user=FirebaseAuth.getInstance().getCurrentUser();
        hv =  navigationView.getHeaderView(0);
        nav_email = (TextView)hv.findViewById(R.id.Emailprofile);
        nav_user=(TextView)hv.findViewById(R.id.userprofile);
        nav_user=(TextView)hv.findViewById(R.id.userprofile);
        if(getIntent().getStringExtra("Username")==null)
        {
            nav_user.setText(user.getDisplayName().toString());
            nav_email.setText(user.getEmail().toString());

        }
        else
        {
            nav_user.setText(getIntent().getStringExtra("Username"));
            nav_email.setText(user.getEmail().toString());
        }



        profilephoto=(CircleImageView) hv.findViewById(R.id.imageView);
        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent();
                it.setAction(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(Intent.createChooser(it,"Select A image"),1);
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!=null && resultCode==RESULT_OK)
        {
            path=data.getData();

            progressDialog.setMessage("Uploading profile Photo..");
            progressDialog.setTitle("Profile Photo");
            progressDialog.show();

            final StorageReference riversRef = mstorageref.child(user.getUid());
            riversRef.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Glide.with(Main2Activity.this).load(uri.toString()).centerCrop().crossFade().into(profilephoto);
                            progressDialog.dismiss();
                        }
                    });
                }
            });


            //profilephoto.setVisibility(View.VISIBLE);
            //profilephoto.setVisibility(View.VISIBLE);
            //profilephoto.setMaxHeight(50);
            //profilephoto.setMaxWidth(50);
//            profilephoto.setImageURI(user.getPhotoUrl());
//            profilephoto.setMaxWidth(60);
//            profilephoto.setMaxHeight(60);
        }
        if(requestCode==2 && data!=null && resultCode==RESULT_OK)
        {
            painting=data.getData();
            Log.d("Hello","Hello");
            startActivity(new Intent(Main2Activity.this,Paintiing_Upload.class));
            //StorageReference upload_art=upload_storage.child();
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.Artist) {
            startActivity(new Intent(Main2Activity.this, Filter_Popup.class));
        }
        else if(id==R.id.Price){
            startActivity(new Intent(Main2Activity.this,Price_Filter.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bidding) {
            // Handle the camera action
        } else if (id == R.id.nav_myuploads) {

            startActivity(new Intent(Main2Activity.this,MyUploads.class));

        } else if (id == R.id.nav_myorders) {

        } else if (id == R.id.nav_settings) {

            startActivity(new Intent(Main2Activity.this,settings.class));

        } else if (id == R.id.nav_logout) {

            firebaseAuth.signOut();
            startActivity(new Intent(Main2Activity.this,MainActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    On_sale tab1= new On_sale();
                    return tab1;
                case 1:
                    On_bid tab2= new On_bid();
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "On Sale";
                case 1:
                    return "On Bid";
            }
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("Start","Start");

        DatabaseReference tempreference= FirebaseDatabase.getInstance().getReference("USER").child(user.getUid()).child("balance");

        tempreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menu menu=navigationView.getMenu();
                MenuItem balance=menu.findItem(R.id.balance);
                balance.setTitle(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final StorageReference riversRef = mstorageref.child(user.getUid());

        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Main2Activity.this).load(uri.toString()).centerCrop().crossFade().into(profilephoto);

            }
        });
    }


    @Override
    protected void onResume() {

        Log.w("Resume","Resume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.w("Stop","Stop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("destroy","Sedtroy");
    }
}





