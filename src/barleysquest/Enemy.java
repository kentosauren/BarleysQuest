
package barleysquest;


import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The parent class for all enemies generated in the game
 * @author Kent
 */
public class Enemy extends Physical {

    protected int health;
    protected String name;
    protected int points;
    protected double speed;
    protected int damage;
    
    // int vision;//how many pixels the enemy can "see"

    public Enemy(BufferedImage[] images)
    {
        super(images);

//        gameOverFont = (new GameFontManager()).getFont(new Font("Agency FB", Font.BOLD, 50));
//        scoreTimer = new Timer(2000);
//        writeScoreBool = false;
//        renderBool = false;

    }

    public void writeScore(Graphics2D g)
    {
    }

    @Override
    public void update(long elapsedTime)
    {
//        if (writeScoreBool)
//        {
//            System.out.println("sadfasdf");
//            scoreText = new Writer("" + points, Color.white, gameOverFont, 0, 0);
//            scoreText.setLocation(this.getX(), this.getY());
//            scoreText.setVerticalSpeed(0.2);
//            scoreTimer.setActive(true);
//            scoreTimer.setCurrentTick(0);
//            writeScoreBool = false;
//            renderBool = true;

//        }else if(renderBool)
//        {
//         scoreText.update(elapsedTime);
//
//        }
//            else  if (renderBool && scoreTimer.action(elapsedTime))
//        {
//
//            scoreText.setActive(false);
//            renderBool = false;
//
//        }

        super.update(elapsedTime);
    }

    @Override
    public void render(Graphics2D g)
    {

//        if (renderBool)
//            scoreText.render(g);
        super.render(g);
    }

    public int getLife()
    {
        return health;
    }

    public void setLife(int life)
    {
        this.health = life;
    }

    public int gePoints()
    {
        return points;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public int getDamage()
    {
        return damage;
    }

}

/************************************************************************
 * **********************************************************************
 *                  INDIVIDUAL ENEMY CLASSES
 ************************************************************************
 ***********************************************************************/
class WallCannon extends AnimatedSprite {

    TheGame game;
    private Timer cannonTimer;
    int damage;
    double bulletSpeed;
    LevelLoader lvl;

    public WallCannon(TheGame game, LevelLoader lvl, BufferedImage[] images)
    {
        super(images);
        this.game = game;
        this.lvl = lvl;
        bulletSpeed = 0.015;

        setFrame(0);
        damage = 10;
        cannonTimer = new Timer(2500);



    }

    public void spreadShot(long elapsedTime)
    {

        if (cannonTimer.action(elapsedTime))
        {
            Sprite cannonTop = new Sprite(game.getImage("images/miscSprite/bomb.png"));
            Sprite cannonMiddle = new Sprite(game.getImage("images/miscSprite/bomb.png"));
            Sprite cannonBottom = new Sprite(game.getImage("images/miscSprite/bomb.png"));
            lvl.ENEMY_PROJECTILES.add(cannonTop);
            lvl.ENEMY_PROJECTILES.add(cannonMiddle);
            lvl.ENEMY_PROJECTILES.add(cannonBottom);

            if (getFrame() == 0)
            {
                game.playSound("sound/cannonBlast.wav");
                cannonTop.setLocation(this.getX() + 18, this.getY() + 9);
                cannonTop.setSpeed(bulletSpeed * elapsedTime, -bulletSpeed * elapsedTime);
                cannonMiddle.setLocation((this.getX() + this.getWidth()), (this.getY() + (this.getHeight() / 2) - (cannonMiddle.getHeight() / 2)));
                cannonMiddle.setSpeed(bulletSpeed * elapsedTime, 0.0);
                cannonBottom.setLocation(this.getX() + this.getHeight() - 18, this.getY() + this.getHeight() - 9);
                cannonBottom.setSpeed(bulletSpeed * elapsedTime, bulletSpeed * elapsedTime);
            } else if (getFrame() == 1)
            {
            }
        }
    }

    @Override
    public void update(long elapsedTime)
    {

        if (this.isOnScreen())
            spreadShot(elapsedTime);
        super.update(elapsedTime);
    }
}

class HomingMissileMan extends Enemy {

    TheGame game;
    private Timer missileTimer;
    Timer beepTimer;
    LevelLoader lvl;
    double bulletSpeed, maxBulletSpeed, minBulletSpeed;
    Projectile missile;
    boolean hasShot;

    public HomingMissileMan(TheGame game, LevelLoader lvl, BufferedImage[] images)
    {
        super(images);
        this.game = game;
        this.lvl = lvl;
        bulletSpeed = 0.0001;
        maxBulletSpeed = 0.2;
        staticDefense = true;
        health = 1;
        setFrame(1);


        missileTimer = new Timer(2500);
        beepTimer = new Timer(900);

        hasShot = false;



    }

    public void shootMissile(long elapsedTime)
    {


        if (!hasShot && missileTimer.action(elapsedTime))
        {

            game.playSound("sound/missileShot.wav");
            missile = new Projectile(game.getImage("images/enemySprite/homingBomb.png"));
            lvl.ENEMY_PROJECTILES.add(missile);
            missile.setLocation(this.getX(), this.getY() - 5);
            hasShot = true;

        }
    }

    public void homing(long elapsedTime)
    {

        if (hasShot)
        {
            if (beepTimer.action(elapsedTime))
                game.playSound("sound/whileMissileSeeking.wav");
            //calculating X direction
            if (lvl.getHero().getX() < missile.getX())
            {
                missile.setHorizontalSpeed((missile.getHorizontalSpeed() - bulletSpeed * elapsedTime));
            } else if (lvl.getHero().getX() > missile.getX())
            {
                missile.setHorizontalSpeed((missile.getHorizontalSpeed() + bulletSpeed * elapsedTime));
            }

            //calculating Y direction
            if (lvl.getHero().getY() < missile.getY())
            {
                missile.setVerticalSpeed((missile.getVerticalSpeed() - bulletSpeed * elapsedTime));
            } else if (lvl.getHero().getY() > missile.getY())
            {
                missile.setVerticalSpeed((missile.getVerticalSpeed() + bulletSpeed * elapsedTime));
            }
            if (missile.getHorizontalSpeed() > maxBulletSpeed)
                missile.setHorizontalSpeed(maxBulletSpeed);
            if (missile.getVerticalSpeed() > maxBulletSpeed)
                missile.setVerticalSpeed(maxBulletSpeed);

            if (missile.getHorizontalSpeed() < -maxBulletSpeed)
                missile.setHorizontalSpeed(-maxBulletSpeed);
            if (missile.getVerticalSpeed() < -maxBulletSpeed)
                missile.setVerticalSpeed(-maxBulletSpeed);
        }



    }

    @Override
    public void update(long elapsedTime)
    {

        if (this.getX() < lvl.getHero().getX())
        {
            this.setFrame(0);
        } else
        {
            this.setFrame(1);
        }
        if (this.isOnScreen())
        {

            shootMissile(elapsedTime);
            homing(elapsedTime);
            if (hasShot)
                if (!missile.isActive())
                    hasShot = false;
            setHorizontalSpeed(0.0);
            super.update(elapsedTime);
        }
    }
}

class LizzardRobot extends Enemy {

    TheGame game;
    Player player;
    double maxSpeed;

    public LizzardRobot(Player player, BufferedImage[] images)
    {
        super(images);
        this.player = player;

        //this is the player it is ordered to follow
        this.player = player;
        // set animation speed 200 milliseconds for each frame
        //getAnimationTimer().setDelay(70);
        // set animation frame starting from the first image to the last image

        //initializing skills
        setFrame(0);
        speed = 0.0001;
        health = 3;
        name = "Lizzard Bot";
        points = 200;
        damage = 15;
        follow();


    }

    @Override
    public void update(long elapsedTime)
    {
        follow();

        patrol(direction, elapsedTime);
        super.update(elapsedTime);

    }

    public void follow()
    {


        if (getX() - player.getX() < 0)
        {

            direction = "right";
        } else
        {
            direction = "left";
        }

    }

    public void patrol(String direction, long elapsedTime)
    {

        if (isOnScreen())
        {
            if (direction.equals("left"))
            {
                setFrame(0);
                setHorizontalSpeed((getHorizontalSpeed() - speed * elapsedTime));
            } else if (direction.equals("right"))
            {
                setFrame(1);
                setHorizontalSpeed(getHorizontalSpeed() + speed * elapsedTime);
            }
        }
    }
}

class Projectile extends Sprite {

    int damage;
    private boolean boss;

    public Projectile(BufferedImage image)
    {
        super(image);
        damage = 10;
        boss = false;
    }

    public int getDamage()
    {
        return damage;
    }
}

class FinalBoss extends Sprite {

    private Timer shootTimer;
    private LevelLoader lvl;
    private Timer diveTimer = new Timer(3000);
    private Timer sideShootTimer = new Timer(1000);
    private double y;
    private boolean patroling;
    private int life;
    private double speed;
    private boolean freakOut;
    private boolean placed;
    Sprite blocks1, blocks2, blocks3, blocks4;

    public FinalBoss(BufferedImage image, LevelLoader lvl)
    {
        super(image);
        this.lvl = lvl;
        shootTimer = new Timer(2000);
        life = 25;
        patroling = true;
        speed = 0.015;
        freakOut = false;
        placed = false;

    }

    @Override
    public void update(long elapsedTime)
    {

        if (lvl.getHero().getX() > (38 - 19) * 50 && !placed)
            addBarrier();



        //if(isOnScreen())
        //lvl.getGame().playMusic("music/bossTheme.mp3");

        if (shootTimer.action(elapsedTime) && patroling || freakOut && shootTimer.action(elapsedTime))
            shoot(elapsedTime);
        else if (diveTimer.action(elapsedTime) && !freakOut || !patroling || freakOut)
            dive(elapsedTime);

        if (!freakOut && life < 10)
        {
            freakOut = true;
            sideShootTimer.setDelay(2500);
            setSpeed(speed * elapsedTime, speed * elapsedTime);
        }

        if (life <= 1)
            removeBarrier();

        super.update(elapsedTime);
    }

    public void addBarrier()
    {
        Sprite block1 = new Sprite(lvl.getGame().getImage("images/terrain/brick.png"), (38 - 20) * 50, (12 - 3) * 50);
        lvl.WALLS_GROUP.add(block1);
        Sprite block2 = new Sprite(lvl.getGame().getImage("images/terrain/brick.png"), (38 - 20) * 50, (12 - 2) * 50);
        lvl.WALLS_GROUP.add(block2);
        Sprite block3 = new Sprite(lvl.getGame().getImage("images/terrain/brick.png"), (38 - 4) * 50, (12 - 3) * 50);
        lvl.WALLS_GROUP.add(block3);
        Sprite block4 = new Sprite(lvl.getGame().getImage("images/terrain/brick.png"), (38 - 4) * 50, (12 - 2) * 50);
        lvl.WALLS_GROUP.add(block4);
        placed = true;

        blocks1 = block1;
        blocks2 = block2;
        blocks3 = block3;
        blocks4 = block4;

        lvl.getGame().playSound("sound/squeehee.wav");
        lvl.getGame().playMusic("music/bossTheme.mp3");
    }

    public void removeBarrier()
    {
        blocks1.setActive(false);
        blocks2.setActive(false);
        blocks3.setActive(false);
        blocks4.setActive(false);
        this.setActive(false);
    }

    public void dive(long elapsedTime)
    {
        if (patroling)
        {
            setSpeed(0.0, 0.014 * elapsedTime);
            patroling = false;
            sideShootTimer.setCurrentTick(0);
        }

        sideShot(elapsedTime);

        if (this.getY() < y && !freakOut)
        {
            setY(y);
            setSpeed(0.014 * elapsedTime, 0.0);
            diveTimer.setCurrentTick(0);
            patroling = true;

        }

    }

    private void sideShot(long elapsedTime)
    {
        if (sideShootTimer.action(elapsedTime))
        {
            HomingProjectile bolt1 = new HomingProjectile(lvl.getGame().getImage("images/miscSprite/boltGreen.png"), this);
            HomingProjectile bolt2 = new HomingProjectile(lvl.getGame().getImage("images/miscSprite/boltGreen.png"), this);
            bolt1.setLocation(this.getX() + 50, this.getY() + 60);
            bolt2.setLocation(this.getX() + 136, this.getY() + 60);
            lvl.ENEMY_PROJECTILES.add(bolt1);
            lvl.ENEMY_PROJECTILES.add(bolt2);

        }
    }

    private void shoot(long elapsedTime)
    {
        BossProjectile bolt = new BossProjectile(lvl.getGame().getImage("images/miscSprite/bolt.png"), this);
        lvl.BOSS_PROJECTILE.add(bolt);
        bolt.setLocation(this.getX() + (this.getWidth() / 2) - (bolt.getWidth() / 2), this.getY() + (this.getHeight() - 20));
        bolt.setVerticalSpeed(0.020 * elapsedTime);

    }

    public void setYcoordinate(double y)
    {
        this.y = y;
    }

    public LevelLoader getLvl()
    {
        return lvl;
    }

    public void setLvl(LevelLoader lvl)
    {
        this.lvl = lvl;
    }

    public int getLife()
    {
        return life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }
}//end Final Boss

class HomingProjectile extends Sprite {

    private final FinalBoss boss;

    public HomingProjectile(BufferedImage image, FinalBoss boss)
    {
        super(image);
        this.boss = boss;

    }

    @Override
    public void update(long elapsedTime)
    {
        homing(elapsedTime);
        super.update(elapsedTime);

    }

    public void homing(long elapsedTime)
    {

        double bulletSpeed = 0.00015;

        //calculating X direction
        if (boss.getLvl().getHero().getX() < getX())
        {
            setHorizontalSpeed((getHorizontalSpeed() - bulletSpeed * elapsedTime));
        } else if (boss.getLvl().getHero().getX() > getX())
        {
            setHorizontalSpeed((getHorizontalSpeed() + bulletSpeed * elapsedTime));
        }

        //calculating Y direction
        if (boss.getLvl().getHero().getY() < getY())
        {
            setVerticalSpeed((getVerticalSpeed() - bulletSpeed * elapsedTime));
        } else if (boss.getLvl().getHero().getY() > getY())
        {
            setVerticalSpeed((getVerticalSpeed() + bulletSpeed * elapsedTime));
        }
//            if (getHorizontalSpeed() > maxBulletSpeed)
//                setHorizontalSpeed(maxBulletSpeed);
//            if (getVerticalSpeed() > maxBulletSpeed)
//                setVerticalSpeed(maxBulletSpeed);
//
//            if (missile.getHorizontalSpeed() < -maxBulletSpeed)
//                missile.setHorizontalSpeed(-maxBulletSpeed);
//            if (missile.getVerticalSpeed() < -maxBulletSpeed)
//                missile.setVerticalSpeed(-maxBulletSpeed);
//        }




    }
}

class BossProjectile extends Sprite {

    private boolean spread;
    private final FinalBoss boss;

    public BossProjectile(BufferedImage image, FinalBoss boss)
    {
        super(image);
        this.boss = boss;
        spread = false;

    }

    @Override
    public void update(long elapsedtime)
    {
        if (spread)
        {
            spreadBlast(elapsedtime);
            spread = false;
        }
        super.update(elapsedtime);

    }

    public void setSpread(boolean spread)
    {
        this.spread = spread;
    }

    public void spreadBlast(long elapsedTime)
    {


        Projectile spreadShot[] = new Projectile[5];

        for (int i = 0; i < spreadShot.length; i++)
        {
            if (i == 0)
            {
                spreadShot[i] = new Projectile(boss.getLvl().getGame().getImage("images/miscSprite/flame.png"));
            } else if (i == spreadShot.length - 1)
            {
                spreadShot[i] = new Projectile(boss.getLvl().getGame().getImage("images/miscSprite/flame_m.png"));
            } else
            {
                spreadShot[i] = new Projectile(boss.getLvl().getGame().getImage("images/miscSprite/bomb2.png"));
            }

            boss.getLvl().BOSS_PROJECTILE.add(spreadShot[i]);
            spreadShot[i].setLocation(this.getX() + (this.getWidth() / 2), this.getY() - 10);
        }
        double spreadShotSpeed = 0.020;
        spreadShot[0].setSpeed(-spreadShotSpeed * elapsedTime, 0.0);
        spreadShot[1].setSpeed(-spreadShotSpeed * elapsedTime, -spreadShotSpeed * elapsedTime);
        spreadShot[2].setSpeed(0.0, -spreadShotSpeed * elapsedTime);
        spreadShot[3].setSpeed(spreadShotSpeed * elapsedTime, -spreadShotSpeed * elapsedTime);
        spreadShot[4].setSpeed(spreadShotSpeed * elapsedTime, 0.0);


        this.setActive(false);

    }
}
