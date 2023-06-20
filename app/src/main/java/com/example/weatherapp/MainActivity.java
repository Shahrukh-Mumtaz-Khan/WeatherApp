package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
EditText editTextText;
Button button2;
TextView time,latitude,humadity,textView9,sun,sunsett,textView14;
TextView city,country,tempreture;
ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        latitude=findViewById(R.id.latitude);
        time=findViewById(R.id.time);
        humadity=findViewById(R.id.humadity);
        textView9=findViewById(R.id.textView9);
        sun=findViewById(R.id.sun);
        sunsett=findViewById(R.id.sunsett);
        textView14=findViewById(R.id.textView14);
button2=findViewById(R.id.button2);
city=findViewById(R.id.city);
country=findViewById(R.id.country);
imageButton=findViewById(R.id.imageButton);
editTextText=findViewById(R.id.editTextText);
tempreture=findViewById(R.id.tempreture);


button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      findweather();

      editTextText.onEditorAction(EditorInfo.IME_ACTION_GO);
    }
});

    }
    public  void  findweather(){
        String citys=editTextText.getText().toString();
String url="https://api.openweathermap.org/data/2.5/weather?q="+citys+"&appid=c209104a006b7a433cd38362f279a053&units=metric";
        //   String apiKey= "c209104a006b7a433cd38362f279a053";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject object=jsonObject. getJSONObject("sys");
                    String country_find=object.getString("country");
                   country.setText(country_find);

                   String city_Find=jsonObject.getString("name");
                   city.setText(city_Find);

                   // find the temptrature
                    JSONObject jsonObject1=new JSONObject(response);
                    JSONObject object1=jsonObject. getJSONObject("main");
                    String temp_find=object1.getString("temp");

                    tempreture.setText(temp_find+"°C");

                        //get image icon
                    JSONArray array=jsonObject.getJSONArray("weather");
                    JSONObject jsonObject2=array.getJSONObject(0);
                    String img=jsonObject2.getString("icon");
                    Picasso.get().load("https://openweather.org/img/wn/"+imageButton+"@2x.png").into(imageButton);

                    //find the Date and Time

                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy \nHH:mm:SS");
                    String date=simpleDateFormat.format(calendar.getTime());
                    time.setText(date);


                    //find the latitude
                 JSONObject  object2=jsonObject1.getJSONObject("coord");
                 double let_find=object2.getDouble("lat");
                    latitude.setText(let_find+"° N");

                    JSONObject  object3=jsonObject1.getJSONObject("coord");
                    double long_find=object3.getDouble("lon");
                    textView9.setText(long_find+"° E");

                    JSONObject  object4=jsonObject1.getJSONObject("main");
                    double huma=object4.getDouble("humidity");
                    humadity.setText(huma+" %");

                    JSONObject  object5=jsonObject1.getJSONObject("sys");
                    double sun_rise_find=object5.getDouble("sunrise");
                    sun.setText(sun_rise_find+" ");

                    JSONObject  object6=jsonObject1.getJSONObject("sys");
                    double sunset_find=object6.getDouble("sunset");
                    sunsett.setText(sunset_find+" ");

                    JSONObject object7= jsonObject1.getJSONObject("main");
                    double pressure_find=object7.getDouble("pressure");
                    textView14.setText(pressure_find+" hPa");

                 


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
    }
