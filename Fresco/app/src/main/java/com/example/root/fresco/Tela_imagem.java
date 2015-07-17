package com.example.root.fresco;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by phmb2 on 05/07/15.
 */

public class Tela_imagem extends ActionBarActivity{

    //Repositório com dez imagens carregadas da internet - Galeria Animais
     private Uri[] animais = {

             Uri.parse("http://cin.ufpe.br/~jts2/animais/aguia.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/cobra.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/gato.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/leao.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/lobo.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/macaco.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/pato.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/tartaruga.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/tigre.jpg"),
             Uri.parse("http://cin.ufpe.br/~jts2/animais/urso.jpg")

     };

    //Repositório com dez imagens carregadas da internet - Galeria Cidades
    private Uri[] cidades = {

            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Barcelona.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Lisboa.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Cairo.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Londres.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Madri.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Paris.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Veneza.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Roma.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Sidney.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/cidades/Toronto.jpg")

    };

    //Repositório com as dez imagens carregadas da internet - Galeria Carros
    private Uri[] carros = {

            Uri.parse("http://cin.ufpe.br/~jts2/carros/bmw.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/bugatti.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/ferrari_black.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/ferrari_green.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/ferrari_red.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/ferrari_yellow.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/fusca.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/range_roover.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/lamborghini.jpg"),
            Uri.parse("http://cin.ufpe.br/~jts2/carros/mercedes.jpg")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Fresco.initialize(this); //Inicializando a classe Fresco antes de chamar o app em setContentView()
        setContentView(R.layout.activity_tela_inicial); //Carregando o arquivo escolha_galeria.xml

        int id = 0; //identificador da escolha do radioButton pelo usuário
        int lim = 0; //identificador do limiar

        Intent recebe_valor = getIntent(); //Pegando a Intent que foi passada

        if (recebe_valor != null) { //Se o que tiver na Intent for diferente de null, recupera o Bundle ligado a esta Intent
            Bundle bundle = recebe_valor.getExtras();

            if (bundle != null)
            {
                id = bundle.getInt(Galeria_imagens.key_valor); //Pegando o id da galeria
                lim = bundle.getInt(Galeria_imagens.key_valor2); //Pegando o id do limiar

            }
        }

        SimpleDraweeView imagem = (SimpleDraweeView) findViewById(R.id.my_image); //Definindo uma imagem a ser carregada pelo app

        if (id == 4) //Gif
        {
            Toast.makeText(this, "Gif", Toast.LENGTH_LONG).show(); //Notificação que mostra um Gif

            Uri gif = Uri.parse("http://i.imgur.com/L32RQlY.gif"); //Carregando Gif da internet

            DraweeController dc; //Construindo um controlador para controlar uma melhor exibição da imagem
            dc = Fresco.newDraweeControllerBuilder()
                    .setUri(gif) //Uri da imagem
                    .setAutoPlayAnimations(true) //Animação ativada
                    .setOldController(imagem.getController()) //Setando o controlador antigo da imagem
                    .build();

            imagem.setController(dc); //Setando o novo controlador

        }
        else {
            ViewPager v_pager = (ViewPager) findViewById(R.id.viewPager); //Criando uma ViewPager para a galeria de imagens

            switch (id)
            {
                case 1: //Galeria Animais
                    Toast.makeText(this, "Galeria de Animais", Toast.LENGTH_SHORT).show(); //Notificação Galeria Animais

                    v_pager.setAdapter(new AdapterImagem(this, animais, imagem, lim)); //Instanciando um adapter para carregar a galeria de animais
                    break;

                case 2: //Galeria Cidades
                    Toast.makeText(this, "Galeria de Cidades", Toast.LENGTH_SHORT).show(); //Notificação Galeria Cidades

                    v_pager.setAdapter(new AdapterImagem(this, cidades, imagem, lim)); //Instanciando um adapter para carregar a galeria de cidades
                    break;

                case 3: //Galeria Carros
                    Toast.makeText(this, "Galeria de Carros", Toast.LENGTH_SHORT).show(); //Notificação Galeria Carros

                    v_pager.setAdapter(new AdapterImagem(this, carros, imagem, lim)); //Instanciando adapter para carregar a galeria de carros
                    break;
            }

        }
    }
}
