package br.com.senaijandira.funcionarios.viewmodel;

import android.arch.persistence.room.Embedded;

import br.com.senaijandira.funcionarios.model.Funcionario;

/**
 * Created by 17259211 on 24/09/2018.
 */

public class FuncionarioCargo { //essa classe é um VIEW MODEL, ela junta dados de várias tabelas, nesse caso duas

    @Embedded // diz que esse objeto é uma tabela do banco
    private Funcionario funcionario;

    private String cargo;



    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
