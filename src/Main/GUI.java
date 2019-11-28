package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import tools.DateTextField;
import tools.ManipulaArquivo;
import java.sql.Date;
import java.text.SimpleDateFormat;
public class GUI extends JFrame {
    private Container cp;
    private JLabel lbIdPessoa = new JLabel("IDPESSOA");
    private JTextField tfIdPessoa= new JTextField(20);
    private JLabel lbNome = new JLabel("Nome");
    private JTextField tfNome= new JTextField(20);
    private JLabel lbDataNasc = new JLabel("DataNasc");
    DateTextField tfDataNasc= new DateTextField();
    private JLabel lbSalarioMensal = new JLabel("SalarioMensal");
    private JTextField tfSalarioMensal= new JTextField(20);
    private JButton btAdicionar = new JButton("Adicionar");
    private JButton btListar = new JButton("Listar");
    private JButton btBuscar = new JButton("Buscar");
    private JButton btAlterar = new JButton("Alterar");
    private JButton btExcluir = new JButton("Excluir");
    private JButton btSalvar = new JButton("Salvar");
    private JButton btCancelar = new JButton("Cancelar");
    private JButton btCarregarDados = new JButton("Carregar");
    private JButton btGravar = new JButton("Gravar");
    private JToolBar toolBar = new JToolBar();
    private JPanel painelNorte = new JPanel();
    private JPanel painelCentro = new JPanel();
    private JPanel painelSul = new JPanel();
    private JTextArea texto = new JTextArea();
    private JScrollPane scrollTexto = new JScrollPane();
    private JScrollPane scrollTabela = new JScrollPane();
    private String acao = "";
    private String chavePrimaria = "";
    private Controle controle = new Controle();
    private Pessoa pessoa = new Pessoa();
    String[] colunas = new String[]{"IdPessoa","Nome","DataNasc","SalarioMensal"};

    String[][] dados = new String[0][4];
    DefaultTableModel model = new DefaultTableModel(dados, colunas);
    JTable tabela = new JTable(model);
    private JPanel painel1 = new JPanel(new GridLayout(1, 1));
    private JPanel painel2 = new JPanel(new GridLayout(1, 1));
    private CardLayout cardLayout;

    public GUI() {
        String caminhoENomeDoArquivo = "DadosPessoa.csv";
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setTitle("CRUD Canguru - V6a");
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(painelNorte, BorderLayout.NORTH);
        cp.add(painelCentro, BorderLayout.CENTER);
        cp.add(painelSul, BorderLayout.SOUTH);
        cardLayout = new CardLayout();
        painelSul.setLayout(cardLayout);
        painel1.add(scrollTexto);
        painel2.add(scrollTabela);
        texto.setText("\n\n\n\n\n\n");
        scrollTexto.setViewportView(texto);
        painelSul.add(painel1, "Avisos");
        painelSul.add(painel2, "Listagem");
        painelNorte.setLayout(new GridLayout(1, 1));
        painelNorte.add(toolBar);
        painelCentro.setLayout(new GridLayout(3, 2));
        painelCentro.add(lbNome);
        painelCentro.add(tfNome);
        painelCentro.add(lbDataNasc);
        painelCentro.add(tfDataNasc);
        painelCentro.add(lbSalarioMensal);
        painelCentro.add(tfSalarioMensal);
        toolBar.add(lbIdPessoa);
        toolBar.add(tfIdPessoa);
        toolBar.add(btAdicionar);
        toolBar.add(btBuscar);
        toolBar.add(btListar);
        toolBar.add(btAlterar);
        toolBar.add(btExcluir);
        toolBar.add(btSalvar);
        toolBar.add(btCancelar);
        btAdicionar.setVisible(false);
        btAlterar.setVisible(false);
        btExcluir.setVisible(false);
        btSalvar.setVisible(false);
        btCancelar.setVisible(false);
        tfNome.setEditable(false);
        tfDataNasc.setEditable(false);
        tfSalarioMensal.setEditable(false);
        texto.setEditable(false);
        btCarregarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManipulaArquivo manipulaArquivo = new ManipulaArquivo();
                if (manipulaArquivo.existeOArquivo(caminhoENomeDoArquivo)) {
                    String aux[];
                    Pessoa t;
                    List<String> listaStringCsv = manipulaArquivo.abrirArquivo(caminhoENomeDoArquivo);
                    for (String linha : listaStringCsv) {
                        aux = linha.split(";");
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat sdfEua = new SimpleDateFormat("yyyy-MM-dd");
                        try{
                            t = new Pessoa(Integer.valueOf(aux[0]),String.valueOf(aux[1]),Date.valueOf(sdfEua.format(formato.parse(aux[2]))),Double.valueOf(aux[3]));
                            controle.adicionar(t);
                        }catch (Exception err){
                            System.out.println("Deu ruim "+err);
                        }
                    }
                    cardLayout.show(painelSul,"Listagem");
                }
            }
        });
        btGravar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                List<Pessoa> listaPessoa = controle.listar();
                List<String> listaPessoaEmFormatoStringCSV = new ArrayList<>();
                for (Pessoa t : listaPessoa) {
                   listaPessoaEmFormatoStringCSV.add(t.toString());
                }
                new ManipulaArquivo().salvarArquivo(caminhoENomeDoArquivo, listaPessoaEmFormatoStringCSV);
                System.out.println("gravou");
            }
        });
        btBuscar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                btAdicionar.setVisible(false);
                cardLayout.show(painelSul, "Avisos");
                scrollTexto.setViewportView(texto);
                if(tfIdPessoa.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(cp, "IDPESSOA nâo pode ser vazio");
                    tfIdPessoa.requestFocus();
                    tfIdPessoa.selectAll();
                }else{
                    chavePrimaria = tfIdPessoa.getText();
                    pessoa = controle.buscar(tfIdPessoa.getText());
                    if (pessoa== null) {
                        btAdicionar.setVisible(true);
                        btAlterar.setVisible(false);
                        btExcluir.setVisible(false);
                        tfNome.setText("");
                        tfDataNasc.setText("");
                        tfSalarioMensal.setText("");
                        texto.setText("Não encontrou na lista - pode Adicionar\n\n\n");
                    }else{
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        texto.setText("Encontrou na lista - pode Alterar ou Excluir\n\n\n");
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                        tfNome.setText(String.valueOf(pessoa.getNome()));
                        tfNome.setEditable(false);
                        tfDataNasc.setText(formato.format(pessoa.getDataNasc()));
                        tfDataNasc.setEditable(false);
                        tfSalarioMensal.setText(String.valueOf(pessoa.getSalarioMensal()));
                        tfSalarioMensal.setEditable(false);
                    }
                }
            }
        });
        btAdicionar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                acao = "adicionar";
                tfIdPessoa.setText(chavePrimaria);
                tfNome.setEditable(true);
                tfDataNasc.setEditable(true);
                tfSalarioMensal.setEditable(true);
                tfIdPessoa.setEditable(false);
                btSalvar.setVisible(true);
                btCancelar.setVisible(true);
                btBuscar.setVisible(false);
                btListar.setVisible(false);
                btAlterar.setVisible(false);
                btExcluir.setVisible(false);
                btAdicionar.setVisible(false);
                texto.setText("Preencha os atributos\n\n\n\n\n");
            }
        });
        btAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acao = "alterar";
                tfIdPessoa.setText(chavePrimaria);
                tfIdPessoa.setEditable(false);
                tfNome.requestFocus();
                btSalvar.setVisible(true);
                btCancelar.setVisible(true);
                btBuscar.setVisible(false);
                btListar.setVisible(false);
                btAlterar.setVisible(false);
                btExcluir.setVisible(false);
                texto.setText("Preencha os atributos\n\n\n\n\n");
                tfNome.setEditable(true);
                tfDataNasc.setEditable(true);
                tfSalarioMensal.setEditable(true);
            }
        });
        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                tfIdPessoa.setEditable(true);
                tfNome.setText("");
                tfNome.setEditable(false);
                tfDataNasc.setText("");
                tfDataNasc.setEditable(false);
                tfSalarioMensal.setText("");
                tfSalarioMensal.setEditable(false);
                tfIdPessoa.requestFocus();
                tfIdPessoa.selectAll();
                texto.setText("Cancelou\n\n\n\n\n");
            }
        });
        btSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (acao.equals("alterar")) {
                    Pessoa pessoaAntigo = pessoa;
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfEua = new SimpleDateFormat("yyyy-MM-dd");
                    try{
                        pessoa.setNome(String.valueOf(tfNome.getText()));
                        pessoa.setDataNasc(Date.valueOf(sdfEua.format(formato.parse(tfDataNasc.getText()))));
                        pessoa.setSalarioMensal(Double.valueOf(tfSalarioMensal.getText()));
                        controle.alterar(pessoa, pessoaAntigo);
                        texto.setText("Registro alterado\n\n\n\n\n");
                    }catch (Exception err){
                        System.out.println("Deu ruim "+err);
                    }
                }else{
                    pessoa= new Pessoa();
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfEua = new SimpleDateFormat("yyyy-MM-dd");
                    try{
                        pessoa.setIdPessoa(Integer.valueOf(tfIdPessoa.getText()));
                        pessoa.setNome(String.valueOf(tfNome.getText()));
                        pessoa.setDataNasc(Date.valueOf(sdfEua.format(formato.parse(tfDataNasc.getText()))));
                        pessoa.setSalarioMensal(Double.valueOf(tfSalarioMensal.getText()));
                        controle.adicionar(pessoa);
                        texto.setText("Foi adicionado um novo registro\n\n\n\n\n");
                        btAlterar.setVisible(true);
                        btExcluir.setVisible(true);
                    }catch (Exception err){
                        System.out.println("Deu ruim "+err);
                    }
                }
                btSalvar.setVisible(false);
                btCancelar.setVisible(false);
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                tfIdPessoa.setEditable(true);
                tfIdPessoa.requestFocus();
                tfIdPessoa.selectAll();
                tfNome.setText("");
                tfDataNasc.setText("");
                tfSalarioMensal.setText("");
                tfNome.setEditable(false);
                tfDataNasc.setEditable(false);
                tfSalarioMensal.setEditable(false);
            }
        });
        btListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Pessoa> lt = controle.listar();
                String[] colunas = {"IdPessoa","Nome","DataNasc","SalarioMensal"};
                Object[][] dados = new Object[lt.size()][colunas.length];
                String aux[];
                for (int i = 0; i < lt.size(); i++) {
                    aux = lt.get(i).toString().split(";");
                    for (int j = 0; j < colunas.length; j++) {
                        dados[i][j] = aux[j];
                    }
                }
                cardLayout.show(painelSul, "Listagem");
                scrollTabela.setPreferredSize(tabela.getPreferredSize());
                painel2.add(scrollTabela);
                scrollTabela.setViewportView(tabela);
                model.setDataVector(dados, colunas);

                btAlterar.setVisible(false);
                btExcluir.setVisible(false);
                btAdicionar.setVisible(false);
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfIdPessoa.setText(chavePrimaria);
                if (JOptionPane.YES_OPTION
                        == JOptionPane.showConfirmDialog(null,
                                "Confirma a exclusão do registro <Nome = " + pessoa.getNome() + ">?", "Confirm",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    controle.excluir(pessoa);
                    texto.setText("Excluiu o registro de " + pessoa.getIdPessoa() + " - " + pessoa.getNome() + "\n\n\n\n\n");                }
                else texto.setText("Não excluiu o registro de " + pessoa.getIdPessoa() + " - " + pessoa.getNome() + "\n\n\n\n\n");
                btBuscar.setVisible(true);
                btListar.setVisible(true);
                btExcluir.setVisible(false);
                btAlterar.setVisible(false);
                tfIdPessoa.requestFocus();
                tfIdPessoa.selectAll();
                tfIdPessoa.setText("");
                tfNome.setText("");
                tfDataNasc.setText("");
                tfSalarioMensal.setText("");
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //antes de sair, salvar a lista em disco
                btGravar.doClick();
                // Sai da classe
                dispose();
            }
        });
        btCarregarDados.doClick();
        setVisible(true);
    }
}
