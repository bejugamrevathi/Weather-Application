package hh.ff.weatherapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class GPSHome extends Activity {
  TextView tv;
  ProgressDialog progress;

  String coord,name,country,setrise,guess,desc,mint,maxt,pressure,sealevel,grndlevel;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpslocation);
        progress = new ProgressDialog(this);

		progress.setMessage("Fetching weather :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        tv=(TextView)findViewById(R.id.gpstext);
        String str = "http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=7c59fc7ce3a9920fe681da32a7859d2f";
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
			  try
		        {
		            

		    
			       rss=new  JSONObject(file_url);
			        JSONObject coord1=rss.getJSONObject("coord");
			        coord="Latitude : "+coord1.getString("lat")+" "+"Longitude : "+coord1.getString("lon");
			        setTitle(coord);
			        JSONObject sys1=rss.getJSONObject("sys");
			        //HH:mm:ss
			        
			        name="\nCity : "+rss.getString("name");
			country="\nCountry : "+sys1.getString("country");
			Date d1 = new Date(sys1.getInt("sunrise")*1000L);
			Date d2 = new Date(sys1.getInt("sunset")*1000L);

			SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("HH:mm:ss");

			setrise="\nSunRise : "+DATE_FORMAT1.format(d1)+"\nSunSet : "+DATE_FORMAT2.format(d2);
			JSONArray weather1=rss.getJSONArray("weather");
			JSONObject iweather1=weather1.getJSONObject(0);
			guess="\nGuess : "+iweather1.getString("main");
			desc="\nDescription : "+iweather1.getString("description");
			JSONObject main1=rss.getJSONObject("main");
			mint="\nMin. Temperature : "+main1.getString("temp_min");
			maxt="\nMax. Temperature : "+main1.getString("temp_max");
			pressure="\nPressure : "+main1.getString("pressure");
			sealevel="\nSea Level : "+main1.getString("sea_level");
			grndlevel="\nGround Level : "+main1.getString("grnd_level");


				
			       		} catch (JSONException e) 
			       		{
			       			// TODO Auto-generated catch block
			       			e.printStackTrace();
			       		}
		        
		        catch(Exception e)
		        {
		        	
		        }
			tv.append(name+country+setrise+guess+desc+mint+maxt+pressure+sealevel+grndlevel);
			
			//tv.setText(title.get(0).toString()+"\n"+title.get(1).toString());
		}
	}
	
    String xml;
    JSONObject rss;
}
