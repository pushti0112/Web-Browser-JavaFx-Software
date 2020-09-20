package application;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.glyphfont.FontAwesome;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SampleController implements Initializable {

		@FXML
	    private TextField url;

	    @FXML
	    private ProgressBar prog;

	    @FXML
	    private WebView web;
	    
	    @FXML
	    private Button btnlock;
	    
	    @FXML
	    private ToggleButton btnbook;
	    
	    @FXML
	    private Button btnhis;

	    @FXML
	    private FontAwesomeIcon star;
	    
	    @FXML
	    private AnchorPane root;
	    
	    static AnchorPane mainroot;

	    Database db=new Database();
	    
	    WebEngine engine;
	    
	    String newv="";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		init();
		
		listner();
		
		onaction();
		
		
	}
	void init()
	{
		mainroot=root;
		
		engine=web.getEngine();
		
		engine.load("https://www.google.com/");
		prog.setVisible(false);
		prog.progressProperty().bind(engine.getLoadWorker().progressProperty());
		
		
				
		btnbook.setTooltip(new Tooltip("Bookmark this tab"));
	}
	void listner()
	{
		engine.locationProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				url.setText(newValue);
				
				newv=newValue;
				
				
				if(newValue.startsWith("https"))
				{
				
					btnlock.setTooltip(new Tooltip("Secured"));
					btnlock.setGraphic(new FontAwesome().create(FontAwesome.Glyph.LOCK).size(20));
				}
				else
				{
				
					btnlock.setTooltip(new Tooltip("Not Secured"));
					btnlock.setGraphic(new FontAwesome().create(FontAwesome.Glyph.INFO_CIRCLE).size(20));
				//	btnlock.setGraphicTextGap(new FontAwesome().create(FontAwesome.Glyph.INFO_CIRCLE).setId("in"));
					
				}
				
				
			}	
		});;
					
					
	prog.progressProperty().addListener(new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			// TODO Auto-generated method stub
			star.setFill(Color.TRANSPARENT);
			if(prog.getProgress()==1.0)
			{
				prog.setVisible(false);
				
				saveurl(newv);
				checkbookmark();
				
				
			}
		}
	});
		
	}
	
	void onaction()
	{
		url.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				prog.setVisible(true);
				mainroot.setVisible(false);
				web.setVisible(true);
				loadurl();
			}
		});
		
		btnbook.setOnAction(new EventHandler<ActionEvent>() {
			
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				
				if(btnbook.isSelected())
				{
					addbookmark();
					star.setFill(Color.STEELBLUE);
					btnbook.setTooltip(new Tooltip("Bookmarked"));
				}
				else if(!btnbook.isSelected())
				{
					removebookmark();
					star.setFill(Color.TRANSPARENT);
					btnbook.setTooltip(new Tooltip("Bookmark this tab"));
					
					
				}
			}
		});
		btnhis.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				web.setVisible(false);
				mainroot.setVisible(true);
				url.setText("chrome://history/");
				navigation.navigate("/application/history.fxml");
			}
		});
		
	}
	void checkbookmark()
	{
		if(newv!="")
		{
			try {
		//	System.out.println("inadd"+newv);
			ResultSet rs=db.getResultSet("select bookmark from bookmark where url='"+newv+"'");
			String bookmark=rs.getString("bookmark");
		//	System.out.println(bookmark);
			
			if(bookmark.equals("yes"))
			{
				removedupicateurl();
				db.Insert("insert into bookmark values('no','"+newv+"')");
				db.Insert("update history set bookmark='yes' where url='"+newv+"'");
				db.Insert("update bookmark set bookmark='yes' where url='"+newv+"'");
				star.setFill(Color.STEELBLUE);
				btnbook.setTooltip(new Tooltip("Bookmarked"));
			}
			else
			{
				removedupicateurl();
				db.Insert("insert into bookmark values('no','"+newv+"')");
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("error"+e.getMessage());
			}
		}
	}
	void addbookmark()
	{
		
		if(newv!="")
		{
			try {
			System.out.println("inadd"+newv);
			
			ResultSet rs=db.getResultSet("select bookmark from bookmark where url='"+newv+"'");
			String bookmark=rs.getString("bookmark");
		//	System.out.println(bookmark);
			
			if(bookmark.equals("no"))
			{
				db.Insert("update history set bookmark='yes' where url='"+newv+"'");
				db.Insert("update bookmark set bookmark='yes' where url='"+newv+"'");
				//System.out.println("updated");
			}
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("error"+e.getMessage());
			}
		}
	}
	void removebookmark()
	{
		if(newv!="")
		{
			try {
		//	System.out.println("inrem"+newv);
			ResultSet rs=db.getResultSet("select bookmark from bookmark where url='"+newv+"'");
			String bookmark=rs.getString("bookmark");
		//	System.out.println(bookmark);
			
			if(bookmark.equals("yes"))
			{
				db.Insert("update history set bookmark='no' where url='"+newv+"'");
				db.Insert("update bookmark set bookmark='no' where url='"+newv+"'");
				//System.out.println("updated");
			}
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("error"+e.getMessage());
			}
		}
	}
	void removedupicateurl()
	{
		if(newv!="")
		{
			try {
		//	System.out.println("inrem"+newv);
			ResultSet rs=db.getResultSet("select url from bookmark");
			
			
			while(rs.next())
			{
				if(rs.getString("url").equals(newv))
				{
				//	System.out.println(rs.getString("url"));
				//	db.Insert("delete from history where url='"+rs.getString("url")+"'");
					db.Insert("delete from bookmark where url='"+rs.getString("url")+"'");
					//System.out.println("deleted");
				}
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("error"+e.getMessage());
			}
		}
	}
	void saveurl(String newv)
	{
		
		//System.out.println(newv);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		//System.out.println(time);
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
		Date date1 = new Date();
		String time1 = sdf1.format(date1);
		//System.out.println(time1);
		
		if(newv!="")
		{
		
			//db.Insert("delete from bookmark where url='http://mytutorials.c1.biz/index.php'");
	//db.Insert("delete from history where bookmark='yes'");
	//db.Insert("delete from bookmark");
		//s	removedupicateurl();
			db.Insert("insert into history values('unchecked','no','"+newv+"','"+time+"','"+time1+"')");
			
			db.Insert("insert into bookmark values('no','"+newv+"')");
			System.out.println("inserted");
			}
		
	}
	void loadurl()
	{
		String urldata=url.getText();
		if(!urldata.isEmpty())
		{
			urldata=urldata.startsWith("http://")?urldata:"http://"+urldata;
			engine.load(urldata);
			
						
		}
		else
		{
			System.err.println("error...");
		}
	}
	
}

















