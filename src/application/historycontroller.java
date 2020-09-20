package application;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

public class historycontroller implements Initializable {

	@FXML
    private AnchorPane apback;

    @FXML
    private AnchorPane apfront;

    @FXML
    private Label labhistory;

    @FXML
    private HBox hboxsearch;

    @FXML
    private Button btnsearch;

    @FXML
    private TextField txtsearch;
    
    @FXML
    private VBox vroot;
    
    @FXML
    private Button btncross;

    @FXML
    private Label labselected;

    @FXML
    private Button btndelete;

    @FXML
    private Button btncancel;


    @FXML
    private Pane hispane;

   
    @FXML
    private AnchorPane aphis;
    
    Database d=new Database();
    
    int c=0;
    
        
    List<List<String>> alldata;
    HBox h= new HBox(20);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//System.out.println("load history");
		
		List<String> date=d.getColumn("select date from history group by date");
		
		
		for(int i=0;i<date.size();i++)
		{
			alldata=d.getData("select * from history where date='"+date.get(i)+"'");
		
			vroot.setSpacing(10);
			VBox v=new VBox();
		//	HBox h= new HBox(20);
			HBox hd=new HBox();
			hd.setPadding(new Insets(20));
			HBox hl=new HBox();
			Line l=new Line();
			l.setEndX(1420);
			l.setStyle("-fx-stroke:#E2E2E2;");
	
			//v.setMinHeight(100);
			//v.setMaxHeight(939);
			v.setMinWidth(1420);
			v.setStyle("-fx-background-color: #FFFFFF;" + "-fx-border-color: #E2E2E2;");
		
			vroot.getChildren().add(v);
		//	System.out.println("added"+i);
			v.getChildren().add(hd);
			v.getChildren().add(hl);
			hd.getChildren().add(getlab(date.get(i)));
			hl.getChildren().add(l);
			
			for(int j=alldata.size()-1;j>=0;j--)
			{
				h= new HBox(10);
				h.setPadding(new Insets(10));
				v.getChildren().add(h);
				h.getChildren().add(check());
				h.getChildren().add(getlabt(alldata.get(j).get(4)));
				h.getChildren().add(getbtn(alldata.get(j).get(2)));
				h.getChildren().add(getbmk(alldata.get(j).get(2),alldata.get(j).get(1)));
				h.getChildren().add(btndot());
				
			}
			
			
		}
		
		
		
		
	}
	Label getlab(String nm)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy");
		Date d = new Date();
		String date = sdf.format(d);
		//System.out.println(date);
		
		Date y = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
		String ydate = sdf.format(y);
		
		Label lab=new Label();
		lab.setText(nm);
		lab.setPrefWidth(300);
		lab.setStyle("-fx-font-size: 18;");
		//lab.setPrefHeight();
	//	lab.setTextAlignment(TextAlignment.CENTER);
		lab.setAlignment(Pos.CENTER_LEFT);
		
		System.out.println(nm);
		if(date.equals(nm)){
			System.out.println("in today");
			date = "Today - " + nm;
			System.out.println(date);
			lab.setText(date);
		}
		else if(ydate.equals(nm))
		{System.out.println("in ystday");
			lab.setText("Yesterday - " + nm);
		}
		else
		{
			lab.setText(nm);
		}
		
		return lab;
	}
	Label getlabt(String nm)
	{
		
		
		Label lab=new Label();
		lab.setText(nm);
		lab.setPrefWidth(70);
		lab.setPrefHeight(20);
		lab.setAlignment(Pos.CENTER_LEFT);
		
	//	lab.setTextAlignment(TextAlignment.CENTER);
		
		return lab;
	}
	CheckBox check()
	{
		CheckBox chk=new CheckBox();
		chk.setAlignment(Pos.CENTER);
		chk.setPrefWidth(10);
		
		chk.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				if(chk.isSelected())
				{
				c++;
				hispane.setVisible(true);
				labselected.setText(c+" Selected");
				//System.out.println(c);
				}
				else
				{
					c--;
					if(c>0)
					{
						hispane.setVisible(true);
						labselected.setText(c+" Selected");
						//System.out.println(c);
					}
					else
					{
						hispane.setVisible(false);
						labselected.setText(c+" Selected");
						//System.out.println(c);
					}
					
				}
				
			}
		});
		
		return chk;
	}
	Button getbtn(String nm)
	{
		Button btn=new Button();
		btn.setText(nm);
		btn.setPrefWidth(1150);
		btn.setPrefHeight(20);
		btn.setStyle("-fx-background-color: transparent;");
	//	lab.setTextAlignment(TextAlignment.CENTER);
		btn.setAlignment(Pos.CENTER_LEFT);
		btn.setTextAlignment(TextAlignment.CENTER);
		return btn;
	}
	Button getbmk(String url,String bmk)
	{
		Button btn=new Button();
		Glyph f=new FontAwesome().create(FontAwesome.Glyph.STAR);
		f.setTextFill(Color.TRANSPARENT);
		btn.setGraphic(f.size(20));
		btn.setPrefWidth(24);
		btn.setPrefHeight(30);
		btn.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;");
	//	lab.setTextAlignment(TextAlignment.CENTER);
		btn.setAlignment(Pos.CENTER_LEFT);
		btn.setTextAlignment(TextAlignment.CENTER);
		
		if(bmk.equals("yes"))
		{
			f.setTextFill(Color.STEELBLUE);
		}
		
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				//btn.setVisible(false);
				f.setTextFill(Color.TRANSPARENT);
				d.Insert("update bookmark set bookmark='no' where url='"+url+"'");
				d.Insert("update history set bookmark='no' where url='"+url+"'");
				
				System.out.println("clicked");
				
			}
		});
		
		
		
		return btn;
	}
	
	Button btndot()
	{
		Button btn=new Button();
		Glyph f=new FontAwesome().create(FontAwesome.Glyph.ELLIPSIS_V);
	//	f.setTextFill(Color.STEELBLUE);
		f.setStyle("-fx-text-fill: #5F6368;");
		btn.setGraphic(f.size(20));
		btn.setPrefWidth(24);
		btn.setPrefHeight(30);
		btn.setStyle("-fx-background-color: transparent;" + "-fx-background-radius: 30;");
	//	lab.setTextAlignment(TextAlignment.CENTER);
		btn.setLayoutX(1500);
		btn.setTextAlignment(TextAlignment.CENTER);
		
		return btn;
		
	}

}
