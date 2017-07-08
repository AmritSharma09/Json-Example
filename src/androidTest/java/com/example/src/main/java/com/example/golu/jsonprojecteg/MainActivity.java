package com.example.golu.jsonprojecteg;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
RecFragment recFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager mgr=getSupportFragmentManager();
        FragmentTransaction trans=mgr.beginTransaction();
        recFragment=new RecFragment();
        trans.add(R.id.container,recFragment);
        trans.commit();
    }
}
