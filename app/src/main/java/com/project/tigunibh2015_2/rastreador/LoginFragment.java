package com.project.tigunibh2015_2.rastreador;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText nome;
    private EditText senha;
    private Button entrar;
    private String n="";
    private String s="";


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container,   false);

        nome=(EditText)view.findViewById(R.id.nome_cadastro);
        senha=(EditText)view.findViewById(R.id.conf_senha_cadastro);
        entrar = (Button)view.findViewById(R.id.button);

        nome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                n=nome.getText().toString().trim();

                //só para teste
                Toast.makeText(getActivity().getApplicationContext(),n,Toast.LENGTH_LONG).show();
            }
        });

        senha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=senha.getText().toString().trim();

                //só para teste
                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(n.equals("")||n.equals(null)){
                //teste
                Toast.makeText(getActivity().getApplicationContext(),"Você não preencheu o campo nome",Toast.LENGTH_LONG).show();
                }
                if(s.equals("")||s.equals(null)){
                    //teste
                    Toast.makeText(getActivity().getApplicationContext(),"Você não preencheu o campo senha",Toast.LENGTH_LONG).show();
                }
                else{ //teste - chamar aqui a task que irá verificar o nome e a senha e logar o usuário

                    Toast.makeText(getActivity().getApplicationContext(),"Campos preenchidos corretamente",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MenuActivity.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }



}
