package com.idan_koren_israeli.rount;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ScaleDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//This is a custom view for Rount selection on main menu

class ScrollSnapper extends ScrollView
{

    final float CARD_MAX_SIZE_MULT = 1.1f;
    final float CARD_MIN_SIZE_MULT = 0.85f;
    int[] anchorPos = new int[2]; //Cordintes where the card should be the biggest.
    int[] childPos = new int[2]; //this array will store x,y values of children
    boolean firstRender = true; //Flag for detecting first frame that is being drawn
    LinearLayout parent;

    // Using this timer to detect when user scroll is finished
    Timer ntimer = new Timer();
    MotionEvent event;
    boolean isSnapping = false;
    int targetSnapY = 0;

    RountCard currentSelected = null;
    ArrayList<RountCard> rountCards = new ArrayList<>();
    // All cards inflated will be saved as objects

    static int counter = 0;
    public ScrollSnapper(Context context){
        super(context);
        init(context);
    }

    public ScrollSnapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollSnapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.scroll_snapper,this); // Adding scroll snapper view
        parent = findViewById(R.id.rount_cards_layout); //Getting the parent of the rount cards


        for(int i=0;i<10;i++)
        {
            View newCard = inflater.inflate(R.layout.rount_card,parent,false); //Adding cards one by one
            parent.addView(newCard); // Setting new cards as children of the parent layout
            rountCards.add(new RountCard(newCard,context));
        }

        //Resetting visability to update parent layout size to wrap all content
        parent.setVisibility(View.GONE);
        parent.setVisibility(View.VISIBLE);

        currentSelected = rountCards.get(0);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Getting the top child position which will be the place for the biggest card (anchor)
        if(firstRender){
            parent.getChildAt(0).getLocationOnScreen(anchorPos);
            firstRender = !firstRender;
        }

        for(RountCard rountCard : rountCards){
            float newPercentValue = getChildSelectedPercent(rountCard.getCard());
            rountCard.update(getChildSelectedPercent(rountCard.getCard()));
            if(rountCard!=currentSelected && newPercentValue >  currentSelected.getSelectedPercent()) {
                //We have a new selected card.
                currentSelected = rountCard;
            }
        }

        for(RountCard card : rountCards){
            if(card==currentSelected)
                card.getRountName().setText("Selected!");
            else
                card.getRountName().setText("Not Selected!");
        }



    }


    //This function calculates distance between 2 vectors represented as two [2] arrays
    private float distance(int[] v1, int[] v2){
        double xSquared = (int) Math.pow(v1[0] - v2[0], 2);
        double ySquared = (int) Math.pow(v1[1] - v2[1], 2);
        return (float) Math.sqrt(xSquared + ySquared);
    }

    private float verticalDistance(int[] v1, int[] v2){
        double ySquared = (int) Math.pow(v1[1] - v2[1], 2);
        return (float) Math.sqrt(ySquared);
    }

    // Selected percent is a number between 0 and 1 - selected card should be the biggest and it will get 1.
    // While unselected card which is not close to the anchor position, should be smaller - will get 0.
    public float getChildSelectedPercent(View child){
        child.getLocationOnScreen(childPos); //this function manipulates the array

        // Children that are close to anchor point should be bigger -> result should be closer to 1
        float distanceFromAnchor = (verticalDistance(childPos,anchorPos) / anchorPos[1]);
        float sizeMultiplier = Math.max(CARD_MIN_SIZE_MULT, CARD_MAX_SIZE_MULT - distanceFromAnchor); // How much should we scale the card
        return ((sizeMultiplier - CARD_MIN_SIZE_MULT) / (CARD_MAX_SIZE_MULT - CARD_MIN_SIZE_MULT)); // ratio is 1 for fully selected
    }



    //Detecting user finishes scroll to perform snap to selected card
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        checkAgain();
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void checkAgain(){
        try{
            ntimer.cancel();
            ntimer.purge();
        }
        catch(Exception e){e.printStackTrace();}
        ntimer = new Timer();
        ntimer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(event.getAction() == MotionEvent.ACTION_UP && !isSnapping) {
                    if (isSelectedViewFarEnough()) {
                        // ScrollView Stopped Scrolling and Finger is not on the ScrollView
                        targetSnapY = (int) currentSelected.getCard().getY();
                    }
                    scrollTo(0, targetSnapY); //Auto Scrolling to the selected card
                    isSnapping = true;
                }
                else{
                    // ScrollView Stopped Scrolling But Finger is still on the ScrollView
                    checkAgain();
                }
            }
        },100);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        isSnapping = false;
        return super.onTouchEvent(event);
    }


    //Checking if current selected card is far enough from the target to make a snap
    //Added this to fix a bug in google pixel phones - scrollview rotated because of 1 coordinate difference
    public boolean isSelectedViewFarEnough(){
        return (Math.abs(currentSelected.getCard().getY()-targetSnapY) > 2);
    }
}



