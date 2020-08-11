package com.idan_koren_israeli.rount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationBar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout[] buttons = new LinearLayout[4]; // From start(left) to end(right)


    public NavigationBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationBar.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationBar newInstance(String param1, String param2) {
        NavigationBar fragment = new NavigationBar();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_bar, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        findViews(view);

        for(View v : buttons)
            setLayoutListener(v);

    }


    private void findViews(View view){
        buttons[0] = view.findViewById(R.id.icon_home);
        buttons[1] = view.findViewById(R.id.icon_create);
        buttons[2] = view.findViewById(R.id.icon_statistics);
        buttons[3] = view.findViewById(R.id.icon_settings);
    }

    private void setLayoutListener(View view){
        if(view==null)
            return;

        Button button = view.findViewById(R.id.icon_button);
        button.setOnClickListener(new ChangeActivityListener(getActivity()));
    }

}

class ChangeActivityListener implements View.OnClickListener{

    private Context context;

    public ChangeActivityListener(Context appContext){
        this.context = appContext;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        System.out.println("child: " + context.getResources().getResourceName(view.getId()));
        System.out.println("root " + context.getResources().getResourceName(((ViewGroup)view.getParent()).getId()));
        int parentId = (((ViewGroup) view.getParent())).getId();
        switch (parentId){
            case(R.id.icon_create):
                intent = new Intent(context, CreateRount.class); // Should be changed to settings when will be created
                break;
            case(R.id.icon_statistics):
                intent = new Intent(context, MainActivity.class); // Should be changed to statistics when will be created
                break;
            case(R.id.icon_settings):
                intent = new Intent(context, MainActivity.class); // Should be changed to settings when will be created
                break;
            default:
                intent = new Intent(context, MainActivity.class); // 'Home' icon gets to here
                break;
        }

        context.startActivity(intent);
    }
}