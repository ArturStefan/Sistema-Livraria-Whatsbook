/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionarios;

import static Funcionarios.Desconto.total;
import ValidarCPF.ValidaçãoCPF;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aluno
 */
public class Compras extends javax.swing.JFrame {

     private int par, i;
    private float n, n2, troco, res;
    //final DecimalFormat df2 = new DecimalFormat();
    
    //variaveis da tabela forma de pagamento
    private String cred, deb, dinh; 
    private Double entrada, resto, v_parc;
    private int  n_parc;
    
    private PesquisarClientes pclt;
    private GerenteDesconto desc;
    
    int nidvenda, contador, novoidvenda;
    int c = 0;
    
     //criando variavel do usuario para passar para esta tela tela
    static protected int id_vendedor;
    static protected String cpf_vendedor, nome_vendedor;
   
    // mandar para o banco de dados da nota fiscal da compra
    String  datavenda, nome_cliente, cpf_cliente;
    Double valor_total_nota;
    int idnota, cod_vendedor, idvenda_clt;
    float vtotal;
    
    String cpf;
    private final DecimalFormat df2 = new DecimalFormat();
    
    //gerar numero de idvenda_clt automatico
    void idvenda(){
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            Statement stm = con.createStatement();
           ResultSet res = stm.executeQuery("SELECT * FROM notafiscal");

            nidvenda=0;
            while (res.next())
            {
                nidvenda = res.getInt("idvenda_clt");
            }
            jTidcompra.setText(""+(c+1));
            c++;
            contador = c;
            
            novoidvenda = nidvenda + c;
            
            jTidcompra.setText(String.valueOf(novoidvenda));

            con.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Compras() {
        
        initComponents();
        
        df2.applyPattern("####0.00");
        
        par = 0;
        n = 0;
        n2 = 0;
        i = 0;
        res = 0;
        troco = 0;
        
        jTcpfcliente.grabFocus();
        
        jTcpfvendedor.setVisible(true);
        jTidcompra.setVisible(true);
       
        
        Date relogio = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        formatador.format( relogio );
        jTdatacompra.setText(formatador.format( relogio ));
        
        desc = new GerenteDesconto();
        
        pclt = new PesquisarClientes();
        
        //desabilita botões pesquisar, desconto e finalizar venda ate digitar cpf
        habilitabotoes(false);
        
        jTcodvendedor.setText(SenhadeUsuario.id);
        jTcpfvendedor.setText(SenhadeUsuario.cpf);
        jTnomevendedor.setText(SenhadeUsuario.nome);
        
        //gerar idvenda_clt automatico
        idvenda();
        jTidcompra.setVisible(false);
        
        // Dados da tabela pagamento de compras
       pagamento(false);
       
       // ativa itens da tabela de pagamento apos selecionar forma de pagamento
       jTextField1.setVisible(false);
       jTextField2.setVisible(false);
       jLabel10.setVisible(false);
       jLabel11.setVisible(false);
       jLabel9.setVisible(false);
       jLabel13.setVisible(false);
       jLabel14.setVisible(false);
       jComboBox1.setVisible(false);
      
   }
 
    //desativa tabele de forma de pagamento
    public void pagamento(boolean condicao){
        
       jComboBox1.setEnabled(condicao);
        jLabel2.setEnabled(condicao);
        jLabel3.setEnabled(condicao);
        jLabel5.setEnabled(condicao);
        jLabel8.setEnabled(condicao);
        jLabel12.setEnabled(condicao);
        jRadioButton1.setEnabled(condicao);
        jRadioButton2.setEnabled(condicao);
        jRadioButton3.setEnabled(condicao);
        jButton3.setEnabled(condicao);
        jPanel2.setEnabled(condicao);
    }
    
    
    //recebendo o valor do id de login
    public void setValor(int valorid)
    {
        int id_vend;
        id_vend = valorid;
       //jTcodvendedor.setText(""+id_vend);
       jTcodvendedor.setText(SenhadeUsuario.id);
       
       //muda as cores
       jPanel1.setBackground(new Color(11,158,255));
       jButton1.setBackground(new Color(255,255,255));
       jButton2.setBackground(new Color(255,255,255));
       jButton4.setBackground(new Color(255,255,255));
       jButton5.setBackground(new Color(255,255,255));
       

    }
    
    //recebendo o valor do cpf de login
    public void setValor(String valorcpf)
    {
        String cpf_vend;
        cpf_vend = valorcpf;
       //jTcpfvendedor.setText(""+cpf_vend);
       jTcpfvendedor.setText(SenhadeUsuario.cpf);
      
    }
    //recebendo o valor do nome de login
    public void Valor(String nomevalor)
    {
        String nome_vend;
        nome_vend = nomevalor;
       //jTnomevendedor.setText(""+nome_vend);
       jTnomevendedor.setText(SenhadeUsuario.nome);
    }
    
    // insere dados da compra no banco de dados
    public void concluir_compras(){
            if(jRadioButton1.isSelected()== true){
            
            idvenda_clt = Integer.parseInt(jTidcompra.getText());
            datavenda = jTdatacompra.getText();
            cpf_cliente = jTcpfcliente.getText();
            nome_cliente = jTnomecliente.getText();
            valor_total_nota = Double.parseDouble(totalpreco.getText().replace(",","."));
            nome_vendedor = jTnomevendedor.getText();
            cod_vendedor = Integer.parseInt(jTcodvendedor.getText());
            cpf_vendedor = jTcpfvendedor.getText();
            
            cred = "x";
            deb = "x";
            dinh = "Dinheiro";
            entrada = Double.parseDouble(jTextField1.getText().replace(",","."));
            resto = Double.parseDouble(jTextField2.getText().replace(",","."));
            v_parc = (0.00);
            n_parc = (0);
            
            }  
            
            if(jRadioButton2.isSelected()== true){
                
            idvenda_clt = Integer.parseInt(jTidcompra.getText());
            datavenda = jTdatacompra.getText();
            cpf_cliente = jTcpfcliente.getText();
            nome_cliente = jTnomecliente.getText();
            valor_total_nota = Double.parseDouble(totalpreco.getText().replace(",","."));
            nome_vendedor = jTnomevendedor.getText();
            cod_vendedor = Integer.parseInt(jTcodvendedor.getText());
            cpf_vendedor = jTcpfvendedor.getText();
            
            cred = "x";
            deb = "Debito";
            dinh = "x";
            entrada = (0.00);
            resto = (0.00);
            v_parc = (0.00);
            n_parc = (0);
            
            }
            
            if(jRadioButton3.isSelected()== true){
                
            idvenda_clt = Integer.parseInt(jTidcompra.getText());
            datavenda = jTdatacompra.getText();
            cpf_cliente = jTcpfcliente.getText();
            nome_cliente = jTnomecliente.getText();
            valor_total_nota = Double.parseDouble(totalpreco.getText().replace(",","."));
            nome_vendedor = jTnomevendedor.getText();
            cod_vendedor = Integer.parseInt(jTcodvendedor.getText());
            cpf_vendedor = jTcpfvendedor.getText();
            
            cred = "Credito";
            deb = "x";
            dinh = "x";
            entrada = (0.00);
            resto = (0.00);
            v_parc = Double.parseDouble(jLabel9.getText().replace(",","."));
            n_parc = Integer.parseInt((String) jComboBox1.getSelectedItem());
            
            }
            
             try
             {
               Class.forName("com.mysql.jdbc.Driver");
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
               Statement stm = con.createStatement();
               if(stm.executeUpdate("INSERT into notafiscal values('"+idnota+"','"+idvenda_clt+"','"+datavenda+"','"+cpf_cliente+"','"+nome_cliente+"','"+valor_total_nota+"','"+nome_vendedor+"','"+cod_vendedor+"','"+cpf_vendedor+"'"
                       + ",'"+cred+"','"+deb+"','"+dinh+"','"+entrada+"','"+resto+"','"+v_parc+"','"+n_parc+"')")!=0)
               {
                   JOptionPane.showMessageDialog(null,"Pagamento realizado com sucesso!!!!!","Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    jComboBox1.setSelectedItem(""+1);
                    
                    NotaFiscalCompra vNfisc=new NotaFiscalCompra();
                    vNfisc.setVisible(true);
                    
                    // Dados da tabela pagamento de compras
                    pagamento(false);
       
                    dispose();
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
                JOptionPane.showMessageDialog(null,"Erro ao concluir Compras.");
             }
       
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTnomecliente = new javax.swing.JTextField();
        jTdatacompra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jBdesconto = new javax.swing.JButton();
        totalpreco = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jTcpfcliente = new javax.swing.JFormattedTextField();
        jTnomevendedor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTcodvendedor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTcpfvendedor = new javax.swing.JTextField();
        jTidcompra = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Area de Vendas");
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(11, 158, 255));

        jLabel1.setText("CPF:");

        jLabel2.setText("Nome: ");

        jTdatacompra.setEditable(false);
        jTdatacompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Data");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Livro", "Cod. Venda", "Nome Livro", "Preço unit.", "Quantidade", "Total R$:"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(40);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(1).setMinWidth(40);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(2).setMinWidth(180);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(180);
        }

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("TOTAL DA COMPRA R$:");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/pesquisar.png"))); // NOI18N
        jButton1.setText("Buscar:");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Finalizar Venda");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setText("VOLTAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jBdesconto.setBackground(new java.awt.Color(255, 255, 255));
        jBdesconto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBdesconto.setText("Desconto");
        jBdesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBdescontoActionPerformed(evt);
            }
        });

        totalpreco.setEditable(false);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/alterar.png"))); // NOI18N
        jButton5.setText("Alterar Cadastro");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        try {
            jTcpfcliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Nome Vendedor");

        jLabel7.setText("Cod. Vendedor");

        jTcpfvendedor.setEditable(false);

        jTidcompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jPanel2.setBackground(new java.awt.Color(11, 158, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel2.setPreferredSize(new java.awt.Dimension(231, 300));

        jLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setText("R$ Entrada:");

        jTextField1.setText("0.00");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jLabel10.setText("R$ Troco:");

        jTextField2.setEditable(false);
        jTextField2.setText("0.00");

        jRadioButton1.setBackground(new java.awt.Color(11, 158, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Dinheiro");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setBackground(new java.awt.Color(11, 158, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Debito");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setBackground(new java.awt.Color(11, 158, 255));
        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Credito");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/confirmar.png"))); // NOI18N
        jButton3.setText("Realizar Pagamento");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Valor da Compra R$:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("R$:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Valor da Parcela");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("R$:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jRadioButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton2)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jRadioButton2))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTcpfcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jTidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jTnomevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 39, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5)
                                .addGap(100, 100, 100)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTcodvendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTdatacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTcpfvendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(278, 278, 278)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jBdesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalpreco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jTcpfcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTnomecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTnomevendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTcpfvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTcodvendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTdatacompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(58, 58, 58)))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalpreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(224, 224, 224)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBdesconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Buscar");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Desconto");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Finalizar Venda");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4.setText("Sair");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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

    public void habilitabotoes(boolean condicao){
        jButton2.setEnabled(condicao);
        jBdesconto.setEnabled(condicao);
        jButton5.setEnabled(condicao);
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        pagamento(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Funcionarios vFunc=new Funcionarios();
        dispose();
        vFunc.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        if(jTcpfcliente.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o Codigo CPF para proseguir!");
        }
        else
        {
        desc.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        if(jTcpfcliente.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o Codigo CPF para proseguir!");
        }
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MousePressed
        // TODO add your handling code here:
        
        
       
    }//GEN-LAST:event_jMenuItem3MousePressed

    private void jBdescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBdescontoActionPerformed
        // TODO add your handling code here:
          
        if(jTcpfcliente.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Preencha o Codigo CPF para proseguir!");
        }
        else
        {
           desc.setVisible(true);
           
           // aqui estou pegando o valor do campo totalpreço, que seria o valor total de uma venda e vou transportar para a tela de desconto
           desc.setValor(vtotal);
           
        }
    }//GEN-LAST:event_jBdescontoActionPerformed

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String CPF=jTcpfcliente.getText();
        
        if (ValidaçãoCPF.isCPF(CPF) == true){
        
        if(jTcpfcliente.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null, "Digite o numero do CPF para realizar a Pesquisa!");
        }
        else
        {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM vendas");
           
            cpf = jTcpfcliente.getText();
            while(res.next()) {
           
                if(res.getString("cpf").compareTo(cpf)==0)
                {
                    jTnomecliente.setText(res.getString("nome")); 
                    
                }

            }
 
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }
        
        //habilita botões pesquisar, desconto e finalizar venda após digitar cpf e pesquisar
        habilitabotoes(true);
        
        cpf = jTcpfcliente.getText();
        
         try
        {
            Date relogio = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            formatador.format( relogio );
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM vendas where cpf ='"+cpf+"' and datavenda ='"+formatador.format(relogio)+"'");
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setNumRows(0);
            vtotal=0;
            while (res.next())
            {
                vtotal= vtotal+res.getFloat("total_item");
                model.addRow(new Object[]
                {
                    res.getString("tombolivro"),
                    res.getString("idvenda"),
                    res.getString("nome_livro"),
                    res.getString("preco_unit"),
                    res.getString("unid_compra"),
                    res.getString("total_item")

                });
            }

            DecimalFormat df2 = new DecimalFormat();
            df2.applyPattern("####0.00");
            totalpreco.setText(df2.format(vtotal));
            jLabel8.setText(df2.format(vtotal));
            con.close();
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        }
    }
    else{
                JOptionPane.showMessageDialog(null, "CPF Inválido, digite um CPF válido.");
    }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void selecionaidcommpra(){
    
        int linha = jTable1.getSelectedRow();
        jTidcompra.setText(jTable1.getValueAt(linha, 1).toString());
    }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int texte=1;
        pclt.setTeste(texte);
        pclt.setVisible(true);
        dispose();
        
        // pego o valor que esta no campo "cpf", transformo em String e vou mandar para a Janela "PesquisarCliente = pclt"
       pclt.setCpf((jTcpfcliente.getText()));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        if(total!=0){
             df2.applyPattern("####0.00");
            totalpreco.setText(df2.format(total));
            jLabel8.setText(df2.format(total));
            
        }
           
        
    }//GEN-LAST:event_formWindowGainedFocus

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //seleciona id da compra
        selecionaidcommpra();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:

        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
       jComboBox1.setVisible(false);
        jTextField1.setVisible(true);
        jTextField2.setVisible(true);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel5.setVisible(true);
        jLabel9.setVisible(false);
        jTextField1.grabFocus();
        jPanel2.setVisible(true);
        jComboBox1.setSelectedItem(""+1);
  
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:

        jComboBox1.setVisible(false);
        jTextField1.setVisible(false);
        jTextField2.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel5.setVisible(true);
        jComboBox1.setSelectedItem(""+1);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jPanel2.setVisible(true);
        
        jTextField2.setText(""+0.00);
      
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
        jComboBox1.setVisible(true);
        jTextField1.setVisible(false);
        jTextField2.setVisible(false);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel5.setVisible(true);
        jLabel9.setVisible(true);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jLabel9.setVisible(true);
        jPanel2.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(true);
        jComboBox1.setVisible(true);
        
        jTextField2.setText(""+0.00);
      

        par = Integer.parseInt((String) jComboBox1.getSelectedItem());
        n = Float.parseFloat(jLabel8.getText().replace(",","."));

        res = n / par ;

        jLabel9.setText(df2.format(res));
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:
        
        
        // calcular o troco do cliente
        n = Float.parseFloat(jLabel8.getText().replace(",","."));
        n2 = Float.parseFloat(jTextField1.getText().replace(",","."));
      
        troco = n2 - n ;
        
        jTextField2.setText(df2.format(troco));
   
    }//GEN-LAST:event_jTextField1FocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        if(jRadioButton1.isSelected()== true || jRadioButton2.isSelected()== true || jRadioButton3.isSelected()== true)
        {
            concluir_compras();
        }
        else
        {
            
            JOptionPane.showMessageDialog(null, "Escolha a forma de pagamento!");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        par = Integer.parseInt((String) jComboBox1.getSelectedItem());
        n = Float.parseFloat(jLabel8.getText().replace(",","."));

        res = n / par ;

        jLabel9.setText(df2.format(res));
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Compras().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBdesconto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTcodvendedor;
    private javax.swing.JFormattedTextField jTcpfcliente;
    private javax.swing.JTextField jTcpfvendedor;
    private javax.swing.JTextField jTdatacompra;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTidcompra;
    private javax.swing.JTextField jTnomecliente;
    private javax.swing.JTextField jTnomevendedor;
    private javax.swing.JTextField totalpreco;
    // End of variables declaration//GEN-END:variables
}
