package hust.pms.view.staff;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import hust.pms.controller.HistoryController;
import hust.pms.controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HistoryDetailForm implements Initializable {

	private HistoryController hc = new HistoryController();
	
    @FXML
    private ImageView iv_lp_in;

    @FXML
    private ImageView iv_in;

    @FXML
    private ImageView iv_lp_out;

    @FXML
    private ImageView iv_out;
    
    @FXML
    private Button btClose;

    @FXML
    void btCloseAction(ActionEvent event) {
    	SceneController.getInstance().closeSceneWithStageRelatedButton(btClose);
    }
    
    private void loadImageView() {
    	
    	try {
    	} catch (NullPointerException npe) {
    		npe.printStackTrace();
    	}
    	try {
    		
    	} catch (NullPointerException npe) {
    		npe.printStackTrace();
    	}
    	try {
    		File lpIn = new File(hc.getURLImage(HistoryForm.historyID)[2]);
    		iv_lp_in.setImage(new Image(lpIn.toURI().toString()));
    	} catch (NullPointerException npe) {
    		npe.printStackTrace();
    	}
    	try {
    		
        	File lpOut = new File(hc.getURLImage(HistoryForm.historyID)[3]);
        	
        	iv_lp_out.setImage(new Image(lpOut.toURI().toString()));
    	} catch (NullPointerException npe) {
    		npe.printStackTrace();
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadImageView();		
	}
}
