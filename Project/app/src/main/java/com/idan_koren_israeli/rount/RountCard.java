package com.idan_koren_israeli.rount;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class RountCard {

    private View card;
    private ImageButton statsButton;
    private TextView rountName;
    private TextView statsText; // Distance and best time
    Context context;

    private int[] defaultSize; // 0 for width, 1 for height. Sizes at start (before changes are made)

    private final float MAX_CARD_MULTIPLIER = 1.1f;
    private final float MIN_CARD_MULTIPLIER = 0.8f;
    private final float MAX_STATS_TEXT_HEIGHT = 0.4f;

    private final float NOT_SELECTED_CARD_ALPHA = 0.5f;


    private float selectedPercent; // between 0 (not selected) and 1 (selected and therefore the biggest)


    public RountCard(View card, Context context){

        this.card = card;
        statsButton = card.findViewById(R.id.stats_button);
        rountName = card.findViewById(R.id.name_text);
        statsText = card.findViewById(R.id.stats_text);
        this.context = context;
        this.selectedPercent = 0;

        this.defaultSize = new int[]{card.getLayoutParams().width, card.getLayoutParams().height};
    }


    public ImageButton getStatsButton() {
        return statsButton;
    }

    public void setStatsButton(ImageButton statsButton) {
        this.statsButton = statsButton;
    }

    public TextView getRountName() {
        return rountName;
    }

    public void setRountName(TextView rountName) {
        this.rountName = rountName;
    }

    public TextView getStatsText() {
        return statsText;
    }

    public void setStatsText(TextView statsText) {
        this.statsText = statsText;
    }

    public float getSelectedPercent() {
        return selectedPercent;
    }

    public View getCard() {
        return card;
    }

    public void setCard(View card) {
        this.card = card;
    }

    public void setSelectedPercent(float selectedPercent) {
        this.selectedPercent = selectedPercent;
    }

    // Calls to all inner changes and will be called every frame
    public void update(float selectedPercent){
        setSelectedPercent(selectedPercent);

        updateCard(); // As start, we update card itself, inner views sizes are updated via percent-constraint-layout
        updateStatsText(); // Text should be smaller when card is not selected

    }

    // Resizing background / card itself view
    public void updateCard(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) card.getLayoutParams();
        int[] currentSize = getCurrentCardSize(); //
        params.width = currentSize[0];
        params.height =  currentSize[1];
        card.setLayoutParams(params); // Updating the card sizes

        //Not sure if this feature should be included
        //card.setAlpha(Math.max(selectedPercent, NOT_SELECTED_CARD_ALPHA));
    }


    // Changes statistics text opacity and size
    public void updateStatsText()
    {
        //Setting text opacity
        int textAlpha = Math.max(0,(int)(selectedPercent * 255));
        statsText.setTextColor(Color.argb(textAlpha,127,0,127)); // Should take here from color context getResources

        //Setting text height
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) statsText.getLayoutParams();
        params.matchConstraintPercentHeight = (MAX_STATS_TEXT_HEIGHT * selectedPercent);

        statsText.setLayoutParams(params);

    }

    // Calculating size card based on default size, multipliers and percentage
    public int[] getCurrentCardSize()
    {
        int width = (int) (defaultSize[0] * (((1-selectedPercent) * MIN_CARD_MULTIPLIER) + (selectedPercent * MAX_CARD_MULTIPLIER)));
        int height = (int) (defaultSize[1] * (((1-selectedPercent) * MIN_CARD_MULTIPLIER) + (selectedPercent * MAX_CARD_MULTIPLIER)));
        return new int[]{width,height};
    }


}
