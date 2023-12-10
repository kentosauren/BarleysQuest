package barleysquest;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;
import com.golden.gamedev.object.collision.CollisionGroup;
import com.golden.gamedev.object.collision.PreciseCollisionGroup;

/*
 *This is a file for gathering all of the collision classes
 *class names reveals their function self explanatory
 *@author Kent
 */
class AnyTerrainCollision extends CollisionGroup {

    public AnyTerrainCollision()
    {

        super();
    }

    @Override
    public void collided(Sprite sprite, Sprite wall)
    {
        pixelPerfectCollision = false;
        if (getCollisionSide() == TOP_BOTTOM_COLLISION)
        {
            pixelPerfectCollision = true;

            sprite.setY(wall.getY() + wall.getHeight());
            sprite.setVerticalSpeed(0.0);

        }

        if (getCollisionSide() == BOTTOM_TOP_COLLISION)
        {
            pixelPerfectCollision = false;

            sprite.setY(wall.getY() - sprite.getHeight());

            sprite.setVerticalSpeed(0.0);
            if (sprite instanceof Player)
            {
                ((Player) sprite).stand(((Player) sprite).getDirection());
            }


            if (sprite instanceof Player)
            {
                ((Player) sprite).setCanJump(true);
            }
        }

        if (getCollisionSide() == LEFT_RIGHT_COLLISION)
        {
            pixelPerfectCollision = false;
            sprite.setX(wall.getX() + sprite.getWidth());

        }
        if (getCollisionSide() == RIGHT_LEFT_COLLISION)
        {
            pixelPerfectCollision = false;
            sprite.setX(wall.getX() - sprite.getWidth());

        }

    }
}

class PlayerEnemyCollision extends CollisionGroup {

    public PlayerEnemyCollision()
    {
        pixelPerfectCollision = true;
    }

    @Override
    public void collided(Sprite player, Sprite enemy)
    {

        if (getCollisionSide() == RIGHT_LEFT_COLLISION || getCollisionSide() == LEFT_RIGHT_COLLISION)
        {
            ((Player) player).setHit(true);
            player.setX(collisionX1);


        }

        if (getCollisionSide() == TOP_BOTTOM_COLLISION)
        {

            player.setVerticalSpeed(0.0);
            ((Player) player).setHit(true);

        }
        if (getCollisionSide() == BOTTOM_TOP_COLLISION)
        {
            if (player instanceof Player)
                ((Player) player).getGame().playSound("sound/landSound.wav");


            if (((Enemy) enemy).getLife() <= 0)
            {
                enemy.setActive(false);
                ((Player) player).addAccumulatedPoints(((Enemy) enemy).getPoints());

            }

            ((Enemy) enemy).setLife(((Enemy) enemy).getLife() - 2);
            player.setVerticalSpeed(0.0);
            player.setVerticalSpeed(-0.5);

        }
        enemy.setX(collisionX2);
        enemy.setHorizontalSpeed(-enemy.getHorizontalSpeed());


    }
}

class EnemyWallsCollision extends PreciseCollisionGroup {

    public EnemyWallsCollision()
    {
        pixelPerfectCollision = false;
    }

    @Override
    public void collided(Sprite enemy, Sprite wall)
    {


        if (getCollisionSide() == LEFT_RIGHT_COLLISION)
        {
            enemy.setX(wall.getX() + wall.getWidth());
            enemy.setHorizontalSpeed(0.0);
            ((Enemy) enemy).setDirection("right");





        } else if (getCollisionSide() == RIGHT_LEFT_COLLISION)
        {
            enemy.setX(wall.getX() - enemy.getWidth());
            enemy.setHorizontalSpeed(0.0);

            ((Enemy) enemy).setDirection("left");

        }

        if (getCollisionSide() == BOTTOM_TOP_COLLISION)
        {
            enemy.setVerticalSpeed(0.0);
            enemy.setY(wall.getY() - enemy.getHeight());

        }
    }
}

class PlayerPitCollision extends CollisionGroup {

    public PlayerPitCollision()
    {
        pixelPerfectCollision = true;
    }

    @Override
    public void collided(Sprite player, Sprite pit)
    {
        ((Pit) pit).setCollidedPlayer(true);
    }
}

class EnemyEnemyCollision extends CollisionGroup {

    public EnemyEnemyCollision()
    {
        pixelPerfectCollision = false;
    }

    @Override
    public void collided(Sprite sprite, Sprite sprite1)
    {

        sprite.setHorizontalSpeed(0.0);
        sprite1.setHorizontalSpeed(0.0);
        if (getCollisionSide() == RIGHT_LEFT_COLLISION)
            sprite.setX(collisionX1 - 1);
        if (getCollisionSide() == LEFT_RIGHT_COLLISION)
            sprite.setX(collisionX1 + 1);
    }
}

class PlayerItemCollision extends BasicCollisionGroup {

    @Override
    public void collided(Sprite player, Sprite item)
    {
        item.setActive(false);
        if (item instanceof Gem)
        {

            ((Player) player).addAccumulatedPoints(((Item) item).getPoints());
            ((Player) player).getGame().playSound("sound/gemPickup.wav");
            ((Player) player).addGems();
        }

        if (item instanceof FirstAid)
        {
            item.setActive(false);
            ((Player) player).getGame().playSound("sound/refillHealth.wav");
            ((Player) player).addHealth(25);
            if (((Player) player).getHealth() >= ((Player) player).getMaxHealth())
            {
                ((Player) player).setHealth(((Player) player).getMaxHealth());
            }
        }


    }
}

class PlayerGoalCollision extends BasicCollisionGroup {

    @Override
    public void collided(Sprite player, Sprite goal)
    {

        ((Player) player).setFinished(true);
        ((Player) player).getGame().playMusic("");
        
    }
}

class ElevatorWallCollision extends CollisionGroup {

    @Override
    public void collided(Sprite sprite, Sprite sprite1)
    {
        sprite.setX(collisionX1);
        ((Elevator) sprite).setVelocity(-((Elevator) sprite).getVelocity());
    }
}

class ProjectileWallCollision extends CollisionGroup {

    public ProjectileWallCollision()
    {
        pixelPerfectCollision = true;
    }

    @Override
    public void collided(Sprite bullet, Sprite wall)
    {

        if (bullet instanceof BossProjectile)
        {
            ((BossProjectile) bullet).setSpread(true);
        } else
        {
            bullet.setActive(false);
        }


    }
}

//class outOfScreen
class PlayerElevatorCollision extends CollisionGroup {

    @Override
    public void collided(Sprite player, Sprite elevator)
    {

        //this if() is making sure you can jump throug the elevator and only collide on the way down
        if (collisionSide == BOTTOM_TOP_COLLISION && player.getVerticalSpeed() >= 0.0)// && && elevator.getY() - player.getY() > (player.getHeight()
        {                                                                               //for aa ikke scope opp hero naar han hopper mot heis
            ((Elevator) elevator).setCollidedPlayer(true);
        }

    }
}

class PlayerProjectileCollision extends CollisionGroup {

    public PlayerProjectileCollision()
    {
        pixelPerfectCollision = true;
    }

    @Override
    public void collided(Sprite projectile, Sprite player)
    {
        ((Player) player).getGame().playSound("sound/getHit.wav");
        projectile.setActive(false);

        ((Player) player).addHealth(-15);
        if (((Player) player).getHealth() < 0)
            ((Player) player).setHealth(0);
    }
}

class playerProjectileEnemyCollision extends CollisionGroup {

    @Override
    public void collided(Sprite sprite, Sprite sprite1)
    {
//        if (sprite instanceof Enemy)
//        {


            sprite1.setVerticalSpeed(sprite1.getVerticalSpeed() - 0.3);
            sprite.setActive(false);
            if (sprite1.getHorizontalSpeed() < 0)
            {

                sprite1.setHorizontalSpeed(sprite1.getHorizontalSpeed() + 0.1);
            } else
            {
                sprite1.setHorizontalSpeed(sprite1.getHorizontalSpeed() - 0.3);
            }
            ((Enemy) sprite1).setLife(((Enemy) sprite1).getLife() - 1);

            if (((Enemy) sprite1).getLife() < 0)
            {
                sprite1.setActive(false);
            }


//        } else
//        {
//
//
//            if (((FinalBoss) sprite1).getLife() < 0)
//            {
//                sprite1.setActive(false);
//            }
//
//
//        }
    }
}

class BossWallCollision extends CollisionGroup {

    public BossWallCollision()
    {
        pixelPerfectCollision = false;
    }

    @Override
    public void collided(Sprite boss, Sprite sprite1)
    {
        if (collisionSide == LEFT_RIGHT_COLLISION || collisionSide == RIGHT_LEFT_COLLISION)
        {
            boss.setX(collisionX1);
            boss.setHorizontalSpeed(-boss.getHorizontalSpeed());
        }
        if (collisionSide == BOTTOM_TOP_COLLISION || collisionSide == TOP_BOTTOM_COLLISION)
        {
            boss.setY(collisionY1);
            boss.setVerticalSpeed(-boss.getVerticalSpeed());
        }
    }
}
class BossProjectileWallCollision extends CollisionGroup {

    FinalBoss bossClass;

    public BossProjectileWallCollision()
    {

        pixelPerfectCollision = false;

    }

    @Override
    public void collided(Sprite projectile, Sprite sprite1)
    {

                        ((FinalBoss) sprite1).setLife(((FinalBoss) sprite1).getLife() - 1);
            projectile.setActive(false);

    }
}

