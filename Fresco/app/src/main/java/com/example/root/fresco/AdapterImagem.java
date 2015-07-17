package com.example.root.fresco;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import static android.widget.Toast.*;

/**
 * Created by phmb2 on 05/07/15.
 */

public class AdapterImagem extends PagerAdapter { //Extendendo uma classe base que provêm um adaptador para popular páginas no ViewPager
    private Context contexto;
    private Uri[] uri_imgs;
    private SimpleDraweeView img;
    private int lim;

    public AdapterImagem(Context contexto, Uri[] uri_imgs, SimpleDraweeView img, int lim) //Construtor do adapter da imagem
    {
        this.contexto = contexto;
        this.uri_imgs = uri_imgs;
        this.img = img;
        this.lim = lim;
    }

    @Override
    public int getCount()
    {
        return uri_imgs.length;
    } //Retorna a quantidade de imagens

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object; //Retorna true se o view é igual ao objeto passado como parâmetro
    }

    @Override
    public Object instantiateItem(ViewGroup container, int pos) //Retorna um novo layout
    {
        LinearLayout l_layout = new LinearLayout(contexto);
        l_layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams l_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        l_layout.setLayoutParams(l_params);
        container.addView(l_layout);

        //Modificando a imagem, aplicando uma thresholded na malha de pixels da imagem
        //Componente para decodificar imagens.
        Postprocessor mesh = new BasePostprocessor() //Classe Postprocessor não suporta imagens animadas.
        {
            @Override
            public void process(Bitmap bitmap)
            {
                for (int x = 0; x < bitmap.getWidth(); x = x + 1) {
                    for (int y = 0; y < bitmap.getHeight(); y = y + 2) {

                        if(bitmap.getPixel(x,y) < lim) //Verifica se o pixel da imagem é maior que o limiar fornecido pelo usuário
                        {
                            bitmap.setPixel(x,y,Color.argb(1,255,255,255));
                        }
                        else
                        {
                            bitmap.setPixel(x, y, Color.argb(1,0,0,0));
                        }
                    }
                }
            }
        };

        //Requisitando múltiplas imagens da URL usando ImageRequestBuilder
        int width = 500, height = 500;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri_imgs[pos])
                .setAutoRotateEnabled(true) //Ativando auto-rotação
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//NNível de requisição completo
                .setResizeOptions(new ResizeOptions(width, height)) //Largura e altura redefinidas
                .setImageType(ImageRequest.ImageType.SMALL) //Mudando o tipo da imagem
                .setPostprocessor(mesh) //Setando a componente para pos-processar a decodificação da imagem
                .build();

        //Usando a classe PipelineDraweeController para controlar o carregamento das imagens
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setOldController(img.getController())
                .setImageRequest(request)
                .build();

        img.setController(controller);

        return l_layout;

    }

    @Override
    public void destroyItem(ViewGroup container, int pos, Object view)
    {
        View v = (View) view;
        container.removeView(v); //Removendo view da galeria de imagens
    }
}
