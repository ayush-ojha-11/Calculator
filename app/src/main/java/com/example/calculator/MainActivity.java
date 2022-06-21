package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.faendir.rhino_android.RhinoAndroidHelper;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    TextView solutionTv,resultTv;

    AppCompatButton buttonC,buttonOpenBrackets,buttonCloseBrackets,buttonAllClear,buttonDot,buttonEqual;
    AppCompatButton buttonDiv,buttonMul,buttonSum,buttonSub;
    AppCompatButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solutionTv=findViewById(R.id.solution_tv);
        resultTv=findViewById(R.id.result_tv);

        //assigning ids

        assignID(buttonC,R.id.button_c);
        assignID(buttonOpenBrackets,R.id.button_opencbrac);
        assignID(buttonCloseBrackets,R.id.button_closebrac);
        assignID(buttonAllClear,R.id.button_allclear);
        assignID(buttonDot,R.id.button_dot);
        assignID(buttonEqual,R.id.button_equals);

        assignID(buttonDiv,R.id.button_div);
        assignID(buttonMul,R.id.button_mul);
        assignID(buttonSum,R.id.button_sum);
        assignID(buttonSub,R.id.button_minus);

        assignID(button0,R.id.button_0);
        assignID(button1,R.id.button_1);
        assignID(button2,R.id.button_2);
        assignID(button3,R.id.button_3);
        assignID(button4,R.id.button_4);
        assignID(button5,R.id.button_5);
        assignID(button6,R.id.button_6);
        assignID(button7,R.id.button_7);
        assignID(button8,R.id.button_8);
        assignID(button9,R.id.button_9);

    }

    public  void assignID(AppCompatButton button, int id)
    {
        button= findViewById(id);
        button.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        AppCompatButton button = (AppCompatButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC"))
        {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(buttonText.equals("="))
        {

            String finalResult = getResult(dataToCalculate);

                        if(!finalResult.equals("Error") && !dataToCalculate.equals("")) {
                        resultTv.setText(finalResult);
            }
            else {
                resultTv.setText("Error");
                Toast.makeText(this,"Write proper expression!",Toast.LENGTH_SHORT).show();
            }

            return;
        }
        if(button.getId()==R.id.button_c)
        {
            if(dataToCalculate.length()>=1)
            dataToCalculate=dataToCalculate.substring(0,dataToCalculate.length()-1);
            else
                solutionTv.setText("");
        }
        else
        {
            dataToCalculate= dataToCalculate+buttonText;
        }

        solutionTv.setText(dataToCalculate);




    }

    String getResult(String data)
    {
         try
         {
             Context context = new RhinoAndroidHelper().enterContext();
             context.setOptimizationLevel(-1);
             Scriptable scriptable =context.initStandardObjects();
             String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
             if(finalResult.endsWith(".0"))
             {
                 finalResult = finalResult.replace(".0","");
             }
             return finalResult;


         }
         catch (Exception e)
         {

             return "Error";

         }
    }
}