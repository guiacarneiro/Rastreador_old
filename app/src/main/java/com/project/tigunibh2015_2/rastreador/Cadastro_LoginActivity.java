package com.project.tigunibh2015_2.rastreador;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;



public class Cadastro_LoginActivity extends AppCompatActivity {
    private String nome_cadastro, telefone_cadastro, senhaCadastro, confirma_senha_cadastro;
    private Context context;
    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        new HttpRequestTask().execute();
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__login);
        context = this;


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Cadastro_LoginActivityFragment clf = new Cadastro_LoginActivityFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            clf.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, clf).commit();
        }


    }

    public void Cadastrar (View view) {
        CadastroFragment cd = new CadastroFragment();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, cd);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void Logar (View view) {
        LoginFragment lg = new LoginFragment();
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, lg);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    //Classe que testa em asyncTask se o servidor está online

    private class HttpRequestTask extends AsyncTask<Void, Void, Healthcheck> {
        @Override
        protected Healthcheck doInBackground(Void... params) {
            try {
                final String url = "http://rastreador-unibh.rhcloud.com/RastreadorService/rest/healthcheck";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Healthcheck healthcheck = restTemplate.getForObject(url, Healthcheck.class);
                return healthcheck;
            } catch (Exception e) {
                Log.e("Cadastro Login Activity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Healthcheck healthcheck) {
            if (healthcheck != null) { //Caso haja conexão com o servidor
                if ((Integer.parseInt(healthcheck.getStatus())!=0)) {   //Caso o servidor não esteja online
                    AlertDialog.Builder al = new AlertDialog.Builder(context);
                    al.setMessage("Não foi Possível Conectar com o Servidor: Tente Novamente mais Tarde").setCancelable(true);
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    al.show();
                }
                else{
                    AlertDialog.Builder al = new AlertDialog.Builder(context);
                    al.setMessage(healthcheck.getDescricao()).setCancelable(true);
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    al.show();
                }

            }
            else{ //Caso não haja conexão com o servidor
                AlertDialog.Builder al = new AlertDialog.Builder(context);
                al.setMessage("Não foi Possível acessar o servidor: Verifique se Você está conectado à Internet").setCancelable(true);
                al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                al.show();
            }


        }
    }








    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro__login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}
