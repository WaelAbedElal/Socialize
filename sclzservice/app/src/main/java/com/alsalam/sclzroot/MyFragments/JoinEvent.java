package com.alsalam.sclzroot.MyFragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.alsalam.sclzroot.handlers.EventsHandler;
import com.example.sclzservice.R;

/**
 * Created by hp1 on 15/03/2016.
 */
public class JoinEvent extends DialogFragment
{

    protected EventsHandler eventsHandler;
    private TextView tvTime,tvHours,tvLocation,tvAge, tvTitle, tvLimitParticipants, tvEventDate, tvDescription,tvRequirments,tvGen;
    private Button btnJoin;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EventsHandler){
            this.eventsHandler= (EventsHandler) context;
        }
    }

    @Override
    public void onDetach() {
        this.eventsHandler=null;
        super.onDetach();
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.join_event, container, false);
        init(view);
        setListerners();
        return  view;
    }

    private void setListerners() {
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventsHandler!=null){
                    //pass event object or id pass them by bundle
                    //join event may implemment serializable interface to pass serializable object for ease of access
                    eventsHandler.onJoinEvent(null);
                }
                dismiss();
            }
        });
    }


    protected void init(View view) {

        tvTime = (TextView) view.findViewById(R.id.tvTime); // beginning time
        tvLocation = (TextView) view.findViewById(R.id.tvLocation);// location
        tvAge = (TextView) view.findViewById(R.id.tvAge);// age range
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvLimitParticipants = (TextView) view.findViewById(R.id.tvLimit);
        tvEventDate = (TextView) view.findViewById(R.id.tvEventDate);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvGen=(TextView)view.findViewById(R.id.tvGen);
        btnJoin=(Button)view.findViewById(R.id.btnJoin);
         //  spnType=(Spinner)view.findViewById(R.id.spnType);// choosing event_itm type
       // btnDone = (Button) view.findViewById(R.id.btnDone);// the Done button which take you to the home Page
     //   getLocBtn = (ImageButton) view.findViewById(R.id.getLocBtn);

    }



}