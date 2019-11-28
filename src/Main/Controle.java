package Main;

import java.util.ArrayList;
import java.util.List;



public class Controle {


    private List<Pessoa> lista = new ArrayList<>();
    public Controle() {

    }
    public void limparLista(){
        lista.clear();
    }
    public void adicionar(Pessoa pessoa){
        lista.add(pessoa);
    }
    public List<Pessoa> listar(){
        return lista;
    }
    public Pessoa buscar(String idPessoa){
        for (int i = 0; i < lista.size(); i++) {
            if (String.valueOf(lista.get(i).getIdPessoa()).equals(idPessoa)) {
                return lista.get(i);
            }        }
        return null;
    }
    public void alterar(Pessoa pessoa, Pessoa pessoaAntigo){
        lista.set(lista.indexOf(pessoaAntigo),pessoa);

    }
    public void excluir(Pessoa pessoa) {
        lista.remove(pessoa);
    }
}
