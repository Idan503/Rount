package com.idan_koren_israeli.rount;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRountInput#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRountInput extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Button nextButton, backButton;
    ViewGroup nameLayout, distanceLayout, locationLayout;

    ViewGroup currentLayout;

    public CreateRountInput() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment create_rount_input.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRountInput newInstance(String param1, String param2) {
        CreateRountInput fragment = new CreateRountInput();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        //Setting the back button to gone 
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("Hello ");
        return inflater.inflate(R.layout.fragment_create_rount_input, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        findViews(view);

        setCurrentLayout(nameLayout); // nameLayout layout is the first that will apear

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup nextLayout = findNextLayout();
                if(nextLayout==null){
                    System.out.println("Finished Input Stage");
                }
                else {
                    setCurrentLayout(nextLayout);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup previousLayout = findBackLayout();
                if(previousLayout==null){
                    System.out.println("There is no back");
                }
                else {
                    setCurrentLayout(previousLayout);
                }
            }
        });

    }

    private void setCurrentLayout(ViewGroup layout){
        if(currentLayout!=null) {
            ViewGroup oldLayout = currentLayout;
            oldLayout.setVisibility(View.GONE);
            //Hiding the old layout
        }

        layout.setVisibility(View.VISIBLE);
        currentLayout = layout;
        //Updating current layout to be the new one

        //Edge case at start of fragment
        if(currentLayout==nameLayout){
            setNextBackButtonsVisibility(true,false);
        }
        else
            setNextBackButtonsVisibility(true,true);

    }

    // Getting the layout that should appear after pressing "next"
    private ViewGroup findNextLayout(){
        switch (currentLayout.getId()){
            case R.id.create_name_layout:
                return distanceLayout;
            case R.id.create_distance_layout:
                return locationLayout;
            default:
                return null; // There is no next layout

        }
    }

    // Getting the layout that should appear after pressing "back"
    private ViewGroup findBackLayout(){
        switch (currentLayout.getId()){
            case R.id.create_distance_layout:
                return nameLayout;
            case R.id.create_location_layout:
                return distanceLayout;
            default:
                return null; // There is no back layout

        }
    }

    // Getting 2 boolean values if to show or hide buttons
    private void setNextBackButtonsVisibility(boolean next, boolean back){
        int nextProperty = (next)? View.VISIBLE : View.GONE;
        int backProperty = (back)? View.VISIBLE : View.GONE;
        nextButton.setVisibility(nextProperty);
        backButton.setVisibility(backProperty);
        System.out.println("Next is now " + nextProperty);
    }

    private void findViews(View parentView){
        nextButton = parentView.findViewById(R.id.fragment_create_input_next_button);
        backButton = parentView.findViewById(R.id.fragment_create_input_back_button);
        nameLayout = parentView.findViewById(R.id.create_name_layout);
        distanceLayout = parentView.findViewById(R.id.create_distance_layout);
        locationLayout = parentView.findViewById(R.id.create_location_layout);
    }
}