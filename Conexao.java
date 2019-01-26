package UTIL;

import java.sql.*;
import javax.swing.*;

public class Conexao {
    final private String driver = "com.mysql.jdbc.Driver";
    //vocês verão ainda como efetuar conexão com diversos bancos
    //final private String url = "jdbc:postgresql://192.168.2.252:5432/bo";
    final private String url = "jdbc:mysql://localhost/biblioteca";
    
    final private String usuario = "root";
    final private String senha = "";
    public Connection conexao; //responsavel por fazer a conexão com o banco (Conexao é o objeto e Connection é do tipo"Connection")
    public Statement statement; //responsável por abrir caminho até o meu local do banco de dados
    public ResultSet resultset; //armazena o resultado dos comandos sql
    
    public boolean conecta() {
	boolean result = true;
	try{
	   Class.forName(driver);
	   conexao = DriverManager.getConnection(url, usuario, senha);
	}catch(ClassNotFoundException Driver){
            JOptionPane.showMessageDialog(null, "Driver não localizado: "+Driver);
            result = false;	
	}catch(SQLException Fonte){
            JOptionPane.showMessageDialog(null, "Deu erro na conexão com o Fonte de dados: "+Fonte);
            result = false;	
	}
	return result;
    }
    
    public boolean desconecta() {
	boolean result = true;
	try{
            conexao.close();
            //JOptionPane.showMessageDialog(null, "banco Fechado");
	}catch(SQLException fecha){
            JOptionPane.showMessageDialog(null, "Não foi possível fechar o banco de dados: "+fecha.getMessage());
            result = false;
	}
        return result;
    }
    
    public boolean executarSQL(String sql)
    {
       try {
           PreparedStatement sentencia = conexao.prepareStatement(sql);
          sentencia.execute();
          return true;
       } catch (SQLException ex) {
            return false;
       }
    }
    
    public void executeSQLSelect(String sql)
    {
	try
	{
		statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultset = statement.executeQuery(sql);
	}
	catch(SQLException sqlex)
	{
		JOptionPane.showMessageDialog(null, "Não foi possível executar o comando SQL: "+sqlex+", o sql passado foi: "+sql);
	}	
    }

    public String resultset(String titulo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

