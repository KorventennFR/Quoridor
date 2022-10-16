package quoridor.view;

import java.io.File;
import java.io.Serializable;

import quoridor.control.*;

/**
 * An abstract representation of what the menu UI must do
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public abstract class MenuUIView implements Serializable{

	/**
	 * The controller used to get data from and to send interactions to the model
	 */
	Controller controller;

	/**
	 * Creates the MenuUIView
	 * @param controller the controller of the application
	 */
	public MenuUIView(Controller controller) {
		this.controller = controller;
	}

	protected String[] getAllSaves() {
		File dir = new File("./saves/");
		File[] files = dir.listFiles();
		String[] ret = new String[1];
		ret[0] = "Pas de sauvegardes";
		if(files!=null) {
			ret = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				ret[i] = files[i].toString().substring(8);
			}
		}
		
		return ret;
	}
	
	/**
	 * Gets the controller used for the application
	 * @return the controller
	 */
	public Controller getController() {
		return this.controller;
	}

}