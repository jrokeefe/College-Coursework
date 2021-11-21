
public class CreateFurnitureButton extends Button implements DormGUI {
	private String type;
	
	public CreateFurnitureButton(String type, float x, float y, PApplet processing) {
		super(x,y,processing);
		this.type = type;
		String firstLetter = type.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();
		type = firstLetter + type.substring(1);
		label = "Create " + type;
	}
	
	/**
	 * This method will create a new Furniture object of type "sofa" under the right
	 * conditions
	 *
	 * @param the array of furniture objects
	 */
	
	@Override
	public void mouseDown(Furniture[] furniture) {
		for (int i = 0; i < furniture.length; ++i) {
			if (furniture[i] == null && this.isMouseOver()) { // finds the first null object in the array													// null
				furniture[i] = new Furniture(type, processing); // create a new Furniture object of type sofa at
				break; // Furniture object created, break the loop
			}
		}
	}
}
