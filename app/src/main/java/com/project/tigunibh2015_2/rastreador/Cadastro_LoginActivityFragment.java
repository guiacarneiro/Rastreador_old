package com.project.tigunibh2015_2.rastreador;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class Cadastro_LoginActivityFragment extends Fragment {

   public Cadastro_LoginActivityFragment() {
   }

    public interface OnButtonLoginIsClicked {
        public void OnButtomClicked (View v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_login, container, false);

    }





}
