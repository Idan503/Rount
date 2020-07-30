package com.idan_koren_israeli.rount;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

//This is a custom view for Rount selection on main menu

class ScrollSnapper extends ScrollView
{


    final float CARD_MAX_SIZE_MULT = 1.1f;
    final float CARD_MIN_SIZE_MULT = 0.8f;
    int[] startSize = new int[2];
    int[] anchorPos = new int[2]; //Cordintes where the card should be the biggest.
    int[] childPos = new int[2]; //this array will store x,y values of children
    boolean firstRender = true; //Flag for detecting first frame that is being drawn
    LinearLayout parent;
    //ArrayList<RountCards> ...

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
                inflater.inflate(R.layout.rount_card,parent); //Adding cards one by one
        //Adding them to the arraylist of cards

        startSize[0] = parent.getChildAt(0).getLayoutParams().width;
        startSize[1] = parent.getChildAt(0).getLayoutParams().height;
        //Picking an example before changing sizes in order to have a constant starting value

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Getting the top child position which will be the place for the biggest card (anchor)
        if(firstRender){
            parent.getChildAt(0).getLocationOnScreen(anchorPos);
            firstRender = !firstRender;
        }

        //Each child will be resized
        for(int i=0;i<10;i++){
            drawCard(parent.getChildAt(i));

        }




    }

    //This function should get a rountcard object maybe
    private void drawCard(View child){
        child.getLocationOnScreen(childPos); //this function changes the array




        //Childs that are close to anchor point should be bigger
        float distanceFromAnchor = (verticalDistance(childPos,anchorPos) / anchorPos[1]);
        float sizeMultiplier = Math.max(CARD_MIN_SIZE_MULT, CARD_MAX_SIZE_MULT - distanceFromAnchor);
        float foremostPercent = ((sizeMultiplier - CARD_MIN_SIZE_MULT) / (CARD_MAX_SIZE_MULT - CARD_MIN_SIZE_MULT)); // 1 for current choose

        TextView statsText =  child.findViewById(R.id.rount_stats); //This should not be called every frame..
        int textAlpha = Math.max(0,(int)(foremostPercent * 255));
        statsText.setTextColor(Color.argb(textAlpha,127,0,127));
        resizeCard(child, (int) (startSize[0] * sizeMultiplier),(int)(startSize[1] * sizeMultiplier));
        resizeInnerText(statsText, foremostPercent);
    }



    //**All those function should get the card object and perform changes on it:::

    //**
    private void resizeCard(View card, int width, int height){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) card.getLayoutParams();
        params.width = width;
        params.height = height;
        card.setLayoutParams(params);
    }

    //**
    private void resizeInnerText(View card, float percent){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) card.getLayoutParams();
        params.matchConstraintPercentHeight = (0.4f * percent); //the 0.4 should come from the different object

        card.setLayoutParams(params);
    }

    //**
    private void setInnerTextOpacity(View card, float percent){

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






}
