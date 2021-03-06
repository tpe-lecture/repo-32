package tpe.oo.ueberschreiben;

import java.awt.Point;
import java.awt.event.MouseEvent;

import de.smits_net.games.framework.board.Board;
import de.smits_net.games.framework.image.AnimatedImage;
import de.smits_net.games.framework.image.StripedImage;
import de.smits_net.games.framework.sprite.AnimatedSprite;
import de.smits_net.games.framework.sprite.Direction;

/**
 * Ein Alien.
 */
public class Alien extends AnimatedSprite {

    /** Geschwindigkeit des Alien X-Richtung. */
    private static final int ALIEN_SPEED = 2;

    /**
     * Neues Alien anlegen.
     *
     * @param board das Spielfeld
     * @param startPoint Start-Position
     */
    public Alien(Board board, Point startPoint) {
        super(board, startPoint, BoundaryPolicy.JUMP_BACK,
                new AnimatedImage(50, 5,
                        "assets/spike_fist_move_strip5.png"));
        velocity.setVelocity(Direction.WEST, ALIEN_SPEED);
    }
    @Override
    public void mouseClicked() {
        // TODO Auto-generated method stub
        super.mouseClicked();
        explode();
       
    }
    /**
     * Alien explodieren lassen.
     */
    public void explode() {
        setActive(false);
        setImages(new AnimatedImage(20,
                new StripedImage("assets/explosion_1.png", 43)));
        setInvisibleAfterFrames(70);
    }
}
