package com.example.setgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class gameset extends AppCompatActivity {
    // Views
    Button btn1 ,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    TextView playerScore,compScore,playerText;
    // Game variables
    int pawnPlaced=0;
    int playerPawnsPlaced = 0;
    boolean isPawnSelected = false;
    int selectedPawn = -1;
    boolean isSetWin = false;
    int playerScores[] = new int[]{0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameset);
        btn1=findViewById(R.id.button_1);
        btn2=findViewById(R.id.button_2);
        btn3=findViewById(R.id.button_3);
        btn4=findViewById(R.id.button_4);
        btn5=findViewById(R.id.button_5);
        btn6=findViewById(R.id.button_6);
        btn7=findViewById(R.id.button_7);
        btn8=findViewById(R.id.button_8);
        btn9=findViewById(R.id.button_9);
        playerText=findViewById(R.id.player_text);
        Bundle bundle = getIntent().getExtras();
        String value=bundle.getString("Hi");
        playerText.setText(value);

        Dictionary<Integer, Integer> gameBoard = new Hashtable<Integer, Integer>();
        Dictionary<Integer, Integer> compPawns = new Hashtable<>();
        Dictionary<Integer, Integer> playerPawns = new Hashtable<Integer, Integer>();
        Random random = new Random();
        int neighbours[][] = {{1,4,3},{0,2,4},{1,4,5},{0,4,6},{0,1,2,3,5,6,7,8},{2,4,8},{3,4,7},{6,4,8},{7,5,4}};
        // Populate gameBoard dictionary with initial values
        gameBoard.put(0, 0);
        gameBoard.put(1, 0);
        gameBoard.put(2, 0);
        gameBoard.put(3, 0);
        gameBoard.put(4, 0);
        gameBoard.put(5, 0);
        gameBoard.put(6, 0);
        gameBoard.put(7, 0);
        gameBoard.put(8, 0);
        // Populate compPawns dictionary with initial values
        compPawns.put(0,-1);
        compPawns.put(1,-1);
        compPawns.put(2,-1);
        playerPawns.put(0,-1);
        playerPawns.put(1,-1);
        playerPawns.put(2,-1);
        playerScore = findViewById(R.id.playerScore);
        compScore = findViewById(R.id.compScore);
        Button  buttons[] = new Button[]{btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9};
        for (int i = 0;i< buttons.length;i++) {
            int finalI = i;

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int btnId = finalI;
                    if(pawnPlaced<6) {
                        int btnValue = gameBoard.get(btnId);
                        if (btnValue == 0) {
                            buttons[finalI].setBackgroundResource(R.drawable.player_pawn);
                            gameBoard.put(btnId,1);
                            pawnPlaced++;
                            playerPawns.put(playerPawnsPlaced,btnId);
                            playerPawnsPlaced++;
                            computerPlay();
                            if(pawnPlaced>3) {
                                checkWinner();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Pawn has been already placed",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if(!isPawnSelected) {
                            if(playerPawns.get(0) == btnId || playerPawns.get(1) == btnId|| playerPawns.get(2) == btnId ) {
                                buttons[btnId].setBackgroundResource(R.drawable.select_pawn);
                                isPawnSelected=true;
                                selectedPawn=btnId;

                                for(int i = 0;i<neighbours[btnId].length;i++) {
                                    if(gameBoard.get(neighbours[btnId][i]) ==0)
                                        buttons[neighbours[btnId][i]].setBackgroundResource(R.drawable.empty_pawn_space);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please select your pawn!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            if(playerPawns.get(0) == btnId || playerPawns.get(1) == btnId|| playerPawns.get(2) == btnId )  {
                                for(int i = 0;i<neighbours[selectedPawn].length;i++) {
                                    if(gameBoard.get(neighbours[selectedPawn][i]) ==0)
                                        buttons[neighbours[selectedPawn][i]].setBackgroundResource(R.drawable.pawn_placable_space);
                                }
                                buttons[selectedPawn].setBackgroundResource(R.drawable.player_pawn);
                                selectedPawn=btnId;
                                buttons[selectedPawn].setBackgroundResource(R.drawable.select_pawn);
                                for(int i = 0;i<neighbours[btnId].length;i++) {
                                    if(gameBoard.get(neighbours[btnId][i]) ==0)
                                        buttons[neighbours[btnId][i]].setBackgroundResource(R.drawable.empty_pawn_space);
                                }
                            } else {
                                boolean isMoved = false;
                                for(int i = 0;i<neighbours[selectedPawn].length;i++) {
                                    if(neighbours[selectedPawn][i]==btnId && gameBoard.get(btnId) ==0) {
                                        buttons[btnId].setBackgroundResource(R.drawable.player_pawn);
                                        gameBoard.put(btnId,1);
                                        gameBoard.put(selectedPawn,0);
                                        for(int j =0;j<3;j++) {
                                            if(playerPawns.get(j) == selectedPawn) {
                                                playerPawns.put(j, btnId);
                                                break;
                                            }
                                        }
                                        buttons[selectedPawn].setBackgroundResource(R.drawable.pawn_placable_space);
                                        isMoved = true;
                                        for(int j = 0;j<neighbours[selectedPawn].length;j++) {
                                            if(gameBoard.get(neighbours[selectedPawn][j])==0)
                                                buttons[neighbours[selectedPawn][j]].setBackgroundResource(R.drawable.pawn_placable_space);
                                        }
                                        computerPlay();
                                        break;
                                    } else if(gameBoard.get(neighbours[selectedPawn][i])==0) {
                                        buttons[neighbours[selectedPawn][i]].setBackgroundResource(R.drawable.pawn_placable_space);
                                    }
                                }
                                if(!isMoved) {
                                    buttons[selectedPawn].setBackgroundResource(R.drawable.player_pawn);
                                    Toast.makeText(getApplicationContext(), "Can't place the pawn there!", Toast.LENGTH_SHORT).show();
                                }
                                selectedPawn=-1;
                                isPawnSelected=false;
                            }
                        }
                    }
                }
                private void computerPlay() {
                    if (pawnPlaced > 5) {
                        while (true) {
                            int compPawnNumber = random.nextInt(3); // select one of three comp pawns
                            int placedIndex = compPawns.get(compPawnNumber); // get selected pawn placed index
                            boolean isLocked = true;
                            for (int i = 0; i < neighbours[placedIndex].length; i++) {
                                if (gameBoard.get(neighbours[placedIndex][i]) == 0) {
                                    isLocked = false;
                                    break;
                                }
                            }
                            if (isLocked) continue;
                            else {
                                int nextCompMovePlaceIndex = getRandomEmptyIndex(neighbours[placedIndex].length, placedIndex);
                                buttons[placedIndex].setBackgroundResource(R.drawable.pawn_placable_space);
                                gameBoard.put(placedIndex, 0);
                                compPawns.put(compPawnNumber, nextCompMovePlaceIndex);
                                buttons[nextCompMovePlaceIndex].setBackgroundResource(R.drawable.comp_pawn);
                                gameBoard.put(nextCompMovePlaceIndex, 2);
                                checkWinner();
                                break;
                            }
                        }
                    } else {
                        while (true) {
                            int index = random.nextInt(9);
                            if (gameBoard.get(index) > 0) {
                                continue;
                            } else {
                                buttons[index].setBackgroundResource(R.drawable.comp_pawn);
                                pawnPlaced++;
                                gameBoard.put(index, 2);
                                compPawns.put(pawnPlaced / 2 - 1, index);
                                checkWinner();
                                break;
                            }
                        }
                    }
                }
                private int getRandomEmptyIndex( int length,int index) {
                    while(true) {
                        int tempIndex = random.nextInt(length);
                        int nextIndex = neighbours[index][tempIndex];
                        if(gameBoard.get(nextIndex) !=0) continue;
                        else {
                            return nextIndex;
                        }
                    }
                }
                private void checkWinner() {
                    int winner = 0;
                    if(gameBoard.get(0) == gameBoard.get(1) && gameBoard.get(1) ==gameBoard.get(2))
                        winner = gameBoard.get(2);
                    else if(gameBoard.get(0) == gameBoard.get(3) && gameBoard.get(3) ==gameBoard.get(6))
                        winner= gameBoard.get(6);
                    else if(gameBoard.get(0) == gameBoard.get(4) && gameBoard.get(4) ==gameBoard.get(8))
                        winner= gameBoard.get(8);
                    else if(gameBoard.get(2) == gameBoard.get(4) && gameBoard.get(4) ==gameBoard.get(6))
                        winner= gameBoard.get(6);
                    else if(gameBoard.get(2) == gameBoard.get(5) && gameBoard.get(5) ==gameBoard.get(8))
                        winner= gameBoard.get(8);
                    else if(gameBoard.get(1) == gameBoard.get(4) && gameBoard.get(7) ==gameBoard.get(4))
                        winner= gameBoard.get(4);
                    else if(gameBoard.get(3) == gameBoard.get(4) && gameBoard.get(4) ==gameBoard.get(5))
                        winner= gameBoard.get(5);
                    else if(gameBoard.get(6) == gameBoard.get(7) && gameBoard.get(7) ==gameBoard.get(8))
                        winner= gameBoard.get(8);

                    if(winner!=0) {
                        isSetWin = true;
                        playerScores[winner-1] = playerScores[winner-1]+1;
                        if(playerScores[winner-1]>1) {
                            resetGame();
                            playerScores[0] = 0;
                            playerScores[1] = 0;
                            Bundle bundle =new Bundle();
                            Intent redirect = new Intent(gameset.this,lastpage.class);

                            if(winner-1==0) {
                                bundle.putString("won",value);
                                redirect.putExtras(bundle);
                            }
                            else{
                                bundle.putString("won","comp");
                                redirect.putExtras(bundle);
                            }
                            startActivity(redirect);
                            return;
                        } else {
                            if (winner-1==0) {
                                Toast.makeText(getApplicationContext(), "You won the set!", Toast.LENGTH_LONG).show();
                                playerScore.setText(playerScores[0]+"");
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Comp won the set!", Toast.LENGTH_LONG).show();
                                compScore.setText(playerScores[1]+"");
                            }
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                            resetGame();
                        }
                    }
                }
                private void resetGame() {
                    for(int i =0;i<9;i++) {
                        buttons[i].setBackgroundResource(R.drawable.pawn_placable_space);
                        gameBoard.put(i,0);
                    }
                    selectedPawn = -1;
                    isSetWin = false;
                    isPawnSelected =false;
                    pawnPlaced = 0;
                    playerPawnsPlaced = 0;
                    for (int i =0;i<3;i++) {
                        playerPawns.put(i,0);
                        compPawns.put(i,0);
                    }
                }
            });
        }

    }
}