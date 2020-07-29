package com.idan_koren_israeli.rount;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;

//This is a custom view for Rount selection on main menu

class ScrollSnapper extends ScrollView
{


    final float CARD_MAX_SIZE_MULTIPLIER = 1.2f;
    final float CARD_MIN_SIZE_MULTIPLIER = 0.8f;
    int[] startSize = new int[2];
    int[] anchorPos = new int[2]; //Cordintes where the card should be the biggest.
    int[] childPos = new int[2]; //this array will store x,y values of children
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

        startSize[0] = parent.getChildAt(0).getLayoutParams().width;
        startSize[1] = parent.getChildAt(0).getLayoutParams().height;
        //Picking an example before changing sizes in order to have a constant starting value

        parent.getChildAt(0).getLocationOnScreen(anchorPos);
        // Anchor which is the biggest card position is determent by the first child as constant
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);


        //Each child will be resized
        for(int i=0;i<parent.getChildCount();i++){
            View child = parent.getChildAt(i);
            child.getLocationOnScreen(childPos); //this function changes the array
            //Childs that are close to anchor point should be bigger
            float mult = CARD_MAX_SIZE_MULTIPLIER - ((float)i/10);
            System.out.println("child pos: " + childPos[0] + " , " + childPos[1]);
            System.out.println("anchor pos: " + anchorPos[0] + " , " + anchorPos[1]);
            System.out.println("parent hegiht: " +  parent.getLayoutParams().height);
            resize(child, (int) (startSize[0] * mult),(int)(startSize[1] * mult) );
        }




    }


    private void resize(View view, int width, int height){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    //This function calculates distance between 2 vectors represented as two [2] arrays
    private float distance(int[] v1, int[] v2){
        double xSquared = (int) Math.pow(v1[0] - v2[0], 2);
        double ySquared = (int) Math.pow(v1[1] - v2[1], 2);
        return (float) Math.sqrt(xSquared + ySquared);
    }






}
