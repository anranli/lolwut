package lolwut;

/**
 * An item that powers up the Penguin.
 * @author Anran Li
 */
public class PowerUpItem extends Item{
	
	public PowerUpItem(double x, double y, double width, double height,
			String imagePath) {
		super(x, y, width, height, imagePath);
		setPointValue(0);
	}

	@Override
	public void activate(Penguin player) {
		player.powerUp();
	}
}
