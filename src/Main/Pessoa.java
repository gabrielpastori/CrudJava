package Main;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Pessoa {


    private int idPessoa;
    private String nome;
    private Date dataNasc;
    private double salarioMensal;


    public Pessoa() {
    }

    public Pessoa(int idPessoa,String nome,Date dataNasc,double salarioMensal){

        this.idPessoa=idPessoa;
        this.nome=nome;
        this.dataNasc=dataNasc;
        this.salarioMensal=salarioMensal;
    }
    public int getIdPessoa(){

        return idPessoa;
    }
    public void setIdPessoa(int idPessoa){

        this.idPessoa=idPessoa;
    }
    public String getNome(){

        return nome;
    }
    public void setNome(String nome){

        this.nome=nome;
    }
    public Date getDataNasc(){

        return dataNasc;
    }
    public void setDataNasc(Date dataNasc){

        this.dataNasc=dataNasc;
    }
    public double getSalarioMensal(){

        return salarioMensal;
    }
    public void setSalarioMensal(double salarioMensal){

        this.salarioMensal=salarioMensal;
    }
    @Override

    public String toString(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return String.valueOf(idPessoa)+";" +nome+";" +String.valueOf(formato.format(dataNasc))+";" +String.valueOf(salarioMensal);
    }
}
