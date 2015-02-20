package com.example.tictactoe;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	// Representing the game state:
	private boolean crossesTurn = true; // Who's turn is it? false=O true=X
	private String board[][] = new String[3][3]; // for now we will represent the board as an array of characters
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			    
		SetupButtonListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void SetupButtonListeners()
	{
		TableLayout table = (TableLayout)findViewById(R.id.tableLayout1);
		//This will iterate through your table layout and get the total amount of cells.
	    for(int x = 0; x < table.getChildCount(); x++)
	    {
	        //Remember that .getChildAt() method returns a View, so you would have to cast a specific control.
	        TableRow row = (TableRow) table.getChildAt(x); 
	        //This will iterate through the table row.
	        for(int y = 0; y < row.getChildCount(); y++)
	        {
	            Button btn = (Button) row.getChildAt(y);
	            //Do what you need to do.
	            btn.setOnClickListener(new MyButtonClick(x, y));
	        }
	    }
	       
	    //Also setup the new game button listers
	    Button btnNewGame = (Button)findViewById(R.id.btnNewGame);
	    btnNewGame.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               //new game
               CreateNewGame();
            }
        });
	}
	
	private class MyButtonClick implements Button.OnClickListener {
	 
	    private int x = 0;
	    private int y = 0;
	 
	    public MyButtonClick(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	 
	    public void onClick(View view) {
	       
	            Button B = (Button) view;
	            String Player = crossesTurn ? "X" : "O";
	            board[x][y] = Player;
	            B.setText(Player);
	            B.setEnabled(false); //can not chnage once clicked on 
	            crossesTurn = !crossesTurn; //change turn
	       
	            //after a button has been clicked check if there is a winner!!
	            if(haveWinner(Player))
	            {
	            	//disable the remaminig buttons as game is over!
	            	WeHaveAWinner(Player);
	            }
	            	
	    }
		
	}
	
	//This is where the magic happens to create a new board
	private void CreateNewGame()
	{
		//Clear the board
		board = new String[3][3];
		
		//reset the turn (crosses always starts)
		crossesTurn = true;
		
		//clear all the X and O on the buttons and enable each one
		TableLayout table = (TableLayout)findViewById(R.id.tableLayout1);
		//This will iterate through your table layout and get the total amount of cells.
	    for(int x = 0; x < table.getChildCount(); x++)
	    {
	        //Remember that .getChildAt() method returns a View, so you would have to cast a specific control.
	        TableRow row = (TableRow) table.getChildAt(x); 
	        //This will iterate through the table row.
	        for(int y = 0; y < row.getChildCount(); y++)
	        {
	            Button btn = (Button) row.getChildAt(y);
	            //Do what you need to do.
	            btn.setText("");
	            btn.setEnabled(true);
	        }
	    }
	}
	
	private boolean haveWinner(String Player)
	{
		int totalInRow = 0;
		int row = 0;
		int column = 0;
		//check each row
		for(row = 0; row < 3; row++)
		{
			totalInRow = 0; //reest
			//check each column
			for(column = 0; column < 3; column++)
			{
				//check if this square of the board is for the current player
				//if not then exit check on this row
				if(board[row][column] != null && !board[row][column].equals(Player)) break;
				
				//square is for this player so increment counter
				totalInRow++;
			}
		
			//check if a winner
			if(totalInRow == 3) return true; 	
		}
		
		//check each column
		for(column = 0; column < 3; column++)
		{
			totalInRow = 0; //reest
			//check each row
			for(row = 0; row < 3; row++)
			{
				//check if this square of the board is for the current player
				//if not then exit check on this row
				if(board[column][row] != null && !board[column][row].equals(Player)) break;
				
				//square is for this player so increment counter
				totalInRow++;
			}
		
			//check if a winner
			if(totalInRow == 3) return true; 	
		}
	    
		
		//check each diagonal (top left to bottom right
		for(row = 0; row < 3; row++)
		{
			totalInRow = 0; //reest
			//check if this square of the board is for the current player
			//if not then exit check on this row
			if(board[row][row] != null &&  !board[row][row].equals(Player)) break;
			
			//square is for this player so increment counter
			totalInRow++;
		
			//check if a winner
			if(totalInRow == 3) return true; 	
		}
		
		
		//check each diagonal (bottom left to top right
		for(row = 2; row >= 0; row--)
		{
			totalInRow = 0; //reest
			//check each row
			for(column = 0; column < 3; column++)
			{
				//check if this square of the board is for the current player
				//if not then exit check on this row
				if(board[row][column] != null &&  !board[row][column].equals(Player)) break;
			}
			
			//square is for this player so increment counter
			totalInRow++;
		
			//check if a winner
			if(totalInRow == 3) return true; 	
		}
				
		return false;
	}
	
	private void WeHaveAWinner(String Player)
	{
		Toast toast = Toast.makeText(getBaseContext(), "The winner is " + Player + "!!!", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,	0);
		toast.show();
	}
}
