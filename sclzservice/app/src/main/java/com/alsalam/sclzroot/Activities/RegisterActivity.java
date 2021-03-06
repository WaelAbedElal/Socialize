package com.alsalam.sclzroot.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alsalam.sclzroot.TableManager.DataBaseMngr;
import com.alsalam.sclzroot.TableManager.UserTbl;
import com.alsalam.sclzroot.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText etMail_Address, etUsername, etPass, etConfirm_Pass, etLocation, etPhoneNumber, etFirstName, etLastName,etEventDate;
    // private Spinner spnDay, spnMonth, spnYear;
    private RadioButton rbMale, rbFemale;
    private RadioGroup rgGender;
    private Button btnSubmit;
    private MobileServiceClient mClient;
    private String userGender = "";
    private ProgressBar mProgressBar;
    private java.util.Date eventDate;
    private int mYear, mMonth, mDay;


    private  ImageButton imageBirthDate;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private EditText etBithDay;
    UserTbl user;
    private EditText firstQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         user=new UserTbl();

        etMail_Address = (EditText) findViewById(R.id.etMail_address);//  type your email address
        etPass = (EditText) findViewById(R.id.etPass);// type password
        etConfirm_Pass = (EditText) findViewById(R.id.etConfirm_pass);// confirm your password
        etLocation = (EditText) findViewById(R.id.etLocation);
        etPhoneNumber = (EditText) findViewById(R.id.tvPhone);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etBithDay =(EditText)findViewById(R.id.etBirthDay);

        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rbFemale = (RadioButton) findViewById(R.id.rbFemale);// choosing male or female
        rbMale = (RadioButton) findViewById(R.id.rbMale);// choosig female or male
        firstQuestion=(EditText)findViewById(R.id.firstQuestion);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        imageBirthDate= (ImageButton)findViewById(R.id.imageBirthDate);

        imageBirthDate.setOnClickListener(this);




    }
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    public UserTbl getUserInfo() {
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        // user.setId(user.getId().toString());
        user.setUserAddress(etLocation.getText().toString());
        user.setUserEmail(etMail_Address.getText().toString());
        user.setUserPassword(etPass.getText().toString());
        user.setUserPhone(etPhoneNumber.getText().toString());
        user.setUserTafkeed(UserTbl.Wait);
        user.setRestoringKey(firstQuestion.getText().toString());
        if(rgGender.getCheckedRadioButtonId()==R.id.rbFemale)
        user.setUserGender("female");
        else
        user.setUserGender("male");

        // TODO complete other fields
//        UserTbl user = new UserTbl(
//                (int) (Math.random() * 99999999) + "",
//                etUsername.getText().toString(),
//                etPass.getText().toString(),
//                etFirstName.getText().toString(),
//                etLastName.getText().toString(),
//                etMail_Address.getText().toString(),
//                "normalUser",
//                etLocation.getText().toString(),
//                new Date(1998, 2, 17),
//                userGender,
//                etPhoneNumber.getText().toString()
//        );

        Log.d("test", user.toString());
        return user;
    }
//s

    public boolean areFieldsFilled() {


         if (etPass.getText().toString().length() == 0) {
            etPass.requestFocus();
            etPass.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (etConfirm_Pass.getText().toString().length() == 0) {
            etConfirm_Pass.requestFocus();
            etConfirm_Pass.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!etConfirm_Pass.getText().toString().equals(etPass.getText().toString())) {
            etConfirm_Pass.requestFocus();
            etConfirm_Pass.setError("PASSWORDS DON'T MATCH");
            return false;
        }

        else if (etFirstName.getText().toString().length() == 0) {
            etFirstName.requestFocus();
            etFirstName.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (etLastName.getText().toString().length() == 0) {
            etLastName.requestFocus();
            etLastName.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if (!checkEmail(etMail_Address.getText().toString())) {
            etMail_Address.requestFocus();
            etMail_Address.setError("EMAIL FORMAT NOT CORRECT");
            return false;
        }
        else if (etLocation.getText().toString().length() == 0) {
            etLocation.requestFocus();
            etLocation.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else
        if (rgGender.getCheckedRadioButtonId() != R.id.rbMale && rgGender.getCheckedRadioButtonId()!= R.id.rbFemale) {
            rgGender.requestFocus();
            Toast.makeText(getBaseContext(), "GENDER NOT CHECKED", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etPhoneNumber.toString().length() == 0) {
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError("FIELD CANNOT BE EMPTY");
            return false;
        }else
        if (firstQuestion.toString().length() == 0) {
            firstQuestion.requestFocus();
            firstQuestion.setError("FIELD CANNOT BE EMPTY");
            return false;
        }

        return true;
    }







    public void addUserToDB(final UserTbl user)
    {
        try {

            if(mClient==null)
            mClient = new MobileServiceClient("https://sclzservice.azurewebsites.net",getBaseContext());
            final MobileServiceTable<UserTbl> mtable = mClient.getTable(UserTbl.class);
            mtable.where().field("userEmail").eq(etMail_Address.getText().toString()).execute(new TableQueryCallback<UserTbl>() {
                @Override
                public void onCompleted(List<UserTbl> result, int count, Exception exception, ServiceFilterResponse response) {

                    // it appears for me and error here, ** remember to ask about it
                    if (result != null && result.size() > 0) {
                        etMail_Address.setError("Mail Already Found!");

                    } else {
                        mtable.insert(user, new TableOperationCallback<UserTbl>() {
                            @Override
                            public void onCompleted(UserTbl entity, Exception exception, ServiceFilterResponse response) {
                                if (exception == null) {
                                    DataBaseMngr.saveLogIn(entity, getBaseContext());

                                    Toast.makeText(getBaseContext(), "REGISTERED SUCCESSFULY!", Toast.LENGTH_LONG).show();
                                    Log.d("AZURE DB", "SUCCESS! YAY!" + entity.toString());
                                    Intent intent = new Intent(getBaseContext(), MainpageActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), "FAILED", Toast.LENGTH_LONG).show();
                                    Log.d("AZURE DB", "FAILED");
                                    Log.d("AZURE DB", exception.getMessage());
                                    exception.printStackTrace();
                                }
                            }
                        });

                    }
                }
            });




        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {


        if (v == imageBirthDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if(eventDate==null)
                                eventDate=new java.util.Date(year,monthOfYear,dayOfMonth);
                            else {
                                eventDate.setYear(year);
                                eventDate.setMonth(monthOfYear);
                                eventDate.setDate(dayOfMonth);                           }
                            etBithDay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            user.setUserBirthday(eventDate);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        switch(v.getId())
        {
//            case R.id.btnSign:
//                startActivity(new Intent(getBaseContext(), MainHomeActivity.class));
//                break;
            case R.id.btnSubmit:
                if(areFieldsFilled())
                {
                    addUserToDB(getUserInfo());
                    Log.d("testa", "Clicked submit");

                     //...
                    //finish();
                    //startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.rgGender:
                if (rgGender.getCheckedRadioButtonId() == rbMale.getId())
                    userGender = "Male";
                else
                    userGender = "Female";
                break;
        }



    }
}
