package com.example.vesti;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    private static final String SHARED_PREFERENCES_PREFIX="MainActivitySharedPreferencesPrefix";
    private static final String SHARED_PREFERENCES_KATEGORIJA="kategorija";
    private static final String SHARED_PREFERENCES_ENG="eng";

    public static ApiInterface apiInterface;
    public static  ArrayList<Category> categories=new ArrayList<>();
    public static  ArrayList<Post> posts=new ArrayList<>();
    public static int category;
    public static boolean eng;
    private View divider;
    private RecyclerView listView;
    private RecyclerView listView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readPreferences();
        Switch switch_eng=(Switch) findViewById(R.id.switch1);
        divider=findViewById(R.id.divider2);
        apiInterface=ApiClient.getClient(ApiInterface.class);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        listView=(RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        listView2=(RecyclerView) findViewById(R.id.listView2);
        listView2.setLayoutManager(layoutManager2);


        switch_eng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b==false){
                    eng=false;
                    getCategories(apiInterface,MainActivity.this);
                    getPosts(apiInterface,MainActivity.this,category);

                    Runnable r=new Runnable() {
                        @Override
                        public void run() {
                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories,listView2,divider,apiInterface);
                    listView.setAdapter(categoryAdapter);
                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts);
                    listView2.setAdapter(vestAdapter);
                        }
                    };
                    Handler h=new Handler();
                    h.postDelayed(r,1500);

                }
                else{
                eng=true;
                    getCategoriesENG(apiInterface, MainActivity.this);
                    getPostsENG(apiInterface,MainActivity.this,category);
                    Runnable r=new Runnable() {
                        @Override
                        public void run() {
                    divider.setBackgroundColor(Color.parseColor(categories.get(category).getColor()));
                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories,listView2,divider,apiInterface);
                    listView.setAdapter(categoryAdapter);
                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts);
                    listView2.setAdapter(vestAdapter);
                }
            };
            Handler h=new Handler();
            h.postDelayed(r,1500);


        }


            }
        });



        if(eng==true){switch_eng.setChecked(true);}
        else{
            getCategories(apiInterface,MainActivity.this);

            getPosts(apiInterface,this,category);

            Runnable r=new Runnable() {
                @Override
                public void run() {
                    divider.setBackgroundColor(Color.parseColor(categories.get(category).getColor()));
                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories,listView2,divider,apiInterface);
                    listView.setAdapter(categoryAdapter);
                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts);
                    listView2.setAdapter(vestAdapter);
                }
            };
            Handler h=new Handler();
            h.postDelayed(r,1500);

        }

        }






    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }


    private void savePreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_PREFIX,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCES_KATEGORIJA,category);
        editor.putBoolean(SHARED_PREFERENCES_ENG,eng);
        editor.commit();
    }

    private void readPreferences(){

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_PREFIX,0);
        category=sharedPreferences.getInt(SHARED_PREFERENCES_KATEGORIJA,0);
        eng=sharedPreferences.getBoolean(SHARED_PREFERENCES_ENG,false);
    }


    protected  void getCategories(ApiInterface apiInterface,Context mcontext) {



        Call<ArrayList<Category>> callLanguage = apiInterface.getCategories();
        final Dialog dialog = LoadDialog.loadDialog(MainActivity.this);
        dialog.show();

         callLanguage.enqueue(new Callback<ArrayList<Category>>()

        {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    categories=response.body();
                }
                else{
                    Toast.makeText(MainActivity.this, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

    }







    protected  void getCategoriesENG(ApiInterface apiInterface, final Context mcontext) {




        Call<ArrayList<Category>> callLanguage = apiInterface.getCategoriesENG();
        final Dialog dialog = LoadDialog.loadDialog(MainActivity.this);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Category>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){

                    categories=response.body();
                }
                else{
                    Toast.makeText(mcontext, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(mcontext, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });


    }




    protected static  void getPosts(ApiInterface apiInterface, final Context mcontext,int category_id) {



        Call<ArrayList<Post>> callLanguage = apiInterface.getPosts(category_id);
        final Dialog dialog = LoadDialog.loadDialog(mcontext);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Post>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    posts=response.body();
                }
                else{
                    Toast.makeText(mcontext, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(mcontext, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

    }





    protected static void getPostsENG(ApiInterface apiInterface, final Context mcontext,int category_id) {



        Call<ArrayList<Post>> callLanguage = apiInterface.getPostsENG(category_id);
        final Dialog dialog = LoadDialog.loadDialog(mcontext);
        dialog.show();
        callLanguage.enqueue(new Callback<ArrayList<Post>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response)
            {
                if (dialog.isShowing())
                    dialog.dismiss();
                if(response.code() == HttpURLConnection.HTTP_OK && response.body() != null){
                    posts=response.body();
                }
                else{
                    Toast.makeText(mcontext, R.string.errorbaza, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t)
            {
                t.printStackTrace();
                Toast.makeText(mcontext, R.string.errornetwork, Toast.LENGTH_SHORT).show();
                if (dialog.isShowing())
                    dialog.dismiss();

            }
        });

    }




    }






//package com.example.vesti;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    private static final String SHARED_PREFERENCES_PREFIX="MainActivitySharedPreferencesPrefix";
//    private static final String SHARED_PREFERENCES_KATEGORIJA="kategorija";
//    private static final String SHARED_PREFERENCES_ENG="eng";
//
//    ApiInterface apiInterface;
//    public static  ArrayList<Category> categories1= new ArrayList<>();
//    public static  ArrayList<Category> categories2= new ArrayList<>();
//
//    public static  ArrayList<Post> posts1= new ArrayList<>();
//    public static  ArrayList<Post> posts2= new ArrayList<>();
//    public static  ArrayList<Post> posts3= new ArrayList<>();
//    public static  ArrayList<Post> posts4= new ArrayList<>();
//    public static int category;
//    public static boolean eng;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        readPreferences();
//        Switch switch_eng=(Switch) findViewById(R.id.switch1);
//        final View divider=findViewById(R.id.divider2);
//        apiInterface=ApiClient.getClient(ApiInterface.class);
//
//
//        LinearLayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        final RecyclerView listView=(RecyclerView) findViewById(R.id.listView);
//        listView.setLayoutManager(layoutManager);
//
//        LinearLayoutManager layoutManager2=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        final RecyclerView listView2=(RecyclerView) findViewById(R.id.listView2);
//        listView2.setLayoutManager(layoutManager2);
//
//        Category category1=new Category("najave","https://cdn2.iconfinder.com/data/icons/iconza/iconza_32x32_404040/right_arrow.png","#ff0000");
//        Category category2=new Category("previews","https://cdn2.iconfinder.com/data/icons/iconza/iconza_32x32_404040/right_arrow.png","#ff0000");
//        Category category3=new Category("izvestaji","https://cdn1.iconfinder.com/data/icons/Keyamoon-IcoMoon--limited/32/arrow-left.png","#3a5fcd");
//        Category category4=new Category("reports","https://cdn1.iconfinder.com/data/icons/Keyamoon-IcoMoon--limited/32/arrow-left.png.png","#3a5fcd");
//
//
//        categories1.add(category1);
//        categories2.add(category2);
//        categories1.add(category3);
//        categories2.add(category4);
//
//
//        Post post1=new Post("http://redstarbelgrade.rs/wp-content/uploads/2019/09/70281238_3202514999788439_8660722563640459264_n-1.jpg","Alijanc Arena, Bajern i fascinantni autsajder","18/09/2019","Ponovljen je uspeh ulaskom u Ligu šampiona, dva užarena letnja meseca su za nama. Za Crvenu zvezdu počinje ono za šta se borila u jeku turističke sezone. Neverovatan je uspeh dve godine zaredom doći do grupne faze Lige šampiona, a pritom dolaziš iz Srbije.\n" +
//                "\n" +
//                "Vladan Milojević i momci su prošli pravu golgotu kvalifikacija, redom izbacivali neugodnu ali taličnu litvansku Suduvu, finski HJK, pa prošli kroz mitsku penal seriju nadobudni Kopenhagen i na koncu švajcarskog prvaka , Jang bojs iz Berna.\n" +
//                "\n" +
//                "Nagrada je tu. Petostruki prvak Evrope i najtrofejniji nemački klub, Bajern iz Minhena, aktuelni vicešampion Evrope , Totenhem, kao i „bratski“ Olimpijakos.\n" +
//                "\n" +
//                "Žreb definitivno bolji nego onaj od prošle godine, ali jednako opasan. Ipak Liga šampiona je Liga šampiona i ona ne trpi bilo kakvo opuštanje, potcenjivanje i svako odsustvo pobedničkog mentaliteta.\n" +
//                "\n" +
//                "Vožnju kroz ovu evropsku jesen Zvezda počinje na velelepnoj „Alijanc Areni“ u Minhenu, izgrađene po najsavremenijim standardima za potrebe svetskog prvenstva u fudbalu 2006. godine. Arena prima 71 000 gledalaca i stadion je sa UEFA-inih „5 zvezdica“. Ako postoji bolji početak evropske kampanje u grupama, onda nek je to Minhen.\n" +
//                "\n" +
//                "Bajern je u padu od odlaska Gvardiole iz kluba. Kad se kaže u padu, misli se uglavnom da nije u onom najužem krugu favorita za osvajanje prestola Evrope. U Bundesligi su sedam puta uzastopno šampioni i jedina prava pretnja kroz ove godine im je dortmundska Borusija, a polako se pojavljuje i Lajpcig, koji može u godinama pred nama kontinuirano da pripreti ovom udarnom dvojcu nemačkog fudbala.\n" +
//                "\n" +
//                "Niko Kovač je u debitantskoj sezoni na klupi Bajerna iz Minhena, prošle godine osvojio duplu krunu, sjajnim foto finišom u prvenstvu prestigao konkurenta iz Dortmunda i okitio se 29. titulom prvaka Bundeslige.\n" +
//                "\n" +
//                "Bajern nije izgledao dobro cele prošle sezone, Kovaču je već na sredini prvenstva dobrano visio otkaz u vazduhu, ali su Rumenige i Henes ipak istrpeli hrvatskog stručnjaka, što im je ovaj vratio trofejima na kraju sezone. Ipak publika koja prati Bajern nije bila zadovoljna igrama kluba, pa su ovaj letnji prelazni rok iskoristili da osveže redove i naprave određene rezove.\n" +
//                "\n" +
//                "Riberi i Roben su napustili klub posle više od 10 godina provedenih u bavarskom gigantu, Humels se vratio u Borusiju Dortmund a u klub su stigli Ernandez iz Atletiko Madrida, Pavar iz Štutgarta, Perišić iz Intera kao i najveće pojačanje , Felipe Koutinjo iz Barselone na pozajmicu sa pravom otkupa.\n" +
//                "\n" +
//                "Bajern je dobio nove obrise, ali rezultati na početku sezone su i dalje tanki za Bajernove standarde. Posle 4. kola Bundeslige , bavarci se nalaze na 4. mestu sa 2 pobede i dva remija na samom startu sezone. Remizirali su protiv Herte u prvom kolu, kao i u derbiju u vikendu za nama protiv Lajpciga u Lajpcigu. Ubedljivo su pobedili Šalke na strani i Majnc kući.\n" +
//                "\n" +
//                "Kovač bi večeras trebao da izvede manje više najjači sastav koji ima na raspolaganju. Povrede Alabe i Gorecke, koji večeras neće igrati kompilkuju stvari Kovaču pred meč. Zamene za Gorecku su spremne, ali za Alabu i nema puno alternativa, pa bi mogli videti Bajern možda u 3-4-3 formaciji kao iznuđenoj, ali ako se pak odluči da ne manja standardnu 4-2-3-1 formaciju onda će doći do određenih pomeranja u ekipi. Možda vidimo Ernandeza na levom beku, Pavara na štoperu uz Zilea, a Kimiha na desnom beku.\n" +
//                "\n" +
//                "U veznom redu Alkantara i Toliso bi trebali imati prednost u odnosu na Havija Martineza na ovom meču. Koman, Perišić i Koutinjo bi trebalo startovati iza najboljeg strelca Levandovskog.\n" +
//                "\n" +
//                "Bajern je ekipa koja je ofanzivno dosta potentna sa puno talenta i potencijala da svakom protivniku na svetu da po nekoliko golova, ali defanzivna linija tima nije tako dobra kao ofanzivna i tu treba Zvezda da traži šansu.\n" +
//                "\n" +
//                "Veliki hendikep za Zvezdu je odsustvo pouzdanog levog beka Rodića. Njega će najverovatnije zameniti brazilac Žander. Marko Gobeljić se oporavio od povrede lože i trebao bi nastupiti večeras na poziciji desnog beka. Milunović i Degenek će imati pune ruke posla protiv Levandovskog i ostatka navale Bajerna.\n" +
//                "\n" +
//                "Ispred zadnje linije bi trebalo da dejstvuju Kanjas i Jovančić, a u završnoj trećini Garsija, Van La Para i „nemački Mesi“, Marko Marin. U špicu će najverovatnije prednost dobiti Pavkov umesto Boaćija.\n" +
//                "\n" +
//                "Na put u Minhen nisu otputovali pomenuti Rodić, kao i Tomane koji odlužuje suspenziju zbog crvenog kartona iz revanša sa Jang bojsem.\n" +
//                "\n" +
//                "Večerašnji meč sudi Škot Bobi Maden a utakmica počinje u 21 čas. Direktan prenos je obezbeđen na RTS 1 kanalu.\n");
//
//        Post post2=new Post("http://redstarbelgrade.rs/wp-content/uploads/2019/09/70281238_3202514999788439_8660722563640459264_n-1.jpg","Allianz Arena, Bayern and the fascinating outsider","18/09/2019","The success of entering the Champions League is repeated, two hot summer months are behind us. For the Red Star, what it has fought for in the heyday of the tourist season begins. It is an incredible success to reach the group stage of the Champions League two years in a row, and you come from Serbia.\n" +
//                "\n" +
//                "Vladan Milojevic and the boys passed the real golgos of qualifications, throwing out an embarrassing but talented Lithuanian Suduva, Finnish HJK, and then went through the mythical penalty series the outspoken Copenhagen and finally the Swiss champion, Young Boys from Bern.\n" +
//                "\n" +
//                "The reward is there. The five-time European champion and the most trophy German club, Bayern Munich, current vice-champion of Europe, Tottenham, as well as the \"fraternal\" Olympiakos.\n" +
//                "\n" +
//                "The draw is definitely better than the one from last year, but just as dangerous. Yet the Champions League is the Champions League and it does not suffer any relaxation, understatement and any absence of a winning mentality.\n");
//
//
//        Post post3=new Post("http://redstarbelgrade.rs/wp-content/uploads/2019/09/kkyveyda-peristeri-768x563.jpg","Košarkaši sigurni i protiv Peristerija","19/09/2019","Košarkaši Crvene zvezde pobedili su u još jednoj pripremnoj utakmici , ovoga puta protiv Peristerija rezultatom 86:77 86:77 (19:21,19.20, 22:16, 26:20). Tokom cele utakmice viđena je rezultatska klackalica, da bi četa Milana Tomića serijom 10:0 krajem poslednje četvrtine došla do pobede. Najraspoloženiji na ovom meču bio je Lorenzo Braun sa 22 poena.\n" +
//                "KK Crvena zvezda mts: Čović 4, L.Braun 22, Perperoglu 12, Davidovac 3,Nenadić, Beron 12, Dobrić 7,Gist 16,Dženkins 4, Simanić 2, Jovanović, Odžo 7.\n" +
//                "\n" +
//                "Derik Braun je ovaj meč odmarao, dok su u Beogradu ostali Branko Lazić, Mo Faje i Ognjen Kuzmić.\n" +
//                "\n" +
//                "Poslednju proveru u pripremnom periodu ekipa Crvene zvezde ima u petak u Pireju kada će pred publikom u dvorani „Mira i Prijateljstva“ odigrati još jednu utakmicu sa Olimpijakosom. KK Crvena zvezda obezbedio je direktan prenos te utakmice koju će biti emitovana na TV „Arenasport“ počinje u 20.15 časova.");
//
//        Post post4=new Post("http://redstarbelgrade.rs/wp-content/uploads/2019/09/kkyveyda-peristeri-768x563.jpg","Convincing victory over Peristeri","19/09/2019","The Red Star basketball players won another preparatory game, this time against Peristeria with a score of 86:77 86:77 (19: 21,19.20, 22:16, 26:20). Throughout the game, a score rocker was seen, in order for Milan Tomic's 10-0 series to win at the end of the last quarter. Lorenzo Brown with 22 points was the best player in this match.\n" +
//                "BC Red Star mts: Covic 4, L. Braun 22, Perperoglu 12, Davidovac 3, Nenadic, Beron 12, Dobric 7, Gist 16, Jenkins 4, Simanic 2, Jovanovic, Odzo 7.");
//
//
//
//
//
//
//
//
//
//
//
//
//        posts1.add(post1);
//        posts1.add(post1);
//        posts2.add(post2);
//        posts2.add(post2);
//        posts3.add(post3);
//        posts3.add(post3);
//        posts4.add(post4);
//        posts4.add(post4);
//
//
//
//        switch_eng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                if(b==false){
//
//                    eng=false;
//                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories1,listView2,divider,apiInterface);
//                    listView.setAdapter(categoryAdapter);
//                    if(category==0){
//                    VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts1);
//                    listView2.setAdapter(vestAdapter);}
//                    else {VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts3);
//                    listView2.setAdapter(vestAdapter);}
//
//
//                }
//                else{
//                    eng=true;
//                    divider.setBackgroundColor(Color.parseColor(categories2.get(category).getColor()));
//                    CategoryAdapter categoryAdapter=new CategoryAdapter(MainActivity.this,categories2,listView2,divider,apiInterface);
//                    listView.setAdapter(categoryAdapter);
//                    if(category==0){
//                        VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts2);
//                        listView2.setAdapter(vestAdapter);}
//                    else {VestAdapter vestAdapter=new VestAdapter(MainActivity.this,posts4);
//                        listView2.setAdapter(vestAdapter);}
//
//
//                }
//
//
//            }
//        });
//
//
//
//
//
//
//
//
//         if(eng==true){switch_eng.setChecked(true);}
//
//        else{
//
//
//            divider.setBackgroundColor(Color.parseColor(categories1.get(category).getColor()));
//            CategoryAdapter categoryAdapter=new CategoryAdapter(this,categories1,listView2,divider,apiInterface);
//            listView.setAdapter(categoryAdapter);
//            VestAdapter vestAdapter=new VestAdapter(this,posts1);
//            listView2.setAdapter(vestAdapter);
//
//        }
//
//    }
//
//
//
//
//
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        savePreferences();
//    }
//
//
//    private void savePreferences(){
//        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_PREFIX,0);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putInt(SHARED_PREFERENCES_KATEGORIJA,category);
//        editor.putBoolean(SHARED_PREFERENCES_ENG,eng);
//        editor.commit();
//    }
//
//    private void readPreferences(){
//
//        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_PREFIX,0);
//        category=sharedPreferences.getInt(SHARED_PREFERENCES_KATEGORIJA,0);
//        eng=sharedPreferences.getBoolean(SHARED_PREFERENCES_ENG,false);
//    }
//
//
//}
//
//


