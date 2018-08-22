package hh.ff.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {
Button myforecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        myforecast=(Button)findViewById(R.id.forecast);
        myforecast.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        startActivity(new Intent(Home.this,ForecastHome.class));	
	
			}
		});
    }
    public void mygps(View v)
    {
    startActivity(new Intent(Home.this,GPSHome.class));	
    }
    public void mylocation(View v)
    {
        startActivity(new Intent(Home.this,LocationHome.class));	

    }
 
}
