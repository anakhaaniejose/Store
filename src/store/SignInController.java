/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package store;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anakha Anie Jose
 */
public class SignInController implements Initializable {
    String em;
      @FXML
    private Button close;
    @FXML
    private TextField login_email;
     //@FXML
    //private TextField login_password;
@FXML
    private PasswordField login_password;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
     Connection con;
     PreparedStatement pst;
     ResultSet rs;
     
  @FXML
  void signInbtn(ActionEvent event) throws IOException {
        
       String email=login_email.getText();
      String password=login_password.getText();
       if(email.equals("")&&password.equals(""))
         JOptionPane.showMessageDialog(null,"Some fields are blank!!!");
      else
      {
         try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query3="select * from login where email=? and password=?";
          pst=con.prepareStatement(query3);
          pst.setString(1, email);
          pst.setString(2,password);
          rs=pst.executeQuery();
          if(rs.next())
          {
              JOptionPane.showMessageDialog(null,"Login Successful!!!!");
              em=email;
              Stage stage1=(Stage)close.getScene().getWindow();
     stage1.close();
              login_email.setText("");
               login_password.setText("");
               login_email.requestFocus();
               login_password.requestFocus();
               System.out.println(email);
               ChooseController.getema(email);
               FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("choose.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Sell/Buy");
        stage.show();
          }else{
//          String query1="insert into signup values(?,?)";
//         pst=con.prepareStatement(query1);
//        // pst.setString(1, name);
//          pst.setString(1, email);
//           pst.setString(2, password);
//           pst.execute();
//           
           JOptionPane.showMessageDialog(null,"Login Failed");
               login_email.setText("");
               login_password.setText("");
               login_email.requestFocus();
               login_password.requestFocus();

           
         }
           
           }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
    }
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
