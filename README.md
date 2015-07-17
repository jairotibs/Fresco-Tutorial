#Tutorial Fresco
Tutorial desenvolvido para a disciplina IF1001 do Centro de Informatica(CIn-UFPE) para mostrar o funcionamento da biblioteca Fresco.
##Fresco
<p>É uma biblioteca poderosa para carregar imagens da Internet, de armazenamento local e de recursos locais em aplicações Android.</p>
##Página Oficial
<p>A documentação oficial pode ser encontrada no seguinte link:</p>
<ul>
<li><a href="http://frescolib.org/">Fresco</a></li>
</ul>
## Adicionando Fresco ao seu projeto
### Android Studio
Edite o arquivo <b>build.gradle</b> e insira na seção <i>dependencies</i> a seguinte linha:
<div class="highlight"><pre><code class="language-groovy" data-lang="groovy"><span class="n">dependencies</span> <span class="o">{</span>
<span class="c1">// outras dependencias aqui</span>
  <span class="n">compile</span> <span class="s1">'com.facebook.fresco:fresco:0.5.3+'</span>
<span class="o">}</span>
</code></pre></div>
## Iniciando com o Fresco
### Permissão
<p>É necessário adicionar a permissão abaixo para fazer o download das imagens da Internet no arquivo <b>AndroidManifest.xml</b>:</p>
<div class="highlight"><pre><code class="language-xml" data-lang="xml">  <span class="nt">&lt;uses-permission</span> <span class="na">android:name=</span><span class="s">"android.permission.INTERNET"</span><span class="nt">/&gt;</span>
</code></pre></div>
## Exemplo utilizando o Fresco
<p>Antes de iniciar sua aplicação, é preciso inicializar a classe Fresco, seguindo o comando abaixo que deve estar antes do método <i>setContentView().</i></p>
<div class="highlight"><pre><code class="language-java" data-lang="java"><span class="n">Fresco</span><span class="o">.</span><span class="na">initialize</span><span class="o">(</span><span class="n">this</span><span class="o">);</span><span class="n"> //onde <i>this</i> é um <a href="http://developer.android.com/reference/android/content/Context.html">contexto</a> </span>
<span class="n">setContentView(R.layout.activity_tela_inicial);</span>
</code></pre></div>
<p>Em seu arquivo XML(activity_tela_inicial.xml), adicione um elemento customizado</p>
<div class="highlight"><pre><code class="language-xml" data-lang="xml"><span class="c"></span>
<span class="n">&lt;LinearLayout</span> 
    <span class="na">xmlns:android=</span><span class="s">"http://schemas.android.com/apk/res/android"</span>
    <span class="na">xmlns:fresco=</span><span class="s">"http://schemas.android.com/apk/res-auto"</span>
    <span class="na">android:layout_height=</span><span class="s">"match_parent"</span>
    <span class="na">android:layout_width=</span><span class="s">"match_parent"</span><span class="nt">&gt;</span>
</code></pre></div>
</code></pre></div>
<p>Em seguida, adicione o SimpleDraweeView ao layout:</p>
<div class="highlight"><pre><code class="language-xml" data-lang="xml"><span class="nt">&lt;com.facebook.drawee.view.SimpleDraweeView</span>
    <span class="na">android:id=</span><span class="s">"@+id/my_image"</span>
    <span class="na">android:layout_width=</span><span class="s">"match_parent"</span>
    <span class="na">android:layout_height=</span><span class="s">"match_parent"</span>
    <span class="na">fresco:placeholderImage=</span><span class="s">"@drawable/sapo"</span>
  <span class="nt">/&gt;</span>
</code></pre></div>
###DraweeController
<p>Para fazer um melhor controle de carregamento de imagens, é necessário definir um controlador da classe DraweeController, utilizado no arquivo <b>Tela_imagem.java</b>. Nesse exemplo, foi utilizado um gif.</p>

<div class="highlight"><pre><code class="language-java" data-lang="java"><span class="n">
Uri gif = Uri.parse(<a href="http://i.imgur.com/L32RQlY.gif">http://i.imgur.com/L32RQlY.gif</a>); 
 DraweeController dc; 
  dc = Fresco.newDraweeControllerBuilder()
  .setUri(gif) //Uri da imagem
  .setAutoPlayAnimations(true) 
  .setOldController(imagem.getController()) 
  .build();
 imagem.setController(dc);
</span> 
</code></pre></div>
<p>No arquivo <b>AdapterImagem.java</b>, define-se um componente para decodificar imagens (no código abaixo é realizado uma limiarização da imagem). Observação: Esse método não suporta imagens com animações (gif).</p>

<div class="highlight"><pre><code class="language-java" data-lang="java"><span class="n">
Postprocessor mesh = new BasePostprocessor() //Classe Postprocessor que não suporta imagens animadas.
{
            @Override
            public void process(Bitmap bitmap)
            {
                for (int x = 0; x &#60; bitmap.getWidth(); x = x + 1) {
                    for (int y = 0; y &#60; bitmap.getHeight(); y = y + 2) {

                        if(bitmap.getPixel(x,y) &#60; lim) //Verifica se o pixel da imagem é maior que o limiar fornecido pelo usuário
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
</span> 
</code></pre></div>
###ImageRequestBuilder
<p align="justify">Usa-se a classe ImageRequestBuilder quando for requisitar múltiplas imagens da URL. O método setLowestPermittedRequestLevel() segue alguns processos onde é feito a procura de uma imagem. O primeiro processo verifica o bitmap cache. Caso encontrado, é retornado. O segundo verifica o cache da memória. Se encontrado, decodifica a imagem e volta. O terceiro verifica o disco cache. Caso encontrado, a carga do disco é decodificada e retornada. No quarto processo, a imagem é baixada, redimensionada ou rotacionada, se solicitado e decodificada. Esse último processo é mais lento. Os valores recebidos como parâmetro desse método são: </p>
<ul>
<li>BITMAP_MEMORY_CACHE</li>
<li>ENCODED_MEMORY_CACHE</li>
<li>DISK_CACHE</li>
<li>FULL_FETCH</li>
</ul>
</p>
<div class="highlight"><pre><code class="language-java" data-lang="java"><span class="n">
int width = 500, height = 500;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri_imgs[pos])
                .setAutoRotateEnabled(true)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH) 
                .setResizeOptions(new ResizeOptions(width, height)) 
                .setImageType(ImageRequest.ImageType.SMALL)
                .setPostprocessor(mesh) 
                .build();
</span> 
</code></pre></div>

###PipelineDraweeController
<p>A classe PipelineDraweeController é uma outra maneira de fazer o controle de carregamento de imagens, assim como a classe DraweeController. A diferença é que ela utiliza também outras threads e armazena tudo em três caches.</p>
<div class="highlight"><pre><code class="language-java" data-lang="java"><span class="n">
PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setOldController(img.getController())
                .setImageRequest(request)
                .build();

        img.setController(controller);
</span> 
</code></pre></div>

<img src="http://www.cin.ufpe.br/~jts2/imagens_limiar/In%edcio.png" height="400" width="300">
<img src="http://www.cin.ufpe.br/~jts2/imagens_limiar/Paris_limiar.png" height="400" width="300">
<img src="http://www.cin.ufpe.br/~jts2/imagens_limiar/ferrari_limiar.png" height="400" width="300">
<img src="http://www.cin.ufpe.br/~jts2/imagens_limiar/macaco_limiar.png" height="400" width="300">
