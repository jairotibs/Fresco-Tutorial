package com.example.root.fresco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by phmb2 on 09/07/15.
 */

public class Galeria_imagens extends ActionBarActivity
{
    private int limiar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escolha_galeria); //Carregando o arquivo escolha_galeria.xml

        final SeekBar seek = (SeekBar) findViewById(R.id.seekBar);

        final TextView quantidade = (TextView) findViewById(R.id.qtde);

        quantidade.setText(seek.getProgress() + "/" + seek.getMax());

        final Button botao = (Button) findViewById(R.id.sair) ; //Definindo o botão sair

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                limiar = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                quantidade.setText(limiar + "/" + seek.getMax());
            }
        });
    }

    static final String key_valor = "ID_GALERIA"; //Chave para guardar o id da galeria a ser escolhido pelo usuário
    static final String key_valor2  = "ID_LIMIAR"; //Chave para guardar o id do thresholded
    private int valor;

    public void eventoClick(View view)
    {
        Intent id_galeria = new Intent(Galeria_imagens.this, Tela_imagem.class); //Criação da intent para iniciar a próxima a activity
        Bundle dado = new Bundle(); //Criação do Bundle

        switch (view.getId()) //Escolha do radioButton
        {
            case R.id.radio_animal:  //id - Galeria Animais
                valor = 1;
                dado.putInt(key_valor, valor); //Colocando o dado no bundle
                break;

            case R.id.radio_cidade: //id - Galeria Cidades
                valor = 2;
                dado.putInt(key_valor, valor); //Colocando o dado no bundle
                break;

            case R.id.radio_carro: //id - Galeria Carros

                valor = 3;
                dado.putInt(key_valor, valor); //Colocando o dado no bundle
                break;

            case R.id.radio_gif: //id - Gif

                valor = 4;
                dado.putInt(key_valor, valor); //Colocando o dado no bundle
                break;
        }

        dado.putInt(key_valor2,limiar);

        id_galeria.putExtras(dado);

        startActivity(id_galeria); //Iniciando a próxima activity
    }

}
