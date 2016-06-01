package com.project.tigunibh2015_2.rastreador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RastrearActivity extends ActionBarActivity {

    private Coordenadas C = new Coordenadas();
    private Button LocalAtual; // Botão de pedir localização atual


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastrear);

        LocalAtual = (Button) findViewById(R.id.button);
        LocalAtual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                requisitaLocalizacao();
            }

        });
    }

    private void requisitaLocalizacao() { //Chamado ao clicar no botão de requisitar localização atual

        Log.i("Send SMS", "");
        String telefone = "99093539";  //Pegar do arquivo que contém o cadastro do GPS - número de tel do chip
        String cCadastro = "xxxxxxxxx"; //Pegar do arquivo que contém o cadastro do GPS - código de cadastro do GPS
        String codRequisicaoDeLocal = "xxxxxxxxx"; //Pegar do arquivo que contém o cadastro do GPS - código usado para solicitar localização do GPS
        String message = null;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rastrear, menu);
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        SmsReceiver sms = new SmsReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


   /* public void Recebelatitude (View view){
        EditText text = (EditText)findViewById(R.id.editText2);
        String s = text.getText().toString();
        double lng=Double.parseDouble(s);
        C.setLatitude(lng);


    }
    public void Recebelongitude (View view){
        EditText text2 = (EditText)findViewById(R.id.editText);
        String s = text2.getText().toString();
        double lng=Double.parseDouble(s);
        C.setLongitude(lng);

    }*/

    public void rastrearGPS (View view){
        C.setLatitude(-19.820066);
        C.setLongitude(-43.968009);
        C.SetHoraAtual();


        String localString = String.valueOf(C.getLatitude())+ "," + String.valueOf(C.getLongitude() + "Hora atual: " + String.valueOf(C.getHoraAtual()));
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText(localString);


        /* Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/


        Intent intent = new Intent(this, MapsActivity.class);
        int escolha = 1; // Fazer métodos para receber se o cliente deseja rastrear uma vez ou exibir a lista no mapa
        intent.putExtra("escolha", escolha);
        startActivity(intent);


    }
}
