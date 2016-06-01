package com.project.tigunibh2015_2.rastreador.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.project.tigunibh2015_2.rastreador.Modelos.UsuarioCadastro;
import com.project.tigunibh2015_2.rastreador.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroFragment extends Fragment{


    private EditText eTnomeCadastro;
    private EditText eTtelefoneCadastro;
    private EditText eTsenhaCadastro;
    private EditText eTconfirmaSenha;
    private Button cadastraUsuario;
    private Context context;
    private RespostaUsuario resp = new RespostaUsuario();
    private Resultado resultado = new Resultado();
    private Usuario usuario = new Usuario();
    private UsuarioCadastro uc = new UsuarioCadastro();

    public static final String UsuarioPrefs = "UsuarioPrefs" ; //arquivo com o usuario logado atualmente no app
    public static final String codUsuario = "codUsuarioKey";
    public static final String nomeUsuario = "nomeUsuarioKey";
    public static final String loginUsuario = "loginUsuarioKey";
    public static final String senhaUsuario = "senhaUsuarioKey";
    public static final String rastreavel = "rastreavelKey";
    public static final String telefone = "telefoneKey";


    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        setWidgets(view); //seta os botões que serão usados
        context = getActivity();
        cadastraUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviaCadastro();
            }
        });
        return view;

    }

    private void setWidgets(View view){
        eTnomeCadastro = (EditText) view.findViewById(R.id.nome_cadastro);
        eTtelefoneCadastro = (EditText) view.findViewById(R.id.telefone_cadasdro);
        eTsenhaCadastro = (EditText) view.findViewById(R.id.senha_cadastro);
        eTconfirmaSenha = (EditText) view.findViewById(R.id.conf_senha_cadastro);
        cadastraUsuario = (Button) view.findViewById(R.id.enviar_cadastro);

    }

    private void enviaCadastro () {
        if (eTsenhaCadastro.getText().toString().length() >= 6 && eTsenhaCadastro.getText().toString().equals(eTconfirmaSenha.getText().toString())  && eTtelefoneCadastro.getText().toString().length() >= 9 && eTnomeCadastro.getText().toString().length() >= 4) {
            SetRespostaUsuario();
            new HttpPostCadastro().execute();
        }
        else { //implementar os outros if
            if (eTsenhaCadastro.getText().length() < 6) {
                Toast.makeText(context, "O campo senha deve ter mais de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
            if (eTtelefoneCadastro.getText().toString().length()<9){
                Toast.makeText(context, "O campo telefone deve ter mais de 9 caracteres", Toast.LENGTH_SHORT).show();
            }
            if(eTnomeCadastro.getText().toString().length()<4){
                Toast.makeText(context, "O campo nome deve ter mais de 4 caracteres", Toast.LENGTH_SHORT).show();
            }
            if(eTsenhaCadastro.getText().toString().length() != eTconfirmaSenha.getText().toString().length()){
                Toast.makeText(context, "As senhas não conferem", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SetRespostaUsuario (){
        uc.setNomeUsuario(eTnomeCadastro.getText().toString());
        uc.setSenhaUsuario(eTsenhaCadastro.getText().toString());
        uc.setLoginUsuario(eTnomeCadastro.getText().toString());
        uc.setTelefone(eTtelefoneCadastro.getText().toString());
    }


    private class HttpPostCadastro extends AsyncTask<Void, Void, RespostaUsuario> {

        private ProgressDialog Pdialog;

        @Override
        protected void onPreExecute() {
            Pdialog = new ProgressDialog(context);
            Pdialog.setMessage("Cadastrando...Por favor aguarde");
            Pdialog.show();
        }

        @Override
        protected RespostaUsuario doInBackground(Void... params) {
            try {
                final String url = "http://rastreador-unibh.rhcloud.com/RastreadorService/rest/usuario/cadastraUsuario";
                resp.setResultado(resultado);
                resp.setUsuario(usuario);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                RespostaUsuario result = restTemplate.postForObject( url, uc, RespostaUsuario.class);
                return result;
            } catch (Exception e) {
                Log.e("Cadastro Login Activity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RespostaUsuario resp) {

            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setCancelable(true);

            if (Pdialog.isShowing()) {
                Pdialog.dismiss();
            }

            if (resp != null) { //Caso haja conexão com o servidor
                if ((Integer.parseInt(String.valueOf(resp.getResultado().getStatus()))==0)) {   //Caso cadastrado com sucesso
                    al.setMessage(String.valueOf(resp.getResultado().getDescricao()));
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            salvaUsuario();
                            chamaMenuActivity();
                        }
                    });
                    al.show();

                }
                else{
                     //Caso não cadastrado com sucesso
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
            getActivity().finish(); //finaliza a activity desativando o botão voltar
        }

        private void salvaUsuario(){ //salva o usuario atualmente logado

            SharedPreferences shared = context.getSharedPreferences(UsuarioPrefs, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putInt(codUsuario, usuario.getCodUsuario());
            editor.putString(loginUsuario, usuario.getLoginUsuario());
            editor.putString(senhaUsuario, usuario.getSenhaUsuario());
            editor.putBoolean(rastreavel, usuario.isRastreavel());
            editor.putString(telefone, usuario.getTelefone());
            editor.putString(nomeUsuario, usuario.getNomeUsuario());
            editor.apply();
        }
    }


}

