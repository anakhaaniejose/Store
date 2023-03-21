package store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import store.Main;
import store.MyListener;
import store.Fruit;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import java.sql.Blob;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class MarketController implements Initializable {
    @FXML
    private Label leftpricelabel;
     @FXML
    private Label sellerlabel;
     @FXML
    private Label pricelabel;
     @FXML
    private Button close;
      @FXML
     ChoiceBox category;
     
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;
    
    @FXML
    private TextField quantity;

    @FXML
    private ScrollPane scroll;
     @FXML
    private Label qtytype;
    @FXML
    private GridPane grid;
    static String emai;
     Connection con,con1;
     PreparedStatement pst;
     ResultSet rs;
     PreparedStatement pst1,pst2;
     ResultSet rs1,rs2;
     String cat;
     static String cat1="all";
    public String New;
    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    
    public static void getcat(String ca,String ema)
    {
        //System.out.println(ca);
        cat1=ca;
        emai=ema;
        System.out.println(ema);
    }
     @FXML
    void exit_btn(ActionEvent event) {
        Platform.exit();

    }
     
   @FXML
    void choosecat(ActionEvent event) throws IOException {
   
    cat=(String) category.getValue();
    Stage stage1=(Stage)close.getScene().getWindow();
     stage1.close();
    MarketController.getcat(cat,emai);
    FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("market.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        //MarketController controller=fxmlloader.getController();
        stage.setTitle("Market");
        stage.show();
        
        
    

    }
    



    private void setChosenFruit(Fruit fruit) {
        fruitNameLable.setText(fruit.getName());
       // pricelabel.setText(Main.CURRENCY);
        fruitPriceLabel.setText(""+fruit.getPrice());
        sellerlabel.setText(fruit.getSeller());
        qtytype.setText(fruit.getqtytype());
        System.out.println(fruit.getqtytype());
        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }
     @FXML
    void Add_To_Cart(ActionEvent event) throws IOException {
      String fruitname=fruitNameLable.getText();
      Float price=Float.parseFloat(fruitPriceLabel.getText());
      String quantitytype=qtytype.getText();
      Float qty=Float.parseFloat(quantity.getText());
      String sellername=sellerlabel.getText();
      
         try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String querybig="select * from signup where Full_name=?";
          pst1=con.prepareStatement(querybig);
          pst1.setString(1, sellername);
          String sellemail="";
          rs1=pst1.executeQuery();
          while(rs1.next())
          {
             sellemail=rs1.getString("email");
          }
          String querycheck="select * from product where email=? and name=?";
          pst2=con.prepareStatement(querycheck);
          pst2.setString(1, sellemail);
          pst2.setString(2, fruitname);
          rs2=pst2.executeQuery();
          while(rs2.next())
          {
              float quantitycheck=rs2.getFloat("qty");
              if(quantitycheck<qty)
              {
                  qty=quantitycheck;
                  JOptionPane.showMessageDialog(null,"Specified quantity not available, adding the available quantity to cart!");
              }
          }
          String query3="insert into cart values(?,?,?,?,?,?,?)";
          pst=con.prepareStatement(query3);
           pst.setString(1, sellername);
           pst.setString(2, fruitname);
          pst.setString(3, quantitytype);
           pst.setFloat(4, qty);
           pst.setFloat(5, price);
           pst.setString(6, emai);
           pst.setString(7, sellemail);
           pst.execute();
           
         
          JOptionPane.showMessageDialog(null,"Successfully added to cart,view cart to see the items in cart");
           }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       
     
      }
    }
      @FXML
    void cart_btn(ActionEvent event) throws IOException {
        CartController.getemailfrommarket(emai);
        FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("cart.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Art To Cart");
        stage.show();
        

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        category.getItems().addAll("fruits","vegetables","cutlery","footwear","electronics");
        quantity.setText(""+0);
        /*try {
            boolean addAll = fruits.addAll(getData());
        } catch (IOException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query1;
          System.out.println(cat1);
          query1="select * from product";
          if(cat1.equals("all")==true){
              query1="select * from product";
           pst=con.prepareStatement(query1);
              
              
          }
          else
          {
              query1="select * from product where category=?";
              pst=con.prepareStatement(query1);
              pst.setString(1, cat1);
               
              
          }
         
          
          //pst.setString(1, New);
        // pst=con.prepareStatement(query1);
         rs=pst.executeQuery();
         while(rs.next())
         {
        String email1= rs.getString("email");
        String name= rs.getString("name");
        String category= rs.getString("category");
         String quantytpe=rs.getString("qtytype");
        float price= rs.getFloat("price");
        String url=(name+".png");
        fruit = new Fruit();
        fruit.setName(name);
        fruit.setPrice(price);
        fruit.setImgSrc(url);
        fruit.setqtytyp(quantytpe);
        fruit.setColor("EEDC82");
        String fullname="";
        String query2="select * from signup";
        pst1=con.prepareStatement(query2);
         rs1=pst1.executeQuery();
        while(rs1.next())
        {
         String email2= rs1.getString("email");
         if(email1.equals(email2)){
         fullname=rs1.getString("Full_name");
         }
         
        }
        fruit.setSeller(fullname);
        fruits.add(fruit);
        
          }
         
 
         }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
        
       

        if (fruits.size() > 0) {
            setChosenFruit(fruits.get(0));
            myListener = new MyListener() {
                public void onClickListener(Fruit fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fruits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void minus(ActionEvent event) {
        String qtysetstring=quantity.getText();
        float qtyset=Float.parseFloat(qtysetstring);
       qtyset=qtyset-1;
       quantity.setText(""+qtyset);
    }

    @FXML
    void plus(ActionEvent event) {
 String qtysetstring=quantity.getText();
        float qtyset=Float.parseFloat(qtysetstring);
       qtyset=qtyset+1;
       quantity.setText(""+qtyset);
    }


}
