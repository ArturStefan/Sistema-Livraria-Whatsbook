/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clientes;

import UTIL.Conexao;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aluno
 */
public final class ConsultaGenero extends javax.swing.JFrame {

    static public String nome, cpf, datavenda;
    public int controle=0;
    private CarrCompras carrcp;
    
     //criando variavel do usuario para passar para esta tela tela
    private float valor, nome_user, valores;
    private int valoridade;
    
    // enviando valores para tela deo carrinho de compras
    private String nomeusuario, cpfusuario;
    private static String  contcarr;
    
    String conservacao, idcad, datacad, preco_compra;

    private DefaultTableModel modelo;
    private JTable tabela;
    
    private int estoque, unidades, sup, qtd_estoque, qtd_comprada;
    
    String  id, nome_livro, titulo, autor, editora, idioma, genero, restgenero, lancamento, quantidade, preco_venda;
    Double total_item , preco_unit;
    int idvenda, idcliente, tombolivro, unid_compra;
    
    // atualizar banco de dados apos comprar quantidade de livros
    public void atualizar_estoque_banco(){
        
        unidades = Integer.parseInt((String) jComboBox1.getSelectedItem());
        estoque = Integer.parseInt(jLabel16.getText());
        qtd_estoque = estoque - unidades;        
        
        try
             {
               Class.forName("com.mysql.jdbc.Driver");
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
               Statement stm = con.createStatement();
               if (stm.executeUpdate(" UPDATE cadastro_livros SET quantidade='"+qtd_estoque+"' WHERE tombo='"+tombolivro+"'")!=0)
               {
               }
               else
               {
                    JOptionPane.showMessageDialog(null,"Dados não encontrados para Alteração!!!!","Atenção", JOptionPane.INFORMATION_MESSAGE);
               }
            }  
            catch(ClassNotFoundException e) 
            {
            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            }
            catch(SQLException e) 
            {      
            }
    }
    
    //conta total de itens do carrinho de compra por cpf e data
    public void totalcarrinho(){
        cpf = jTextField14.getText();
         try
        {
            Date relogio = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            formatador.format( relogio );
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            Statement stm = con.createStatement();
           ResultSet res = stm.executeQuery("SELECT count(nome) FROM vendas where cpf ='"+cpf+"' and datavenda ='"+formatador.format(relogio)+"'");
                     
            if(res.next())
            {
                //String total = res.getString("count(nome)");  
                //jLabel11.setText(""+total);
                String sum = res.getString("count(nome)");  
                jLabel11.setText(sum);
            }
            con.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    int i;
    int i2;
    void setCons(String genero) {
        i2=1;
        try
        {
             jTextField1.setVisible(false);
             jButton2.setVisible(false);
             jButton3.setVisible(false);
             jFormattedTextField1.setVisible(false);
             jButton4.setVisible(false);
             jButton5.setVisible(false); 

            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "")) {
                Statement stm = con.createStatement();
                ResultSet res = stm.executeQuery("SELECT * FROM cadastro_livros where genero='"+genero+"'");
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setNumRows(0);
                
                while (res.next())
                {
                    model.addRow(new Object[]
                    {
                        res.getString("tombo"),
                        res.getString("titulo"),
                        res.getString("autor"),
                        res.getString("editora"),
                        res.getString("idioma"),
                        res.getString("genero"),
                        res.getString("lancamento"),
                        res.getString("quantidade"),
                        res.getString("preco_venda")
                            
                    });
                }
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    void setLanc(String lancamento) {
        controle=5;
        jTextField1.setVisible(false);
        jButton2.setVisible(false);
        jButton4.setVisible(false);
        jButton5.setVisible(false);
        jButton3.setVisible(true);
        jFormattedTextField1.setVisible(true);
    }
    
    void setTit(String titulo) {
        controle=4;
        jButton3.setVisible(false);
        jFormattedTextField1.setVisible(false);
        jButton4.setVisible(false);
        jButton5.setVisible(false);
        jButton2.setVisible(true);
        jTextField1.setVisible(true);
    }
    
    void Autor(String autor) {
        controle=3;
        jButton3.setVisible(false);
        jFormattedTextField1.setVisible(false);
        jButton2.setVisible(false);
        jButton4.setVisible(false);
        jButton5.setVisible(true);
        jTextField1.setVisible(true);
        
    }
    
    void setEd(String editora) {
        controle=2;
        jButton3.setVisible(false);
        jFormattedTextField1.setVisible(false);
        jButton2.setVisible(false);
        jButton5.setVisible(false);
        jButton4.setVisible(true);
        jTextField1.setVisible(true);
  
    }

    public ConsultaGenero() {
        initComponents();

        jTextField14.setText(LoginCliente.cpf);
        
        jButton3.setVisible(false);
        jFormattedTextField1.setVisible(false);
        jButton2.setVisible(false);
        jButton5.setVisible(false);
        jButton4.setVisible(false);
        jTextField1.setVisible(false);
        
        jTextField11.setVisible(false);
        jTextField12.setVisible(false);
        jTextField14.setVisible(false);
        jTextField1.setVisible(false);
        jTextField15.setVisible(true);
        
        Date relogio = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        formatador.format( relogio );
        jTextField8.setText(formatador.format( relogio ));

        jTextField14.setText(LoginCliente.cpf);
        
        // Carregar carrinho de compras ok
        totalcarrinho();
        
        lancamento = jFormattedTextField1.getText();
        jTextField1.setVisible(false);
        jButton2.setVisible(false);
        
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);

        //chama a função desabilitar combobox
        desabilitaJcombobox(false);
        
        //Muda as cores da janelas e dos botões
        jPanel1.setBackground(new Color(11,158,255));
        jButton1.setBackground(new Color(255,255,255));
        jButton2.setBackground(new Color(255,255,255));
        jButton3.setBackground(new Color(255,255,255));
        jButton4.setBackground(new Color(255,255,255));
        jButton5.setBackground(new Color(255,255,255));
        jButton6.setBackground(new Color(255,255,255));
        jComboBox1.setBackground(new Color(255,255,255));
        
        carrcp = new CarrCompras();
    }   

    //pesquisa imagem do livro no banco de dados
     public void pesquisaimagem() throws SQLException{
        
        tombolivro = Integer.parseInt(jTextField13.getText());
        
        Conexao con = new Conexao();
        con.conecta();
        con.executeSQLSelect("SELECT * FROM cadastro_livros_fotos where cod = '"+tombolivro+"'");
        con.resultset.beforeFirst();
        while(con.resultset.next()){
            //int xCod = con.resultset.getInt("cod");
            byte[] imgBytes = ((byte[]) con.resultset.getBytes("foto"));
            ImageIcon pic = null;
            pic = new ImageIcon(imgBytes);
            Image scaled = pic.getImage().getScaledInstance(jLabel15.getWidth(), jLabel15.getHeight(), Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(scaled);
            jLabel15.setIcon(icon);//Adapta o tamanho da img para ficar do tamanho da label
  
        } 
        con.desconecta();
    }
     
    //desabilitar botão combobox quando não tiver nenhum livro selecionado
    public void desabilitaJcombobox(boolean condicao){
        jComboBox1.setEnabled(condicao);
    }
    
    //recebendo o valor do id de login
    public void setValor(int valor)
    {
       jTextField12.setText(LoginCliente.id);
    }
    
    //recebendo o valor do cpf de login
    public void setValor(String valores)
    {
       jTextField14.setText(LoginCliente.cpf);
    }
    
    //recebendo o valor do nome de login
    public void Valor(String nome_valor)
    {
       jTextField15.setText(LoginCliente.nome);
    }
    
    //recebendo o valor do id de login
    /**
     *
     * @param valoridade
     */
    public void Valor(int valoridade)
    {
       jLabel17.setText(LoginCliente.idade);
    }

    void setOrdemAlfabetica() {
        controle=1;
        try
        {
 
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM cadastro_livros ORDER BY titulo;");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            while (res.next())
            {
                model.addRow(new Object[]
                {
                    res.getString("tombo"),
                    res.getString("titulo"),
                    res.getString("autor"),
                    res.getString("editora"),
                    res.getString("idioma"),
                    res.getString("genero"),
                    res.getString("lancamento"),
                    res.getString("quantidade"),
                    res.getString("preco_venda"),
             
                });
            }
            
            con.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }  
    }

    //limpar campos para uma nova pesquisa
    public void limparcampos(){
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField13.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jLabel15.setIcon(null);
   
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Livros por Genero");
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tombo", "Titulo", "Autor", "Editora", "Idioma", "Gênero", "Ano Lançamento", "Quantidade", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMinWidth(150);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(6).setMinWidth(100);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMinWidth(80);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(8).setMinWidth(80);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(80);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(80);
        }

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/pesquisar.png"))); // NOI18N
        jButton2.setText("Pesquisar Titulo Livro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/pesquisar.png"))); // NOI18N
        jButton3.setText("Pesquisar por Ano");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/pesquisar.png"))); // NOI18N
        jButton4.setText("Pesquisar Editora");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/pesquisar.png"))); // NOI18N
        jButton5.setText("Pesquisar Autor");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setText("Titulo");

        jLabel2.setText("Autor");

        jLabel3.setText("Editora");

        jLabel4.setText("Idioma");

        jLabel5.setText("Gênero");

        jLabel6.setText("Ano Lançamento");

        jLabel7.setText("Quantidade");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Valor Unitário");

        jLabel9.setText("Valor Total");

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/add.png"))); // NOI18N
        jButton6.setText("Adicionar ao Carrinho");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/carrinho compras 36x36.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel11.setOpaque(true);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/sair 40x40 2.png"))); // NOI18N
        jLabel13.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel13AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel13MousePressed(evt);
            }
        });

        jTextField8.setEditable(false);

        jTextField11.setEditable(false);
        jTextField11.setText("0");

        jTextField12.setEditable(false);

        jLabel14.setText("Tombo");

        jTextField14.setEditable(false);

        jTextField15.setEditable(false);

        jTextField9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField9.setOpaque(true);

        jTextField10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField10.setOpaque(true);

        jTextField7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField7.setOpaque(true);

        jTextField6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField6.setOpaque(true);

        jTextField13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField13.setOpaque(true);

        jTextField2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField2.setOpaque(true);

        jTextField3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField3.setOpaque(true);

        jTextField4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField4.setOpaque(true);

        jTextField5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextField5.setOpaque(true);

        jLabel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel16.setOpaque(true);

        jLabel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel17.setOpaque(true);

        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(238, 238, 238)
                                    .addComponent(jLabel2)
                                    .addGap(74, 74, 74)
                                    .addComponent(jLabel3)
                                    .addGap(109, 109, 109)
                                    .addComponent(jLabel4)
                                    .addGap(68, 68, 68))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(jLabel14))
                                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(6, 6, 6)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(i2==1){
            GenerosLivros vGen=new GenerosLivros();
            vGen.setVisible(true);
            dispose();
            
        }
        else{
            Clientes vCli=new Clientes();
            vCli.setVisible(true);
            dispose();  
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(jTextField1.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o campo pesquisar para proseguir!\n","Erro", JOptionPane.ERROR_MESSAGE);
        }
        else
         {
        titulo = jTextField1.getText();
        
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "")) {
                Statement stm = con.createStatement();
                ResultSet res = stm.executeQuery("SELECT * FROM cadastro_livros where titulo lIKE '"+titulo+"%'");
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setNumRows(0);
                
                while (res.next())
                {
                    model.addRow(new Object[]
                    {
                        res.getString("tombo"),
                        res.getString("titulo"),
                        res.getString("autor"),
                        res.getString("editora"),
                        res.getString("idioma"),
                        res.getString("genero"),
                        res.getString("lancamento"),
                        res.getString("quantidade"),
                        res.getString("preco_venda")
                            
                    });
                }
            }
            
             //limpar campos para nova pesquisa
            limparcampos();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
         }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        if(jFormattedTextField1.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o campo pesquisar para proseguir!\n","Erro", JOptionPane.ERROR_MESSAGE);
        }
        else
         {
        lancamento = jFormattedTextField1.getText();
        jTextField1.setVisible(false);
        jButton2.setVisible(false);
 
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "")) {
                Statement stm = con.createStatement();
                ResultSet res = stm.executeQuery("SELECT * FROM cadastro_livros where lancamento ='"+lancamento+"'");
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setNumRows(0);
                
                while (res.next())
                {
                    model.addRow(new Object[]
                    {
                        res.getString("tombo"),
                        res.getString("titulo"),
                        res.getString("autor"),
                        res.getString("editora"),
                        res.getString("idioma"),
                        res.getString("genero"),
                        res.getString("lancamento"),
                        res.getString("quantidade"),
                        res.getString("preco_venda")
                            
                    });
                }
            }
            
            //limpar campos para nova pesquisa
            limparcampos();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
       }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(jTextField1.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o campo pesquisar para proseguir!\n","Erro", JOptionPane.ERROR_MESSAGE);
        }
        else
         {
        
        editora = jTextField1.getText();

         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            Statement stm = con.createStatement();
           ResultSet res = stm.executeQuery("SELECT * FROM cadastro_livros where editora lIKE '"+editora+"%'");
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);

            while (res.next())
            {
                model.addRow(new Object[]
                {
                    res.getString("tombo"),
                    res.getString("titulo"),
                    res.getString("autor"),
                    res.getString("editora"),
                    res.getString("idioma"),
                    res.getString("genero"),
                    res.getString("lancamento"),
                    res.getString("quantidade"),
                    res.getString("preco_venda")
                               
                });
            }
            con.close();
            
             //limpar campos para nova pesquisa
            limparcampos();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
         }   
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if(jTextField1.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o campo pesquisar para proseguir!\n","Erro", JOptionPane.ERROR_MESSAGE);
        }
        else
         {
        autor = jTextField1.getText();

         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "")) {
                Statement stm = con.createStatement();
                ResultSet res = stm.executeQuery("SELECT * FROM cadastro_livros where autor lIKE '"+autor+"%'");
                
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setNumRows(0);
                
                while (res.next())
                {
                    model.addRow(new Object[]
                    {
                        res.getString("tombo"),
                        res.getString("titulo"),
                        res.getString("autor"),
                        res.getString("editora"),
                        res.getString("idioma"),
                        res.getString("genero"),
                        res.getString("lancamento"),
                        res.getString("quantidade"),
                        res.getString("preco_venda")
                            
                    });
                }
            }
            
             //limpar campos para nova pesquisa
            limparcampos();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
         }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //seleciona livro na jtable
        selecionaLivro();
            
        try {
            //pesquisa imagem do livro
            pesquisaimagem();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGenero.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        //Habilitar o jcombobox
        desabilitaJcombobox(true);
            
        //muda contador de unidades para 1 toda vez que muda de livro
        jComboBox1.setSelectedItem(""+1);
            
        //jLabel15.setIcon(null);
     
    }//GEN-LAST:event_jTable1MouseClicked

    public void selecionaLivro(){
    
        int linha = jTable1.getSelectedRow();
        jTextField13.setText(jTable1.getValueAt(linha, 0).toString());
        jTextField2.setText(jTable1.getValueAt(linha, 1).toString());
        jTextField3.setText(jTable1.getValueAt(linha, 2).toString());
        jTextField4.setText(jTable1.getValueAt(linha, 3).toString());
        jTextField5.setText(jTable1.getValueAt(linha, 4).toString());
        jTextField6.setText(jTable1.getValueAt(linha, 5).toString());
        jTextField7.setText(jTable1.getValueAt(linha, 6).toString());        
        jTextField9.setText(jTable1.getValueAt(linha, 8).toString());
        
        jLabel16.setText(jTable1.getValueAt(linha, 7).toString());

        float vunit = Float.parseFloat(jTextField9.getText());
        jTextField10.setText(""+vunit);

    }
    
    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:
            //seleciona o livro para adicionar no carinho de compras
            selecionaLivro();
                
                try {
            //pesquisa imagem do livro
            pesquisaimagem();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultaGenero.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            //Habilitar o jcombobox
            desabilitaJcombobox(true);
                
            //muda contador de unidades para 1 toda vez que muda de livro
            jComboBox1.setSelectedItem(""+1);
                
            // jLabel15.setIcon(null);
    }//GEN-LAST:event_jTable1KeyReleased

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
       
        unidades = Integer.parseInt((String) jComboBox1.getSelectedItem());
        estoque = Integer.parseInt(jLabel16.getText());
        
        sup = unidades - estoque;
        
        if(unidades > estoque){
        
            JOptionPane.showMessageDialog(null, "Você ultrapassou o nosso estoque em " +sup+" unidades!");
        }
        else{
       
        unidades = Integer.parseInt((String) jComboBox1.getSelectedItem());
        double vunit = Double.parseDouble(jTextField9.getText());
        
        DecimalFormat df2 = new DecimalFormat();
        df2.applyPattern("####0.00");
        double total = unidades * vunit;
        jTextField10.setText(df2.format(total));
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int idade = Integer.parseInt(jLabel17.getText());
        int adulto = 18;
        int dif = adulto - idade;
        int restr = adulto - dif;
        
        restgenero = jTextField6.getText();
        if(restgenero.equals("Adulto") && (idade < adulto)){  
            
        JOptionPane.showMessageDialog(null, "Você não pode comprar este livro! "
                    + "Você tem apenas "+restr+" anos!\n","Erro", JOptionPane.ERROR_MESSAGE);
        
        }else{
            // verifica se o estoque não vai ficar negativo
            if(unidades > estoque){
        
            JOptionPane.showMessageDialog(null, "Você ultrapassou o nosso estoque em " +sup+" unidades!");
            }
        else{
                      
        if(jTextField2.getText().equals("")|| jTextField3.getText().equals("")|| jTextField4.getText().equals("")|| jTextField5.getText().equals("")|| jTextField6.getText().equals("")|| jTextField7.getText().equals("")|| jTextField8.getText().equals("")|| jTextField9.getText().equals("")|| jTextField10.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Pesquise um item antes para proseguir!");
        }
        else
         {
         if(jTextField11.getText().compareTo("") == 0 ||
           jTextField8.getText().compareTo("") == 0 ||
           jTextField12.getText().compareTo("") == 0 ||
           jTextField14.getText().compareTo("") == 0 ||
           jTextField15.getText().compareTo("") == 0 ||
           jTextField13.getText().compareTo("") == 0 ||
           jTextField2.getText().compareTo("") == 0 ||
           jTextField9.getText().compareTo("") == 0 ||
           jTextField10.getText().compareTo("") == 0 )
 
        {
            JOptionPane.showMessageDialog(null,"Erro!!! \n Verifique dados informados","Erro", JOptionPane.ERROR_MESSAGE);  
        }
        else
        {
            
            id = jTextField11.getText();
            datavenda = jTextField8.getText();
            idcliente = Integer.parseInt(jTextField12.getText());
            cpf = jTextField14.getText();
            nome = jTextField15.getText();
            tombolivro = Integer.parseInt(jTextField13.getText());
            nome_livro = jTextField2.getText();
            preco_unit = Double.parseDouble(jTextField9.getText());
            unid_compra = Integer.parseInt((String) jComboBox1.getSelectedItem());           
            total_item = Double.parseDouble(jTextField10.getText().replace(",","."));

             try
             {
               Class.forName("com.mysql.jdbc.Driver");
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
               Statement stm = con.createStatement();
               if(stm.executeUpdate("INSERT into vendas values('"+id+"','"+datavenda+"','"+idcliente+"','"+cpf+"','"+nome+"','"+tombolivro+"','"+nome_livro+"','"+preco_unit+"','"+unid_compra+"','"+total_item+"')")!=0)
               {
                   JOptionPane.showMessageDialog(null,"Item selecionado com sucesso!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
                   //atualiza o total de itens no carrinho de compras
                    totalcarrinho();
                    //atualiza o no banco de dados de livros apos selicionar item e adicionar no carrinho de compras
                    atualizar_estoque_banco();
                    //volta o combobox para 1 unidade apos adicionar item ao carrinho de compras
                    jComboBox1.setSelectedItem(""+1);
                    if(controle==1){
                        setOrdemAlfabetica();
                    }if(controle==2){
                           setEd(editora);
                    }if(controle==3){
                           Autor(autor);
                    }if(controle==4){
                        setTit(titulo);
                    }if(controle==5){
                        setLanc(lancamento);
                    }                    
               }
               else
               {
                   JOptionPane.showMessageDialog(null,"Erro no cadastro!!! \n","Erro", JOptionPane.ERROR_MESSAGE);  
               } 
             }
             catch(ClassNotFoundException e)
             {
                JOptionPane.showMessageDialog(null,"Erro!!! \n"+e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE); 
             }
             catch(SQLException e)
             {
                JOptionPane.showMessageDialog(null,"Erro ao Cadastrar! Erro ao Salvar item no Carrinho de Compras.");
             }
        }
       }
     }
    }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
       
    }//GEN-LAST:event_jButton6MousePressed

    private void jLabel13AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel13AncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel13AncestorAdded

    private void jLabel13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MousePressed
        // TODO add your handling code here:
        dispose();
        LoginCliente vLogin=new LoginCliente();
        vLogin.setVisible(true);
        
    }//GEN-LAST:event_jLabel13MousePressed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        
        int texte=1;
        carrcp.setTeste2(texte);
        carrcp.setVisible(true);
        carrcp.Valor(nomeusuario);
        carrcp.setValor(cpfusuario);
        
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MousePressed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        
        int texte=1;
        carrcp.setTeste2(texte);
        carrcp.setVisible(true);
        carrcp.Valor(nomeusuario);
        carrcp.setValor(cpfusuario);
        
        dispose();
        
    }//GEN-LAST:event_jLabel10MouseClicked

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        totalcarrinho();
        atualizar_estoque_banco();
        
    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaGenero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ConsultaGenero().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JLabel jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JLabel jTextField2;
    private javax.swing.JLabel jTextField3;
    private javax.swing.JLabel jTextField4;
    private javax.swing.JLabel jTextField5;
    private javax.swing.JLabel jTextField6;
    private javax.swing.JLabel jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel jTextField9;
    // End of variables declaration//GEN-END:variables

    private static class quant {

        public quant() {
        }
    }

}
