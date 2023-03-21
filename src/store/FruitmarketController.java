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

public class FruitmarketController implements Initializable {
    @FXML
    private TextField category;
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
    private GridPane grid;
     Connection con;
     PreparedStatement pst;
     ResultSet rs;
     
    public String New;
    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    
     @FXML
     void exit_btn(ActionEvent event) {
        Platform.exit();
     }
     @FXML
      void cart_btn(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("cart.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Art To Cart");
        stage.show();
        

    
    }
   @FXML
    void choosecat(ActionEvent event) throws IOException {
        
      New=category.getText();
      if(New.equals("vegetables")==true){
    FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("vegetables.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Vegetable Market");
        stage.show();
        
    }
       else if(New.equals("fruit")==true){
    FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("fruitmarket.fxml"));
        Parent root=(Parent)fxmlloader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Fruit Market");
        stage.show();
        
    }

    }
    
    //System.out.println(New);
    

    private List<Fruit> getData() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;
        try
         {
           Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3307/shopping_app","root","appukuttan123");
          String query1;
          String cat="fruit";
              query1="select * from product where category=?";
         
          
          
         pst=con.prepareStatement(query1);
         pst.setString(1, cat);
         rs=pst.executeQuery();
         while(rs.next())
         {
        String email= rs.getString("email");
        String name= rs.getString("name");
        String category= rs.getString("category");
        float price= rs.getFloat("price");
        String url=(name+".png");
        fruit = new Fruit();
        fruit.setName(name);
        fruit.setPrice(price);
        fruit.setImgSrc(url);
        fruit.setColor("EEDC82");
        fruits.add(fruit);
          }
         
 
         }catch(ClassNotFoundException | SQLException e)
           {
           
           System.out.println("Connection failed");
           System.out.println(e);
           
       }
        
       

        return fruits;
    }

    private void setChosenFruit(Fruit fruit) {
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(Main.CURRENCY + fruit.getPrice());
        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }
     @FXML
    void Add_To_Cart(ActionEvent event) throws IOException {
        
        

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            boolean addAll = fruits.addAll(getData());
        } catch (IOException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
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

}

