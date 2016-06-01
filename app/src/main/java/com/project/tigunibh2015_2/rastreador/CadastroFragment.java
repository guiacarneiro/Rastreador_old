package com.project.tigunibh2015_2.rastreador;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
        return view;

    }

    private void setWidgets(View view){
        eTnomeCadastro = (EditText) view.findViewById(R.id.nome_cadastro);
        eTtelefoneCadastro = (EditText) view.findViewById(R.id.telefone_cadasdro);
        eTsenhaCadastro = (EditText) view.findViewById(R.id.senha_cadastro);
        eTconfirmaSenha = (EditText) view.findViewById(R.id.conf_senha_cadastro);
        cadastraUsuario = (Button) view.findViewById(R.id.enviar_cadastro);
        cadastraUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviaCadastro();
            }
        });
    }

    private void enviaCadastro () {
        if (eTsenhaCadastro.getText().toString().length() >= 6 && eTsenhaCadastro == eTconfirmaSenha && eTtelefoneCadastro.getText().toString().length() == 9 && eTnomeCadastro.getText().toString().length() >= 4) {
            SetRespostaUsuario();
            new HttpPostCadastro().execute();
        }

        else { //implementar os outros if
            if (eTsenhaCadastro.getText().length() < 6) {
                Toast.makeText(this.getActivity(), "O campo senha deve ter mais de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SetRespostaUsuario (){
        usuario.setNomeUsuario(eTnomeCadastro.getText().toString());
        usuario.setSenhaUsuario(eTsenhaCadastro.getText().toString());
        usuario.setLoginUsuario(eTnomeCadastro.getText().toString());
        usuario.setTelefone(eTtelefoneCadastro.getText().toString());
    }


    private class HttpPostCadastro extends AsyncTask<Void, Void, RespostaUsuario> {

        @Override
        protected RespostaUsuario doInBackground(Void... params) {
            try {
                final String url = "http://rastreador-unibh.rhcloud.com/RastreadorService/rest/cadastraUsuario";
                resp.setResultado(resultado);
                resp.setUsuario(usuario);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                RespostaUsuario result = restTemplate.postForObject( url, resp, RespostaUsuario.class);
                return result;
            } catch (Exception e) {
                Log.e("Cadastro Login Activity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RespostaUsuario resp) {
            if (resp != null) { //Caso haja conexão com o servidor
                if ((Integer.parseInt(String.valueOf(resp.getResultado().getStatus()))==0)) {   //Caso cadastrado com sucesso
                    AlertDialog.Builder al = new AlertDialog.Builder(context);
                    al.setMessage(String.valueOf(resp.getResultado().getDescricao())).setCancelable(true);
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    al.show();
                }
                else{
                    AlertDialog.Builder al = new AlertDialog.Builder(context);  //Caso não cadastrado com sucesso
                    al.setMessage(String.valueOf(resp.getResultado().getDescricao())).setCancelable(true);
                    al.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            chamaMenuActivity();
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

        private void chamaMenuActivity(){
            Intent myIntent = new Intent(context, MenuActivity.class);
            getActivity().startActivity(myIntent);
        }
    }




}

