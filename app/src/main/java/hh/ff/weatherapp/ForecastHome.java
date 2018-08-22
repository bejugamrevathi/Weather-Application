package hh.ff.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ForecastHome extends Activity {
String name,coord,country;
int count;
ProgressDialog progress;

ListView forecastlist;
ArrayList<String> dt,min,max,day,night,eve,morn,pressure,main,description;
	@SuppressLint("SimpleDateFormat") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast);
        progress = new ProgressDialog(this);

		progress.setMessage("Fetching weather :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        forecastlist=(ListView)findViewById(R.id.forecaselist);
        dt=new ArrayList<String>();
        min=new ArrayList<String>();
        max=new ArrayList<String>();
       night=new ArrayList<String>();
       eve=new ArrayList<String>();
        morn=new ArrayList<String>();
        pressure=new ArrayList<String>();
       main=new ArrayList<String>();
        day=new ArrayList<String>();
        description=new ArrayList<String>();
   
       
        String str = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=13.2000&lon=79.1167&cnt=16&appid=7c59fc7ce3a9920fe681da32a7859d2f";
        new MyLoaderTask().execute(str);
		}
	
    class MyLoaderTask extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.show();

		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			
			JSONParser jp=new JSONParser();
			String jaison=jp.getJSONFromUrl(f_url[0]);
			return jaison;
			    
		     
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
		}

		@Override
		protected void onPostExecute(String file_url) {
			progress.dismiss();
			try {
			      
			       rss=new  JSONObject(file_url);
			       JSONObject city=rss.getJSONObject("city");
			       name=city.getString("name");
			       country=city.getString("country");
			       setTitle(name+"  "+country);


			       JSONObject coord1=city.getJSONObject("coord");
			       coord="Latitude : "+coord1.getString("lat")+" "+"Longitude : "+coord1.getString("lon");
			       JSONArray kk=rss.getJSONArray("list");
			       count=rss.getInt("cnt");

			       for(int i=0;i<kk.length();i++)
			       {
			       	JSONObject ilist=kk.getJSONObject(i);
			             Date d = new Date(ilist.getInt("dt")*1000L);
			               SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
			               dt.add(DATE_FORMAT.format(d));
			               JSONObject itemp=ilist.getJSONObject("temp");
			               min.add(itemp.getString("min"));
			               max.add(itemp.getString("max"));
			               night.add(itemp.getString("night"));
			               eve.add(itemp.getString("eve"));
					       Log.d("shashank", itemp.getString("eve") );

			               morn.add(itemp.getString("morn"));
			               day.add(itemp.getString("day"));
			               pressure.add(ilist.getString("pressure"));
			               JSONArray iweather=ilist.getJSONArray("weather");
			             
			               	JSONObject iiweather=iweather.getJSONObject(0);
			               	main.add(iiweather.getString("main"));
			               	description.add(iiweather.getString("description"));
			               
			       }
			       		} catch (JSONException e) 
			       		{
			       			// TODO Auto-generated catch block
			       			e.printStackTrace();
			       		}
		        
		        catch(Exception e)
		        {
		        	
		        }
			 ArrayAdapter<String> dateadapter=new ArrayAdapter<String>(ForecastHome.this,android.R.layout.simple_list_item_1, dt);
				forecastlist.setAdapter(dateadapter);
				forecastlist.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
				Toast.makeText(ForecastHome.this, coord, Toast.LENGTH_LONG).show();
				AlertDialog.Builder aa=new AlertDialog.Builder(ForecastHome.this);
				
				aa.setTitle("Weather details : ");
				aa.setMessage("Minimum Temperature : " +min.get(position).toString()+
						"\nMaximum Temperature : " +max.get(position).toString()+
						"\nMorning Temperature : " +morn.get(position).toString()+
						"\nEvening Temperature : " +eve.get(position).toString()+
						"\nNight Temperature : " +night.get(position).toString()+
						"\nPressure : " +pressure.get(position).toString()+
						"\nGuessing : " +main.get(position).toString()+
						"\nDescription : "+description.get(position).toString());

				aa.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				aa.create();
				aa.show();
					}
				});
		}
	}
	
    String xml;
    JSONObject rss;
    
}
