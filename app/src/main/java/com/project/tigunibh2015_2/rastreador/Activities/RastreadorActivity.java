package com.project.tigunibh2015_2.rastreador.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.project.tigunibh2015_2.rastreador.Adapters.DependenteAdapter;
import com.project.tigunibh2015_2.rastreador.Modelos.Dependente;
import com.project.tigunibh2015_2.rastreador.Modelos.RespostaUsuario;
import com.project.tigunibh2015_2.rastreador.Modelos.Resultado;
import com.project.tigunibh2015_2.rastreador.Modelos.Usuario;
import com.project.tigunibh2015_2.rastreador.Modelos.UsuarioCadastro;
import com.project.tigunibh2015_2.rastreador.R;
import com.project.tigunibh2015_2.rastreador.parametros.ParametroRastreamento;
import com.project.tigunibh2015_2.rastreador.respostas.RespostaListaUsuario;
import com.project.tigunibh2015_2.rastreador.ws.AdicionaDependenteWs;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class RastreadorActivity extends AppCompatActivity{

    final String urlGetDependentes = "http://rastreador-unibh.rhcloud.com/RastreadorService/rest/usuario/getListaDependentes";

    private ArrayList<Dependente> dependentes = new ArrayList<Dependente>();
    private SharedPreferences shared;
    private Context context;
    public static final String UsuarioPrefs = "UsuarioPrefs" ; //arquivo com o usuario logado atualmente no app
    public static final String codUsuario = "codUsuarioKey";
    public static final String nomeUsuario = "nomeUsuarioKey";
    public static final String loginUsuario = "loginUsuarioKey";
    public static final String senhaUsuario = "senhaUsuarioKey";
    public static final String rastreavel = "rastreavelKey";
    public static final String telefone = "telefoneKey";
    private int webOption=0;//1 para pesquisar dependentes

    private RespostaUsuario resp = new RespostaUsuario();
    private Resultado resultado = new Resultado();
    private Usuario usuario = new Usuario();
    private UsuarioCadastro uc = new UsuarioCadastro();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastreador);

        context =this;
        shared = context.getSharedPreferences(UsuarioPrefs, Context.MODE_PRIVATE); //pega o usuario salvo quando feito o login/cadastro
        if(shared!=null){
            usuario.setCodUsuario(shared.getInt(codUsuario, 0));
            usuario.setNomeUsuario(shared.getString(nomeUsuario, null));
            usuario.setLoginUsuario(shared.getString(loginUsuario, null));
            usuario.setSenhaUsuario(shared.getString(senhaUsuario, null));
            usuario.setRastreavel(shared.getBoolean(rastreavel, false));
            usuario.setTelefone(shared.getString(telefone, null));
        }
        webOption= 1;
       // new HttpPostRastrear().execute();


        for(int i = 0 ; i<15; i++){
            Dependente d = new Dependente();
            d.setNome("Fulano de Tal" + (i+1));
            dependentes.add(d);
        }
        ListView lv = (ListView) findViewById(R.id.lv);

        if (lv != null) {
            lv.setAdapter(new DependenteAdapter(this, dependentes));
            lv.setDividerHeight(15);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(RastreadorActivity.this, dependentes.get(position).getNome(), Toast.LENGTH_LONG).show();
                    view.setBackgroundColor(100);
                }
            });
        }

        final EditText campoLogin = (EditText)findViewById(R.id.editLogin);
        final EditText campoTelefone = (EditText)findViewById(R.id.editTelefone);
        Button btnAdicionar = (Button) findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpPostAdicionarDependente hpad = new HttpPostAdicionarDependente();
                hpad.execute(campoLogin.getText().toString(),campoTelefone.getText().toString());
            }
        });

    }

    private class HttpPostAdicionarDependente extends AsyncTask<String, Void, RespostaListaUsuario> {

        private ProgressDialog Pdialog;

        @Override
        protected void onPreExecute() {
            Pdialog = new ProgressDialog(context);
            Pdialog.setMessage("Cadastrando...Por favor aguarde");
            Pdialog.show();
        }

        @Override
        protected RespostaListaUsuario doInBackground(String... params) {
            try {
                AdicionaDependenteWs adws = new AdicionaDependenteWs();
                Usuario usuarioFilho = new Usuario();
                usuarioFilho.setLoginUsuario(params[0]);
                usuarioFilho.setTelefone(params[1]);
                ParametroRastreamento parametroRastreamento = new ParametroRastreamento();
                parametroRastreamento.setUsuarioFilho(usuarioFilho);
                parametroRastreamento.setUsuarioPai(usuario);
                RespostaListaUsuario result = adws.adicionaDependente(parametroRastreamento);
                return result;
            } catch (Exception e) {
                Log.e("Cadastro Login Activity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RespostaListaUsuario resp) {

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
            startActivity(myIntent);
            finish(); //finaliza a activity desativando o botão voltar
        }

    }

    private class HttpPostRastrear extends AsyncTask<Void, Void, RespostaUsuario> {

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
            startActivity(myIntent);
            finish(); //finaliza a activity desativando o botão voltar
        }

    }


}
