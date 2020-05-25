package com.pratyushshivam.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Random;

import sun.rmi.runtime.Log;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backgroud;
	Texture[] man;
	int manState = 0;
	int pause = 0;
	float gravity = 0.8f;
	int gameState=0;
	float velocity = 0f;
	int manY = 0;
	ArrayList<Integer> coinXs = new ArrayList<Integer>();
	ArrayList<Integer> coinYs = new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangle = new ArrayList<Rectangle>();
	Texture coin;
	int coinCount;
	Rectangle manRectangle;
	Texture dizzy;

	BitmapFont font,font2,font3;
	Random random;
	Texture bomb;
	ArrayList<Integer> bombXs = new ArrayList<Integer>();
	ArrayList<Integer> bombYs = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangle = new ArrayList<Rectangle>();
	int bombCount;
	int score=0;

	@Override
	public void create() // this is basically oncreate funmction for the game, before starting do all the assigning things here
	{
		batch = new SpriteBatch(); // sttaring point of app
		backgroud = new Texture("bg.png");
		man = new Texture[4];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame2.png");
		man[2] = new Texture("frame3.png");
		man[3] = new Texture("frame4.png"); // doing the assining process
		manY = Gdx.graphics.getHeight() / 2;
		random = new Random();

		coin = new Texture("coin.png");
		bomb=new Texture("bomb.png");
		//manRectangle=new Rectangle();


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		dizzy=new Texture("dizzy1.png");
		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(10);

		font3 = new BitmapFont();
		font3.setColor(Color.RED);
		font3.getData().setScale(10);



	}


	public void makeCoin() {
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int) height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	public void makeBomb() {
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int) height);
		bombXs.add(Gdx.graphics.getWidth());
	}



	@Override
	public void render() // ye function hmesha chalta rehta hai
	{
		batch.begin();
		batch.draw(backgroud, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		if(gameState==1)
		{
			//Coin is live
			//THIS IS FOR BOMBS
			if (bombCount < 250) {
				bombCount++;
			} else {
				bombCount = 0;
				makeBomb();
			}
			bombRectangle.clear();
			for (int i = 0; i < bombXs.size(); i++)
			{
				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
				bombXs.set(i, bombXs.get(i) - 12);
				bombRectangle.add(new Rectangle(bombXs.get(i),bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
			}








			// THIS IS FOR COINS
			if (coinCount < 100) {
				coinCount++;
			} else {
				coinCount = 0;
				makeCoin();
			}
			coinRectangle.clear();
			for (int i = 0; i < coinXs.size(); i++)
			{
				batch.draw(coin, coinXs.get(i), coinYs.get(i));
				coinXs.set(i, coinXs.get(i) - 6);
				coinRectangle.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
			}
			if(Gdx.input.justTouched())
			{
				velocity=-18;
			}
			if(pause<5)
			{
				pause++; // loops 8 times here
			}
			else {
				pause = 0;
				if (manState < 3)
				{
					manState++;
				} else
				{
					manState = 0;
				}
			}

			velocity=velocity+gravity;
			manY-=velocity;
			if(manY<=0)
			{
				manY=0;
			}


		}

		else if(gameState==0)
		{

				font2.draw(batch, "Coins vs Bombs", Gdx.graphics.getWidth() / 2 - 523, Gdx.graphics.getHeight() / 2 + 780);
			font3.draw(batch,"Tap to Play",Gdx.graphics.getWidth()/2-378,Gdx.graphics.getHeight()/2-200);
			//Waiting to start
			if(Gdx.input.justTouched())
			{

				gameState=1;
			}

		}
		else if(gameState==2)
		{
			font3.draw(batch,"Tap to Restart",Gdx.graphics.getWidth()/2-450,Gdx.graphics.getHeight()/2-200);

			// GAme over situatin
			if(Gdx.input.justTouched())
			{
				if(Gdx.input.justTouched()) {
					int a=0;
					for(a=0;a<10000;a++)
					{
						a++;
					}
					gameState = 1;
					manY = Gdx.graphics.getHeight() / 2;
					score = 0;
					velocity = 0;
					coinXs.clear();
					coinYs.clear();
					coinRectangle.clear();
					coinCount = 0;
					bombXs.clear();
					bombYs.clear();
					bombRectangle.clear();
					bombCount = 0;
				}

			}


		}

		if(gameState==2) {
			batch.draw(dizzy,Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
			font.draw(batch,"Coins vs Bombs",Gdx.graphics.getWidth()/2-523,Gdx.graphics.getHeight()/2+780);
		}
		else
		{
			batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
		}
    	manRectangle = new Rectangle(Gdx.graphics.getWidth()/2 - man[manState].getWidth()/2,manY,man[manState].getWidth(),man[manState].getHeight());
    	for(int i=0;i<coinRectangle.size(); i++)
		{
			if(Intersector.overlaps(manRectangle,coinRectangle.get(i)))
			{
				Gdx.app.log("Coin!","Collision");
				score++;
				coinRectangle.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;

			}
		}
		for(int i=0;i<bombRectangle.size(); i++)
		{
			if(Intersector.overlaps(manRectangle,bombRectangle.get(i)))
			{
				Gdx.app.log("Bomb!","BombCollision");
				gameState=2;
			}
		}



		font.draw(batch,String.valueOf(score),100,200);




    	batch.end();

	}
	
	@Override
	public void dispose ()
    {
		batch.dispose();

	}
}
