package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class navigation {
	
	
	public static void navigate(String path)
	{
		Pane cmdpane=null;
		FXMLLoader fxmlloader=new FXMLLoader(Main.class.getResource(path));
		try {
			
			cmdpane=(Pane)fxmlloader.load();
			
			SampleController.mainroot.getChildren().clear();
			SampleController.mainroot.getChildren().add(cmdpane);
			
			//SampleController.mainroot.getChildren().setAll(cmdpane);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("error"+e.getMessage());
		}
	}

}
