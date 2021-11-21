
public class ClearButton extends Button implements DormGUI {
	
	public ClearButton(float x, float y, PApplet processing) {
		super(x,y,processing);
		label = "Clear Room";
	}
	
	@Override
	public void mouseDown(Furniture[] furniture) {
		if (this.isMouseOver()) {
			for (int i = 0; i < furniture.length; ++i) {
				if (furniture[i] != null) {
					furniture[i] = null;
				}
			}
		}
	}
}
