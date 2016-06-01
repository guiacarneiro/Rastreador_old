package com.project.tigunibh2015_2.rastreador.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.project.tigunibh2015_2.rastreador.Fragments.MenuFragment;
import com.project.tigunibh2015_2.rastreador.R;

public class MenuActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
            MenuFragment mf = new MenuFragment();
            mf.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mf).commit();
        }
    }

    public void Rastrear (View view){
        Intent intent = new Intent(this, RastreadorActivity.class);
        startActivity(intent);
    }

    public void AdicionarRemover (View view){
        Intent intent = new Intent(this, RastreadorActivity.class);
        startActivity(intent);
    }
}
