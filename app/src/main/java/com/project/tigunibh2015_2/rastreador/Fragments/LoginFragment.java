package com.project.tigunibh2015_2.rastreador.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tigunibh2015_2.rastreador.Activities.MenuActivity;
import com.project.tigunibh2015_2.rastreador.Modelos.RespostaUsuario;
import com.project.tigunibh2015_2.rastreador.Modelos.Resultado;
import com.project.tigunibh2015_2.rastreador.Modelos.Usuario;
import com.project.tigunibh2015_2.rastreador.Modelos.Usuariologin;
import com.project.tigunibh2015_2.rastreador.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText nome;
    private EditText senha;
    private Button entrar;

    private String n="";
    private String s="";

    private RespostaUsuario resp = new RespostaUsuario();
    private Resultado resultado = new Resultado();
    private Usuario usuario = new Usuario();
    private Usuariologin ul = new Usuariologin();

    private Context context;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container,   false);
        context = getActivity();
        nome=(EditText)view.findViewById(R.id.nome_cadastro);
        senha=(EditText)view.findViewById(R.id.conf_senha_cadastro);
        entrar = (Button)view.findViewById(R.id.button);

        nome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                n=nome.getText().toString().trim();

                //só para teste
                //Toast.makeText(context,n,Toast.LENGTH_LONG).show();
            }
        });

        senha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=senha.getText().toString().trim();


                //só para teste
               // Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if((n.length()>=3 && s.length()>=3)){
                    SetUsuarioLogin();
                    LogaUsuário();
                }
                else{
                    if(n.length()<3){
                        //teste
                        Toast.makeText(context,"O campo nome parece errado",Toast.LENGTH_LONG).show();
                    }
                    if(s.length()<3){
                        //teste
                        Toast.makeText(context,"O campo senha parece errado",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    private void LogaUsuário (){
        new HttpPostLogin().execute();
    }

    private void SetUsuarioLogin (){
        ul.setLoginUsuario(n);
        ul.setSenhaUsuario(s);
    }

    private class HttpPostLogin extends AsyncTask<Void, Void, RespostaUsuario> {
        private ProgressDialog Pdialog;

        @Override
        protected void onPreExecute() {
            Pdialog = new ProgressDialog(context);
            Pdialog.setMessage("Entrando...Por favor aguarde");
            Pdialog.show();
        }

        @Override
        protected RespostaUsuario doInBackground(Void... params) {
            try {
                final String url = "http://rastreador-unibh.rhcloud.com/RastreadorService/rest/usuario/autenticaUsuario";
                resp.setResultado(resultado);
                resp.setUsuario(usuario);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                RespostaUsuario result = restTemplate.postForObject( url, ul, RespostaUsuario.class);
                return result;

            } catch (Exception e) {
                Log.e("Cadastro Login Activity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RespostaUsuario resp) {

            if (Pdialog.isShowing()) {
                Pdialog.dismiss();
            }

            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setCancelable(true);

            if (resp != null) { //Caso haja conexão com o servidor
                if ((Integer.parseInt(String.valueOf(resp.getResultado().getStatus()))==0)) {   //Caso cadastrado com sucesso
                    al.setMessage(String.valueOf(resp.getResultado().getDescricao()));
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            chamaMenuActivity();
                        }
                    });
                    al.show();

                }
                else{
                    //Caso não logado com sucesso
                    al.setMessage(String.valueOf(resp.getResultado().getDescricao()));
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    al.show();
                }

            }
            else{ //Caso não haja conexão com o servidor
                al.setMessage("Não foi Possível acessar o servidor: Verifique se Você está conectado à Internet");
                al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                al.show();
            }



        }

        private void chamaMenuActivity(){
            Intent myIntent = new Intent(context, MenuActivity.class);
            getActivity().startActivity(myIntent);

        }


    }


}
