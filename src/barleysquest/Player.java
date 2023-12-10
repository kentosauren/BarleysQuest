
package barleysquest;


import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * This class contain all player attributes and commands.
 * The player's motion and actions are all managed through this class
 * @author Kent
 */
public class Player extends Physical {

    private TheGame game;
    private LevelLoader lvl;
    private int health, maxHealth, gemsCollected, maxGems, accumulatedPoints;
    double jumpPower, velocity, bulletSpeed;
    boolean finished, hit, dead, blinking, jumpReleased, canJump;
    private Timer stunTimer, lowHealthTimer, gunCooldown, bulletRegenTimer;
    private Timer invincibleTimer, flashingTimer;
    private boolean invincible = false;
    private Writer scoreText, jewelLeftText, livesLeftText;
    private SpriteGroup TEXT_GROUP;
    private SpriteGroup PROJECTILE;
    private GameFont titleFont;
    private int bullets, maxBullets;
    StatusBar healthBar, bulletBar;

    public Player(TheGame game, LevelLoader lvl, BufferedImage[] images)
    {
        super(images);
        this.game = game;
        this.lvl = lvl;
        // set animation speed 200 milliseconds for each frame
        getAnimationTimer().setDelay(70);
        // set animation frame starting from the first image to the last image



        maxBullets = 5;
        bullets = maxBullets;
        maxHealth = 100;
        health = maxHealth;
        jumpPower = 0.03;
        accumulatedPoints = 0;
        gemsCollected = 0;
        velocity = 0.014;
        bulletSpeed = 0.05;

        finished = false;
        hit = false;

        direction = "right";

        PROJECTILE = new SpriteGroup("Projectile Group");

        bulletRegenTimer = new Timer(2000);
        lowHealthTimer = new Timer(1000);
        invincibleTimer = new Timer(2000);
        stunTimer = new Timer(500);
        gunCooldown = new Timer(500);
        flashingTimer = new Timer(200);

        healthBar = new StatusBar(50, 50, maxHealth, 0, new Color(51, 204, 0));
        bulletBar = new StatusBar(100, 50, maxBullets, 0, new Color(51, 0, 204));
        addTextGroup();

    }

    @Override
    public void update(long elapsedTime)
    {

        scoreText.setText("Score: " + game.getPlayerScore());
        jewelLeftText.setText("Gems: " + game.getGems());
        livesLeftText.setText("x " + game.getPlayerLives());

        if (bulletRegenTimer.action(elapsedTime) && bullets < maxBullets)
        {
            bullets++;
        }

        playerMotion(elapsedTime);
        checkLowHealth(elapsedTime);
        TEXT_GROUP.update(elapsedTime);
        PROJECTILE.update(elapsedTime);

        int barColorRed = (maxHealth - health) * 2;
        double barColorGreen = health * 2;
        healthBar.setColor(new Color(barColorRed, ((int) barColorGreen), 0));
        if (hit)
            stun(elapsedTime);
        if (game.keyPressed(KeyEvent.VK_J))
            gemsCollected = maxGems;
        super.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g)
    {
        healthBar.drawStatusBar(g, health);
        bulletBar.drawStatusBar(g, bullets);
        TEXT_GROUP.render(g);
        PROJECTILE.render(g);
        super.render(g);

    }

    public void addTextGroup()
    {
        //initiating text related to player's status
        titleFont = (new GameFontManager()).getFont(new Font("arial", Font.BOLD, 30));
        TEXT_GROUP = new SpriteGroup("Status Text");
        scoreText = new Writer("Score: ", Color.WHITE, titleFont, 300, 52);

        jewelLeftText = new Writer("gems", Color.WHITE, titleFont, 600, 52);
        livesLeftText = new Writer("Lives Left ", Color.WHITE, titleFont, 190, 52);

        Sprite healthIcon = new Sprite(game.getImage("images/miscSprite/heartIcon.png"));
        healthIcon.setLocation(healthBar.getX(), healthBar.getY() - healthIcon.getHeight() - 5);
        Sprite bulletIcon = new Sprite(game.getImage("images/miscSprite/bulletIcon.png"));
        bulletIcon.setLocation(bulletBar.getX(), bulletBar.getY() - bulletIcon.getHeight() - 5);
        Sprite playerHead = new Sprite(game.getImage("images/playerSprite/player_head.png"));
        playerHead.setLocation(livesLeftText.getX() - 46, 56);

        TEXT_GROUP.add(playerHead);
        TEXT_GROUP.add(healthIcon);
        TEXT_GROUP.add(bulletIcon);
        TEXT_GROUP.add(scoreText);
        TEXT_GROUP.add(jewelLeftText);
        TEXT_GROUP.add(livesLeftText);
    }

    public void flashAfterHit(long elapsedTime)
    {
//        if (hit)
//        {
//            flashingInvincible(elapsedTime);
//            setActive(true);
//            hit = false;
//            invincible = false;
//        }
    }

    public void checkLowHealth(long elapsedTime)
    {
        if (health < 25)
        {
            if (lowHealthTimer.action(elapsedTime))
            {
                game.playSound("sound/lowHealth.wav");
            }
        }
    }

    public void walk(String direction, double elapsedTime)
    {


        if (direction.equals("right"))
        {
            setHorizontalSpeed(velocity * elapsedTime);
            setAnimationFrame(1, 8);
            setAnimate(true);
            setLoopAnim(true);
        }

        if (direction.equals("left"))
        {
            setHorizontalSpeed(-velocity * elapsedTime);
            setAnimationFrame(13, 20);
            setAnimate(true);
            setLoopAnim(true);
        }
    }

    public void jump(String direction, long elapsedTime)
    {



        if (!jumpReleased && canJump && this.getVerticalSpeed() == 0.0)
        {
            game.playSound("sound/jump.wav");
            setVerticalSpeed(-jumpPower * elapsedTime);
        }

    }

    public void stand(String direction)
    {
        if (getVerticalSpeed() == 0.0 && getHorizontalSpeed() == 0.0)
        {

            if (direction.equals("right"))
            {
                setAnimate(false);
                setLoopAnim(false);
                setAnimationFrame(0, 0);
                setFrame(0);
            } else if (direction.equals("left"))
            {
                setAnimate(false);
                setLoopAnim(false);
                setAnimationFrame(12, 12);
                setFrame(12);
            }

        }
    }

    public void stun(long elapsedTime)
    {
        //if(!invincible)
        //{
              //invincible = true;
              //invincibleTimer.
        //}
        //else   
        {
            //if(invincibleTimer)
            if (stunTimer.action(elapsedTime))
            {
                game.playSound("sound/hitByEnemy.wav");
                this.addHealth(-20);
                if (health < 0)
                    health = 0;
                // setSpeed(0.02 * elapsedTime, -0.02 * elapsedTime);
                // game.playSound("sound/getHit.mp3");
                hit = false;
            }
        
        }
    }

    public void flashingInvincible(long elapsedTime)
    {
        if (invincible)
        {
            addHealth(-20);

            while (invincibleTimer.action(elapsedTime))
            {
                setActive(!isActive());
                blinking = !blinking;
                if (blinking)
                {
                    if (flashingTimer.action(elapsedTime))
                    {
                        setActive(!isActive());
                    }

                }


            }
            setActive(true);
            hit = false;
            invincible = false;
        }
    }

    public void playerMotion(long elapsedTime)
    {
        if (game.keyDown(KeyEvent.VK_RIGHT))
        {
            setDirection("right");
            walk(this.direction, elapsedTime);
        }
        else if(game.bsInput.isKeyReleased(KeyEvent.VK_RIGHT))
        {

            setHorizontalSpeed(0.0);
            setFrame(0);

        }
        if (game.keyDown(KeyEvent.VK_LEFT))
        {

            setDirection("left");
            walk(this.direction, elapsedTime);

        }

        else if(game.bsInput.isKeyReleased(KeyEvent.VK_LEFT))
        {

            setHorizontalSpeed(0.0);
            setFrame(12);

        }

        if (game.keyDown(KeyEvent.VK_Z))
        {

            setJumpReleased(false);
            jump(direction, elapsedTime);
            setCanJump(false);
        }
        if (game.keyPressed(KeyEvent.VK_X))
        {
            shoot(elapsedTime);
        }


        //checks direction which the player is to fall
        checkJumpDirection();




        if (game.bsInput.isKeyReleased(KeyEvent.VK_SPACE))
        {
            setJumpReleased(true);
        }

    }

    public void checkJumpDirection()
    {
        //player is rising
        if (getVerticalSpeed() < 0)
        {
            setAnimate(false);
            setLoopAnim(false);

            if (getDirection().equals("right"))
            {
                setAnimationFrame(10, 10);
                setFrame(10);
            } else
            {
                if (getDirection().equals("left"))
                {
                    setAnimationFrame(22, 22);
                    setFrame(22);
                }
            }
        }

        //if player is falling
        if (getVerticalSpeed() > 0)
        {
            setAnimate(false);
            setLoopAnim(false);


            if (getDirection().equals("right"))
            {
                setAnimationFrame(11, 11);
                setFrame(11);
            } else
            {
                if (getDirection().equals("left"))
                {
                    setAnimationFrame(23, 23);
                    setFrame(23);


                }
            }
        }
    }

    public void shoot(long elapsedTime)
    {


        if (bullets <=  0)
        {
            game.playSound("sound/click.wav");
        } else
        {
            this.setFrame(9);
            AnimatedSprite bullet = new AnimatedSprite(game.getImages("images/playerSprite/bulletMotion.png", 2, 1, true, 0, 1));
            lvl.PLAYER_PROJECTILES.add(bullet);
            game.playSound("sound/powerbeam.wav");
            if (direction.equals("right"))
            {

                bullet.setLocation(getX() + getWidth(), getY() + (getHeight() / 2));
                bullet.setHorizontalSpeed(bulletSpeed * elapsedTime);
                bullet.setFrame(0);
            } else if (direction.equals("left"))
            {
                bullet.setLocation(getX(), getY() + (getHeight() / 2));
                bullet.setHorizontalSpeed(-bulletSpeed * elapsedTime);
                bullet.setFrame(1);
            }
            bullets--;

        }
    }

    public boolean isDead()
    {
        return dead;
    }

    public void setDead(boolean dead)
    {
        this.dead = dead;
    }

    public int getGemsCollected()
    {
        return gemsCollected;
    }

    public void addGems()
    {
        game.setGems(game.getGems() + 1);
    }

    public boolean isJumpReleased()
    {
        return jumpReleased;
    }

    public void setJumpReleased(boolean jumpReleased)
    {
        this.jumpReleased = jumpReleased;
    }

    public boolean isCanJump()
    {
        return canJump;
    }

    public void setCanJump(boolean canJump)
    {
        this.canJump = canJump;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void addHealth(int health)
    {
        this.health = this.health + health;
    }

    public int getAccumulatedPoints()
    {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(int points)
    {
        this.accumulatedPoints = points;
    }

    public void addAccumulatedPoints(int accumulatedPoints)
    {
        game.setPlayerScore(game.getPlayerScore() + accumulatedPoints);
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public boolean isHit()
    {
        return hit;
    }

    public void setHit(boolean hit)
    {
        this.hit = hit;
    }

    public boolean isInvincible()
    {
        return invincible;
    }

    public void setInvincible(boolean invincible)
    {
        this.invincible = invincible;
    }

    public TheGame getGame()
    {
        return game;
    }

    public int getMaxGems()
    {
        return maxGems;
    }

    public void setMaxGems(int maxGems)
    {
        this.maxGems = maxGems;
    }
}//end class

