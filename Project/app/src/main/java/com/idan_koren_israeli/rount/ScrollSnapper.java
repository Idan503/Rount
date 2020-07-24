package com.idan_koren_israeli.rount;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

//This is a custom view for Rount selection on main menu
class ScrollSnapper extends ScrollView
{

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
        View view = LayoutInflater.from(context).inflate(R.layout.scroll_snapper,this);

        LinearLayout parent = findViewById(R.id.rount_cards_layout);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<10;i++)
            inflater.inflate(R.layout.rount_card,parent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        counter++;

    }


}
