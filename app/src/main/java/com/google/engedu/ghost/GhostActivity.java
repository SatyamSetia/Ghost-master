package com.google.engedu.ghost;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity   {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private TextView gameStatus;
    private TextView text;
    //private TextView gameStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        text = (TextView) findViewById(R.id.ghostText);
        gameStatus = (TextView) findViewById(R.id.gameStatus);

        InputStream i= null;
        try {
            i = getAssets().open("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dictionary =new FastDictionary(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(null);

    //    userTurn = random.nextBoolean();

    }

    private void declareResult(boolean userTurn) {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if(userTurn){
            label.setText("User won");
        }else{
            label.setText("Computer won");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }
    public void challenge(View v)
    {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        String s=(String)text.getText();
        if(s!=null)
        {
            if(s.length()>3 && dictionary.isWord(s))
            {
                declareResult(true);
                Toast.makeText(getApplicationContext(),"word formed "+s, Toast.LENGTH_SHORT).show();
            }
            else
            {
                String word=dictionary.getAnyWordStartingWith(s);
                if(word==null)
                {
                    declareResult(true);
                    Toast.makeText(getApplicationContext(),"no word with prefix "+s, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    declareResult(false);
                    Toast.makeText(getApplicationContext(),"word having prefix "+s+" is "+word, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    public void restart(View v)
    {

        Intent intent = new Intent(GhostActivity.this,
                GhostActivity.class);

        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String s=(String)text.getText();
        if(s.length()>3 && dictionary.isWord(s))
        {
            declareResult(false);
            Toast.makeText(getApplicationContext(),"word formed "+s, Toast.LENGTH_SHORT).show();
        }
        else
        {
           String word=dictionary.getAnyWordStartingWith(s);
            if(word==null)
            {
                declareResult(false);
                Toast.makeText(getApplicationContext(),"no word with prefix "+s, Toast.LENGTH_SHORT).show();
            }
            else
            {
                 int len=s.length();
                String com="";
                if(len!=0)
                {
                    char ch=word.charAt(len);
                    String cat=String.valueOf(ch);
                     com=s.concat(cat);
                }
                else
                {
                    final int N = 26;

                    Random r = new Random();
                       char ch= (char)(97+r.nextInt(N));
                    com=String.valueOf(ch);
                }
                text.setText(com);
                userTurn = true;
                label.setText(USER_TURN);
            }
        }
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
         text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        TextView label = (TextView) findViewById(R.id.gameStatus);

        String word = null;

        word = (String)text.getText() + "" + ((char)event.getUnicodeChar());
        text.setText(word);

        if(dictionary.isWord(word)){
            gameStatus.setText("Valid word formed");
        }

        userTurn=false;
        label.setText(COMPUTER_TURN);
        computerTurn();
        return super.onKeyUp(keyCode, event);
    }
}
