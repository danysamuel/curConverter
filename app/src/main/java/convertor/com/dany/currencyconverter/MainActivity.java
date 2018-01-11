package convertor.com.dany.currencyconverter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private EditText fromCurrTxt;
    private TextView resultTxt;
    private TextView resultTxt2;
    private AutoCompleteTextView currencyAutoCompleteTextView;
    private AutoCompleteTextView toCurrautoCompleteTextView;
    private ImageView fromCurImageView;
    private ImageView toCurImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         currencyAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.currencyAutoCompleteTextView);
        toCurrautoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.toCurrautoCompleteTextView);
        //fromCurImageView= (ImageView)findViewById(R.id.frmCRimageView2);
        //toCurImageView= (ImageView)findViewById(R.id.toCRimageView3);

        //fromCurImageView.setBackgroundResource(R.drawable.us);
        //toCurImageView.setBackgroundResource(R.drawable.in);

        String[] countries = getResources().getStringArray(R.array.currency_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        currencyAutoCompleteTextView.setAdapter(adapter);
        toCurrautoCompleteTextView.setAdapter(adapter);

        currencyAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Src Cur Editable",editable.toString());

               // fromCurImageView.setBackgroundResource(R.drawable.us);
            }
        });


        toCurrautoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("To Cur Editable",editable.toString());
            }
        });
    }




    public void convert(View view) throws ExecutionException, InterruptedException {

        String convertCurs;

        AsyncTask<String, Void, String> result=null;
        try{




            String fromCur,toCur;

            if(!(currencyAutoCompleteTextView.getText().toString().equals("") & toCurrautoCompleteTextView.getText().toString().equals(""))){

            fromCur=getCurrencyCode(currencyAutoCompleteTextView.getText().toString());
            toCur=getCurrencyCode(toCurrautoCompleteTextView.getText().toString());
            convertCurs=fromCur+ "_" +toCur;

            fromCurrTxt =findViewById(R.id.amttxt);

            if(!(fromCurrTxt.getText().toString().equals("")) & !fromCur.equals("") & !toCur.equals("")){
            if(Double.parseDouble(fromCurrTxt.getText().toString())>0){

             result=new ReadCurrencyRateResult().execute("http://free.currencyconverterapi.com/api/v3/convert?q="+convertCurs+"&compact=ultra");
             Log.d("Result : ",result.get());

            Double fromCurrD,toCurrD;
            DecimalFormat f = new DecimalFormat("###.####");

            if(result.get()!=null & (Double.parseDouble(result.get().toString())>0)){
                resultTxt =findViewById(R.id.resultTxt);
                resultTxt2 =findViewById(R.id.resultTxt2);
                fromCurrD=Double.parseDouble(fromCurrTxt.getText().toString());
                toCurrD=fromCurrD * Double.parseDouble(result.get().toString());
                String formate = f.format(toCurrD);
                toCurrD= (Double) f.parse(formate);
                resultTxt.setText(toCurrD.toString() + " " + toCur);
                resultTxt2.setText("(1 " + fromCur + " = " + result.get() + " " + toCur+")");
            }


            }
            }


            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), result.get(), Toast.LENGTH_LONG).show();
        }


    }

    private String getCurrencyCode(String cur){
        String[] curA=null;
        if(!cur.equals("")){


            curA=cur.split("-");


        }
        return curA[0].trim();
    }


}
