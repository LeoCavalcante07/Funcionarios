package br.com.senaijandira.funcionarios;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.funcionarios.db.AppDatabase;
import br.com.senaijandira.funcionarios.model.Cargo;
import br.com.senaijandira.funcionarios.model.Funcionario;
import br.com.senaijandira.funcionarios.viewmodel.FuncionarioCargo;

public class MainActivity extends AppCompatActivity {

    ListView lstViewFuncionarios;

    FuncionariosAdapter adapter;

    //variavel que serve com interface entre a aplicação e o os DAO
    AppDatabase appDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fazendo isntancia do banco
        appDB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "funcionarios.db").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        lstViewFuncionarios = findViewById(R.id.lstViewFuncionarios);

        //instanciar o adapter
        adapter = new FuncionariosAdapter(this);
        lstViewFuncionarios.setAdapter(adapter);

    }


    public void inserirDados(View v){

        //CARGOS FAKIE

        //o long serve para que ele retorne o id apos o insert
        long idAnalista = appDB.daoCargo().inserir(new Cargo("Analista"));

        //o long serve para que ele retorne o id apos o insert
        long idGerente = appDB.daoCargo().inserir(new Cargo("Gerente"));


        Cargo ger = new Cargo("Gerente");
        ger.setId(2);

        //FUNCIONARIOS FAKE
        Funcionario f1 = new Funcionario("João", (int)idAnalista);
        Funcionario f2 = new Funcionario("Mario", (int)idAnalista);
        Funcionario f3 = new Funcionario("José", (int)idAnalista);
        Funcionario f4 = new Funcionario("Maria", (int)idGerente);

        appDB.daoFuncionario().inserir(f1);
        appDB.daoFuncionario().inserir(f2);
        appDB.daoFuncionario().inserir(f3);
        appDB.daoFuncionario().inserir(f4);


    }


    public void carregarFuncionarios(View v){


        FuncionarioCargo[] funcionariosCargos = appDB.daoFuncionario().selecionarFuncionarioCargo();

        //adicionando os funcionarios ao adapter
        adapter.addAll(funcionariosCargos);


    }


    private class FuncionariosAdapter extends ArrayAdapter<FuncionarioCargo>{
        //CONSTRUTOR
        public FuncionariosAdapter(Context context){
            super(context, 0, new ArrayList<FuncionarioCargo>());


        }

        @NonNull
        @Override

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if(v == null){//SE v ESTIVER NULL É PORQUE ELE AINDA NÃO FOI INFLADO, LOGO ELE DEVE SER INFLADO
                v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_funcionario, parent, false);
            }
                                //o getItem pega o funcionario que tá no getView pela posição dele
            FuncionarioCargo func = getItem(position);

            TextView txtNomeFunc = v.findViewById(R.id.txtNomeFuncionario);
            TextView txtCardo = v.findViewById(R.id.txtTituloCargo);

            txtNomeFunc.setText(func.getFuncionario().getNome());
            txtCardo.setText(func.getCargo() + "");

            return v;
        }
    }
}
