


   
package store;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class SignUpController implements Initializable {
  
     @FXML
    private Button close;
    
    @FXML
    private TextField signup_email;

    @FXML
    private TextField signup_name;

   // @FXML
    //private TextField signup_pass;
    @FXML
    private PasswordField signup_pass;

    Connection con;
     PreparedStatement pst;
     ResultSet rs;

    @FXML
    void signupbtn(ActionEvent event) throws IOException {
        //JOptionpane.showMessageDialogue(null,"Hi");
        //JOptionPane.showMessageDialog(null,"Hi");
        String name=signup_name.getText();
       String email=signup_email.getText();
      String password=signup_pass.getText();
       if(name.equals("")&&email.equals("")&&password.equals(""))
         JOptionPane.showMessageDialog(null,"Some fields are blank!!!");
      else
      {
         try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query3="select * from login where email=?";
          pst=con.prepareStatement(query3);
          pst.setString(1, email);
          rs=pst.executeQuery();
          if(rs.next())
          {
              JOptionPane.showMessageDialog(null,"Email already exist!!!Try logging in");
              signup_email.setText("");
               signup_pass.setText("");
               signup_name.setText("");
               signup_email.requestFocus();
               signup_pass.requestFocus();
               signup_name.requestFocus();
          }else{
          String query1="insert into signup values(?,?,?)";
         pst=con.prepareStatement(query1);
         pst.setString(1, name);
          pst.setString(2, email);
           pst.setString(3, password);
           pst.execute();
           
           String query2="insert into login values(?,?)";
           pst=con.prepareStatement(query2);
           pst.setString(1, email);
           pst.setString(2, password);
           pst.execute();
           
              JOptionPane.showMessageDialog(null,"Registration Successful!!!");
              Stage stage1=(Stage)close.getScene().getWindow();
     stage1.close();
              signup_email.setText("");
               signup_pass.setText("");
               signup_name.setText("");
               signup_email.requestFocus();
               signup_pass.requestFocus();
               signup_name.requestFocus();
               ChooseController.getema(email);
              FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("choose.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("sell/buy");
        stage.show();
           
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
       // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
