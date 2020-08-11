package com.example.vamorachar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, TextToSpeech.OnInitListener {
    EditText edtValor,edtGalera;
    TextView tvRes;
    FloatingActionButton share,tocar;
    TextToSpeech ttsPlayer;
    int galera=2;
    double valor=0.0;
    String resultado ="R$ 0,00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtValor = (EditText) findViewById(R.id.editValor);
        edtValor.addTextChangedListener(this);
        edtGalera = (EditText) findViewById(R.id.galera);
        edtGalera.addTextChangedListener(this);
        tvRes = (TextView) findViewById(R.id.tv);
        tvRes.setText("R$ 0.00");
        share = (FloatingActionButton) findViewById(R.id.share);
        share.setOnClickListener(this);
        tocar =(FloatingActionButton) findViewById(R.id.tts);
        tocar.setOnClickListener(this);
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent,1122);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1122){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                ttsPlayer = new TextToSpeech(this,this);

            }else{
                Intent instalTTSIntent = new Intent();
                instalTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(instalTTSIntent);
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
       try{
            double res = Double.parseDouble(edtValor.getText().toString());
            res = (res / Double.parseDouble(edtGalera.getText().toString()));
           DecimalFormat dt = new DecimalFormat("#.00");
           tvRes.setText("R$ "+dt.format(res));
       }catch (Exception e){
           tvRes.setText("R$ 0.00");
       }
    }

    @Override
    public void onClick(View view) {
        if(view==share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(intent.EXTRA_TEXT,"A conta dividida por pessoa é "+tvRes.getText().toString());
            startActivity(intent);
        }

        if(view ==tocar){
            if(ttsPlayer !=null){
                ttsPlayer.speak("o valor por pessoa é de "+tvRes.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,"ID1");
            }
        }
    }

    @Override
    public void onInit(int i) {
        if(i == TextToSpeech.SUCCESS){

        }
    }
}