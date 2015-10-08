package com.example.jsondata;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AttendenceList extends Activity {

	/* Strings */

	String mAuth = "";
	String mdeviceType = "";
	String mdeviceID = "";
	String msummitID = "2";

	/* Async task class */

	AttendenceList_Asyc attendenceList_asyc;

	InputStream is = null;
	StringBuilder sb = null;
	String Json_result = null;

	JSONArray jarray;
	JSONObject jdata;

	ProgressDialog mProgressDialog;

	ArrayList<Attendence_Bean> Attendence_arraylist;
	
	ListView mAttendiListview;
	
	List_Of_Attendence_Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendencelist);

		attendenceList_asyc = new AttendenceList_Asyc();
		attendenceList_asyc.execute();

		Attendence_arraylist = new ArrayList<Attendence_Bean>();
		mAttendiListview=(ListView)findViewById(R.id.attendence_list);
		
		
		mAttendiListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Intent in = new Intent(AttendenceList.this,UserDetails.class);
//				startActivity(in);
			}
		});
		
	}

	class AttendenceList_Asyc extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			mProgressDialog = new ProgressDialog(AttendenceList.this);
			mProgressDialog.setTitle("Loading...");
			mProgressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			HttpPost request = new HttpPost(
					"http://192.168.168.5:8089/VglsWebService.svc/attendenceList");
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");
			JSONStringer getCarInfo;
			try {
				getCarInfo = new JSONStringer()

				.object().key("Auth").value("").key("summitID").value(2)
						.endObject();

				StringEntity entity = new StringEntity(getCarInfo.toString());

				request.setEntity(entity);

				// Send request to WCF service
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(request);

				HttpEntity entitys = response.getEntity();
				is = entitys.getContent();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is), 1000);
				sb = new StringBuilder();
				sb.append(reader.readLine() + "\n");

				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

				is.close();
				Json_result = sb.toString();

			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			Log.e("Response Attendencelist", Json_result);

			try {

				jdata = new JSONObject(Json_result);
				jarray = jdata.getJSONArray("d");

				for (int i = 0; i < jarray.length(); i++) {

					jdata = jarray.getJSONObject(i);

					String Image = jdata.getString("ImageFormat");
					String FirstName = jdata.getString("fname");
					String LastName = jdata.getString("lname");
					String PID = jdata.getString("pid");
					String Status = jdata.getString("status");
					String UserToken = jdata.getString("userTokens");

					Attendence_Bean bean = new Attendence_Bean();
					bean.setImage(Image);
					bean.setFirstName(FirstName);
					bean.setLastName(LastName);
					bean.setPID(PID);
					bean.setStatus(Status);
					bean.setUserToken(UserToken);

					Attendence_arraylist.add(bean);
					
					 Log.e("Image : ", Image);
					 Log.e("FirstName : ", FirstName);
					 Log.e("LastName : ", LastName);
					 Log.e("PID : ", PID);
					 Log.e("Status : ", Status);
					 Log.e("UserToken : ", UserToken);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			adapter = new List_Of_Attendence_Adapter(AttendenceList.this, R.layout.attendence_list_content,
					Attendence_arraylist);
			mAttendiListview.setAdapter(adapter);

			mProgressDialog.dismiss();

		}
	}

}
