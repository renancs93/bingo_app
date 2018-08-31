package br.edu.ifsp.scl.sdm.bingoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String NUMEROS_FULL_TAG = "NUMEROS_FULL_TAG";
    private final String NUMEROS_SORTEADOS_TAG = "NUMEROS_SORTEADOS_TAG";

    //private TextView todosNumSorteados;
    private Button btnSortearBola;
    private ImageButton btnRefresh;

    private Random geradorRandomico;

    private ArrayList<Integer> numeros = new ArrayList<Integer>();
    private ArrayList<Integer> numerosSorteados = new ArrayList<Integer>();

    private GridView gridView;
    GridAdapter adapter;

    private final int balls = 75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todosNumSorteados = findViewById(R.id.todosNumerosSorteados_TextView);
        btnSortearBola = findViewById(R.id.btn_sortearBola);
        btnRefresh = findViewById(R.id.btn_Refresh);

        //colocar um listener no botao para o evento click
        btnSortearBola.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

        geradorRandomico = new Random(System.currentTimeMillis());

        gridView = (GridView) findViewById(R.id.customLayoutBingo);

        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //salvar dados de estado dinâmicos
        outState.putIntegerArrayList(NUMEROS_FULL_TAG, numeros);
        outState.putIntegerArrayList(NUMEROS_SORTEADOS_TAG, numerosSorteados);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //resgatar o estado armazenado
        if (savedInstanceState != null){

            numeros = savedInstanceState.getIntegerArrayList(NUMEROS_FULL_TAG);
            numerosSorteados = savedInstanceState.getIntegerArrayList(NUMEROS_SORTEADOS_TAG);

            if (numerosSorteados != null){
                exibeNumerosSorteados();
            }
        }
    }

    //instancia todos os números do Bingo
    public void init(){

        for(int i=1 ; i<=balls ; i++){
            numeros.add(i);
        }

    }

    private void reiniciarBingo() {

        //numerosSorteados.removeAll(numerosSorteados);
        numerosSorteados.clear();
        numeros.clear();
        init();
        exibeNumerosSorteados();

    }

    public void sortearNumero(){

        if (!numeros.isEmpty()){
            int index = geradorRandomico.nextInt(numeros.size());//seleciona um index dentro do tamanho da lista
            int num = numeros.get(index);

            Toast.makeText(this, "Número Sorteado: "+ num, Toast.LENGTH_SHORT).show();

            atualizaNumerosSorteados(num);
        }
        else{
            ordenarListaInteirosAsc(numerosSorteados);
            exibeNumerosSorteados();
            Toast.makeText(this, "Todos os números já foram Sorteados!!\n BINGO!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void atualizaNumerosSorteados(int novoNum){

        numerosSorteados.add(novoNum); // add o numero sorteado ao novo array list

        for (int i=0 ; i<numeros.size() ; i++){

            if (numeros.get(i).equals(novoNum)){
                Log.v("NUM_REMOVIDO","REMOVIDO NUM: "+numeros.get(i));
                numeros.remove(i); //remove o numero que saiu da lista inicial
            }
        }

        exibeNumerosSorteados();

    }

    //Ordena de forma Crescente uma ArrayList de Inteiros
    public void ordenarListaInteirosAsc(ArrayList<Integer> arrayList){

        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

    }

    public void exibeNumerosSorteados(){

        //todosNumSorteados.setText(numerosSorteados.toString()); //exibe os numeros sorteados

        if (adapter == null){
            adapter = new GridAdapter(MainActivity.this, numerosSorteados);
            gridView.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_sortearBola:
                sortearNumero();
                break;
            case  R.id.btn_Refresh:
                reiniciarBingo();
                break;

        }

    }


}
