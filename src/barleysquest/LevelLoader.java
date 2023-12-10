
package barleysquest;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.ImageBackground;
import java.awt.image.BufferedImage;
import com.golden.gamedev.object.PlayField;

/**
 * This class loads the current level indicated by the StageCounter(TheGame)
 * All visible objects on the level area is loaded here
 * Extends Playfield
 * @author Kent
 */
public class LevelLoader extends PlayField {

    private TheGame game;
    //LevelDesign acts as a template for the levels
    private LevelDesign levelDesign;
    private Player hero;//the player object
    private ImageBackground bg;
    private BufferedImage backgr;
    //declaring sprite groups for easy management of sprite objects
    public SpriteGroup WALLS_GROUP, PLAYER_GROUP, ENEMY_GROUP,
            PIT_GROUP, GOALS_GROUP, ITEM_GROUP, ELEVATOR_GROUP,
            STATIC_ENEMY, ENEMY_PROJECTILES, PLAYER_PROJECTILES,
            BOSS_PROJECTILE, BOSS_GROUP;
//    private Writer stageIntroText;
//    private Timer introTextTime;
    //   double bgSpeed;
    private boolean scrollCheck;
  

    public LevelLoader(TheGame game)
    {


        super();

        this.game = game;
        levelDesign = new LevelDesign();
        scrollCheck = false;

        //creating player object
        hero = new Player(game, this, game.getImages("images/playerSprite/playermotion.png", 12, 2, true, 0, 23));




        //Initiating spritegroups
        WALLS_GROUP = new SpriteGroup("Walls Group");
        PLAYER_GROUP = new SpriteGroup("Player Group");
        ENEMY_GROUP = new SpriteGroup("Enemy Group");
        PIT_GROUP = new SpriteGroup("Pit Group");
        GOALS_GROUP = new SpriteGroup("Goal Group");
        ITEM_GROUP = new SpriteGroup("Item Group");
        ELEVATOR_GROUP = new SpriteGroup("Elevator Group");
        STATIC_ENEMY = new SpriteGroup("Static Enemy");
        ENEMY_PROJECTILES = new SpriteGroup("Enemy Projectiles");
        PLAYER_PROJECTILES = new SpriteGroup("Player Projectiles");
        BOSS_GROUP = new SpriteGroup("Boss Group");
        BOSS_PROJECTILE = new SpriteGroup("Boss Projectile");


        //adding all the spritegroups to the playfield so that I can Render it as one big chunk
        addGroup(WALLS_GROUP);
        addGroup(PLAYER_GROUP);
        addGroup(ENEMY_GROUP);
        addGroup(PIT_GROUP);
        addGroup(GOALS_GROUP);
        addGroup(ITEM_GROUP);
        addGroup(GOALS_GROUP);
        addGroup(ELEVATOR_GROUP);
        addGroup(STATIC_ENEMY);
        addGroup(ENEMY_PROJECTILES);
        addGroup(PLAYER_PROJECTILES);
        addGroup(BOSS_GROUP);
        addGroup(BOSS_PROJECTILE);

        //creates the collisions and adding the spritegroup to respective collision
        addCollisionGroup(PLAYER_GROUP, ENEMY_GROUP, new PlayerEnemyCollision());
        addCollisionGroup(PLAYER_GROUP, WALLS_GROUP, new AnyTerrainCollision());
        addCollisionGroup(PLAYER_GROUP, PIT_GROUP, new PlayerPitCollision());
        addCollisionGroup(PLAYER_GROUP, GOALS_GROUP, new PlayerGoalCollision());
        addCollisionGroup(PLAYER_GROUP, ITEM_GROUP, new PlayerItemCollision());
        addCollisionGroup(PLAYER_GROUP, ELEVATOR_GROUP, new PlayerElevatorCollision());
        addCollisionGroup(ENEMY_GROUP, WALLS_GROUP, new EnemyWallsCollision());
        addCollisionGroup(ENEMY_GROUP, ENEMY_GROUP, new EnemyEnemyCollision());
        addCollisionGroup(ELEVATOR_GROUP, WALLS_GROUP, new ElevatorWallCollision());
        addCollisionGroup(ENEMY_PROJECTILES, WALLS_GROUP, new ProjectileWallCollision());
        addCollisionGroup(PLAYER_PROJECTILES, WALLS_GROUP, new ProjectileWallCollision());
        addCollisionGroup(ENEMY_PROJECTILES, PLAYER_GROUP, new PlayerProjectileCollision());
        addCollisionGroup(PLAYER_PROJECTILES, ENEMY_GROUP, new playerProjectileEnemyCollision());
        addCollisionGroup(BOSS_GROUP, WALLS_GROUP, new BossWallCollision());
        addCollisionGroup(PLAYER_PROJECTILES, BOSS_GROUP, new BossProjectileWallCollision());
        addCollisionGroup(BOSS_PROJECTILE, WALLS_GROUP, new ProjectileWallCollision());
        addCollisionGroup(BOSS_PROJECTILE, PLAYER_GROUP, new PlayerProjectileCollision());

        addCollisionGroup(PLAYER_GROUP, BOSS_GROUP, new PlayerEnemyCollision());

        //loading all the necessary sprites and components for the level
        loadLevel(levelDesign.getMapStorage(game.getStageCounter()), levelDesign.getColumns(game.getStageCounter()), levelDesign.getLevelNames(game.getStageCounter()), levelDesign.getSongURL(game.getStageCounter()));


    }

    @Override
    public void update(long elapsedTime)
    {

//        if(game.getStageCounter() == 3)
//            checkTriggerPoint();

        //checks if your on lvl 2
        if (!scrollCheck && game.getStageCounter() == 1)
        {
            scrollCheck = true;
            bg.setLocation(0, 4000);

        }
        if (game.getStageCounter() == 1 && scrollCheck)
        {
            double bgSpeed = -0.035;
            bg.move(0, bgSpeed * elapsedTime);
        } else
        {
            bg.setLocation((hero.getX() - (game.getWidth() / 2)), (hero.getY() - (game.getHeight() / 2)));
        }

        //checks if player either is dead or has finished the goals
        checkIfFinished();
        checkIfDead();

        super.update(elapsedTime);

    }

    public void checkIfFinished()
    {

        //her sjekkes det om du har kommet i goal
        if (hero.isFinished())
        {
//            game.bsMusic.stop(levelDesign.getSongURL(game.getStageCounter()));
            game.playSound("sound/success.wav");

            game.setStageCounter(game.getStageCounter() + 1);

            //checks if the array containing the levels is out of bounds
            if (game.getStageCounter() == levelDesign.getMapStorageLength())
            {
                //resets game and goes back to main menu
                game.setStageCounter(0);
                game.setCurrentLevel(new CongratulationsScreen(game));
            } else
            {
                //loads next stage
                game.setCurrentLevel(new PreStageScreen(game));
            }
        }
        hero.setFinished(false);
    }

    public void checkIfDead()
    {
        if (hero.getHealth() <= 0 || hero.getScreenY() > game.getHeight() + 60)
        {
            game.bsMusic.stop(levelDesign.getSongURL(game.getStageCounter()));
            game.playSound("sound/dieSound.wav");
            game.setPlayerLives(game.getPlayerLives() - 1);



            if (game.getPlayerLives() < 0)
            {

                game.setCurrentLevel(new GameOverScreen(game));
                game.setPlayerLives(5);



            } else
            {
                //game.setCurrentLevel(new LevelLoader(game));
                game.setCurrentLevel(new DeathScreen(game));
            }
        }
    }

    //this method is taking the arrays from the levelDesign class and inserting the sprites specified from the array
    public void loadLevel(int[] levelDesignArray, int cols, String stageName, String SongURL)
    {


        //initializing background
        backgr = game.getImage(levelDesign.getBgURL(game.getStageCounter()));
        bg = new ImageBackground(backgr);
        setBackground(bg);
        

        int yCounter = 0;
        int xCounter = 0;//this counts to the level-collumn-indicator and then resets to zero
        for (int i = 0; i < levelDesignArray.length; i++)
        {
            if (xCounter == cols)
            {
                xCounter = 0;
                yCounter++;
            }

            if (levelDesignArray[i] == 1)
            {   //tile set will change dependent on the stage counter
                if (game.getStageCounter() == 0)
                {
                    Sprite terrain = new Sprite(game.getImage("images/terrain/grass.png"), (xCounter * 50), yCounter * 50);
                    WALLS_GROUP.add(terrain);
                } else if (game.getStageCounter() == 1)
                {
                    Sprite terrain = new Sprite(game.getImage("images/terrain/cloud.png"), (xCounter * 50), yCounter * 50);
                    WALLS_GROUP.add(terrain);
                } else if (game.getStageCounter() == 2)
                {
                    Sprite terrain = new Sprite(game.getImage("images/terrain/lavaRock.png"), (xCounter * 50), yCounter * 50);
                    WALLS_GROUP.add(terrain);
                } else if (game.getStageCounter() == 3)
                {
                    Sprite terrain = new Sprite(game.getImage("images/terrain/brick.png"), (xCounter * 50), yCounter * 50);
                    WALLS_GROUP.add(terrain);
                }


            } else if (levelDesignArray[i] == 2)
            {
                Pit lava = new Pit(game.getImage("images/terrain/lava.png"));
                lava.setLocation((xCounter * 50), (yCounter * 50));
                lava.setPlayer(hero);
                PIT_GROUP.add(lava);
            } else if (levelDesignArray[i] == 3)
            {
                if (game.getStageCounter() == 3)
                {
                    FinalBoss boss = new FinalBoss(game.getImage("images/enemySprite/finalBoss.png"), this);
                    boss.setLocation(xCounter * 50, yCounter * 50);
                    boss.setHorizontalSpeed(0.1);
                    boss.setYcoordinate(boss.getY());
                    BOSS_GROUP.add(boss);

                } else
                {
                    LizzardRobot lizzardBot = new LizzardRobot(hero, game.getImages("images/enemySprite/lizzardbotmotion.png", 1, 2, true, 0, 1));
                    lizzardBot.setLocation(xCounter * 50, yCounter * 50);
                    ENEMY_GROUP.add(lizzardBot);
                }
            } else if (levelDesignArray[i] == 4)
            {
                WallCannon wallCannon = new WallCannon(game, this, game.getImages("images/enemySprite/wallCannonMotion.png", 2, 1, true, 0, 1));
                wallCannon.setLocation(xCounter * 50, yCounter * 50);
                STATIC_ENEMY.add(wallCannon);
            } else if (levelDesignArray[i] == 5)
            {
                FirstAid firstAid = new FirstAid(game.getImages("images/itemSprite/firstAid.png", 1, 1, true));
                firstAid.setLocation(xCounter * 50, yCounter * 50);
                ITEM_GROUP.add(firstAid);
            } else if (levelDesignArray[i] == 6)
            {
                Elevator elevator = new Elevator(game.getImage("images/miscSprite/elevator.png"));
                elevator.setLocation(xCounter * 50, yCounter * 50);
                ELEVATOR_GROUP.add(elevator);
                elevator.setPlayer(hero);

            } else if (levelDesignArray[i] == 7)
            {
                hero.setLocation(xCounter * 50, yCounter * 50);
                PLAYER_GROUP.add(hero);
            } else if (levelDesignArray[i] == 8)
            {
                if (game.getStageCounter() == 0)
                {
                    Gem gem = new Gem(game.getImages("images/itemSprite/gemOrange.png", 1, 1, true, 0, 0));
                    ITEM_GROUP.add(gem);
                    gem.setLocation(xCounter * 50, yCounter * 50);
                } else if (game.getStageCounter() == 1)
                {
                    Gem gem = new Gem(game.getImages("images/itemSprite/gemYellow.png", 1, 1, true, 0, 0));
                    ITEM_GROUP.add(gem);
                    gem.setLocation(xCounter * 50, yCounter * 50);
                } else if (game.getStageCounter() == 2)
                {
                    Gem gem = new Gem(game.getImages("images/itemSprite/gemGreen.png", 1, 1, true, 0, 0));
                    ITEM_GROUP.add(gem);
                    gem.setLocation(xCounter * 50, yCounter * 50);
                } else if (game.getStageCounter() == 3)
                {
                    Gem gem = new Gem(game.getImages("images/itemSprite/gemBlue.png", 1, 1, true, 0, 0));
                    ITEM_GROUP.add(gem);
                    gem.setLocation(xCounter * 50, yCounter * 50);
                }

                hero.setMaxGems(hero.getMaxGems() + 1);

            } else if (levelDesignArray[i] == 9)
            {

                Sprite goal = new Sprite(game.getImage("images/miscSprite/goal.png"), (xCounter * 50), yCounter * 50);
                goal.setLocation(xCounter * 50, yCounter * 50);
                GOALS_GROUP.add(goal);


            } else if (levelDesignArray[i] == 'h')
            {
                HomingMissileMan homing = new HomingMissileMan(game, this, game.getImages("images/enemySprite/homingMissileManMotion.png", 2, 1, true, 0, 1));
                homing.setLocation(xCounter * 50, yCounter * 50);
                ENEMY_GROUP.add(homing);
            }
            xCounter++;
        }

        if (game.isMusicOnOff())
            game.playMusic(levelDesign.getSongURL(game.getStageCounter()));
    }

    public Player getHero()
    {
        return hero;
    }

    public TheGame getGame()
    {
        return game;
    }
}
