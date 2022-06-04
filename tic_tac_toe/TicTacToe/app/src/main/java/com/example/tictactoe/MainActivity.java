package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tictactoe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    private final int grid_size = 3;    // can use R.string.gridSize
    TableLayout gameBoard;              // will be using view ding to bind
    char [][] my_board;                 // tictactoe board data structure
    char turn;                          // to change to X of O

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing binding variable
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        // associating with java file with xml file
        View view = activityMainBinding.getRoot();
        setContentView(view);


        gameBoard = activityMainBinding.gameBoard;
        // creating something like this
        // my board = [ a - [char , char , char],
        //              b - [char , char , char],
        //              c - [char , char , char]]
        my_board = new char[grid_size][grid_size];

        resetBoard();
        // using view binding (activityMainBinding) to access turn (TextView)
        activityMainBinding.turn.setText("Turn: "+ turn);

        // getChildCount :
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            // loops through the rows
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            // loops through the columns of row i
            for (int j = 0; j < row.getChildCount(); j++) {
                TextView tv = (TextView) row.getChildAt(j);
                // sets everything on the gameBoard to null
                // this is the start of the game.
                tv.setText(R.string.none);;
                // pass the position through i,j and the TextView tv itself to be modified
                tv.setOnClickListener(Move(i, j, tv));
            }
        }

        activityMainBinding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a intent and store the current intent in it
                Intent current = getIntent();
                finish();               // finish the current intent
                startActivity(current); // start the current intent again
                                        // UI is clear and my_board is reset above
            }
        });
    }

    // clears the my_board data  structure
    // not the UI
    protected void resetBoard() {
        turn ='X';  // X starts fist
        for (int i = 0; i < grid_size; i++) {       // through row
            for (int j = 0; j < grid_size; j++) {   // through column
                my_board[i][j] = ' ';               // set to null
            }
        }
    }

    // checks if the position passed is empty in the my_board data structure
    // if the position is empty returns false
    protected boolean Cell_Set(int row, int column ) {
        return !(my_board[row][column] == ' ');
    }


    // returns an int indicating game status
    // will be used when a player plays a move
    protected int gameStatus() {
        // 0 - Continue
        // 1 - X wins
        // 2 - O wins
        //-1 - Draw

        int rowX = 0, columnX = 0;
        int rowO = 0, columnO = 0;

        for(int i = 0; i< grid_size; i++){
            if(check_Row_Equality(i,'X'))
                return 1;
            if(check_Column_Equality(i, 'X'))
                return 1;
            if(check_Row_Equality(i,'O'))
                return 2;
            if(check_Column_Equality(i,'O'))
                return 2;
            if(check_Diagonal('X'))
                return 1;
            if(check_Diagonal('O'))
                return 2;
        }
        boolean boardFull = true;
        for(int i = 0; i< grid_size; i++){
            for(int j = 0; j< grid_size; j++){
                if(my_board[i][j]==' ')
                    boardFull = false;
            }
        }
        if(boardFull)
            return -1;
        else return 0;
    }

    // logic used in game status
    protected boolean check_Diagonal(char player){
        int count_Equal1 = 0,count_Equal2 = 0;
        for(int i = 0; i< grid_size; i++)
            if(my_board[i][i]==player)
                count_Equal1++;
        for(int i = 0; i< grid_size; i++)
            if(my_board[i][grid_size -1-i]==player)
                count_Equal2++;
        if(count_Equal1== grid_size || count_Equal2== grid_size)
            return true;
        else return false;
    }

    protected boolean check_Row_Equality(int r, char player){
        int count_Equal=0;
        for(int i = 0; i< grid_size; i++){
            if(my_board[r][i]==player)
                count_Equal++;
        }

        if(count_Equal== grid_size)
            return true;
        else
            return false;
    }

    protected boolean check_Column_Equality(int c, char player){
        int count_Equal=0;
        for(int i = 0; i< grid_size; i++){
            if(my_board[i][c]==player)
                count_Equal++;
        }

        if(count_Equal== grid_size)
            return true;
        else
            return false;
    }

    protected void stopMatch(){
        for(int i = 0; i< gameBoard.getChildCount(); i++){
            TableRow row = (TableRow) gameBoard.getChildAt(i);
            for(int j = 0; j<row.getChildCount(); j++){
                TextView tv = (TextView) row.getChildAt(j);
                tv.setOnClickListener(null);
            }
        }
    }

    // called when reset is called
    // clears my_board data structure
    // clears UI


    // called when the TextView is pressed
    // arguments passed are the position using row and column and the TestView to be modified
    View.OnClickListener Move(final int r, final int c, final TextView tv){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the position is empty (' ')
                // sets that position to turn string
                if (!Cell_Set(r, c)) {
                    my_board[r][c] = turn;
                    // updating the UI
                    if (turn == 'X') {
                        tv.setText(R.string.X);
                        turn = 'O';
                    } else if (turn == 'O') {
                        tv.setText(R.string.O);
                        turn = 'X';
                    }
                    if (gameStatus() == 0) {
                        activityMainBinding.turn.setText("Turn: Player " + turn);
                    }
                    else if(gameStatus() == -1){
                        activityMainBinding.turn.setText("This is a Draw match");
                        stopMatch();
                    }
                    else{
                        activityMainBinding.turn.setText(turn+" Loses!");
                        stopMatch();
                    }
                }
                else {
                    activityMainBinding.turn.setText(activityMainBinding.turn.getText()+" Choose an Empty Call");
                }
            }
        };
    }


}