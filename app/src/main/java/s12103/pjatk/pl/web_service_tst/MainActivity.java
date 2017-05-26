package s12103.pjatk.pl.web_service_tst;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	final Context context = this;
	TextView textView;
	Button bt_student_data, bt_test, bt_show, bt_go_calendar;

	String username = "";
	String password = "";

	Person person;
	ArrayList<Lesson> lessonList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView);
		bt_student_data = (Button) findViewById(R.id.bt_student_data);
		bt_test = (Button) findViewById(R.id.bt_test);
		bt_show = (Button) findViewById(R.id.bt_show);
		bt_go_calendar = (Button) findViewById(R.id.bt_goCalendar);

		textView.setText("START");
		bt_student_data.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AsyncCallLoadData task = new AsyncCallLoadData();
				task.execute();
			}
		});

		bt_test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AsyncCallTest task = new AsyncCallTest();
				task.execute();
				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
				alertBuilder.setMessage("TEST - OK").setCancelable(true);
				AlertDialog alertDialog = alertBuilder.create();
				alertDialog.show();
			}
		});

		bt_show.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				textView.setText(person.toString());
			}
		});

		bt_go_calendar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, CalendarViewActivity.class);
				intent.putExtra("SCHEDULE", lessonList);
				startActivity(intent);
			}
		});
	}

	public void test_connect() throws Exception {
		HttpResponse response;

		response = getStudentSchedule(username, password, "2017-05-26", "2017-05-29");
		JSONArray jsonArray = new JSONArray(EntityUtils.toString(response.getEntity()));
		lessonList = getScheduleFromJson(jsonArray);
		Log.i("TEST", "Status Code: " + response.getStatusLine().getStatusCode());

		response = getStudentPersonalDataSimple(username, password);
		JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
		person = getPersonFromJson(jsonObject);
		Log.i("TEST", "Status Code: " + response.getStatusLine().getStatusCode());
	}

	private class AsyncCallLoadData extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Log.i("LOAD_DATA", "doInBackground");
			try {
				HttpResponse response = getStudentPersonalDataSimple(username, password);
				JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
				person = getPersonFromJson(jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	private class AsyncCallTest extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				test_connect();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return null;
		}
	}

	public HttpResponse getResponseFromUrl(String URL, String username, String password)
			throws IOException {
		CredentialsProvider provider = new BasicCredentialsProvider();
		NTCredentials ntCredentials = new NTCredentials(username, password, "", "");
		provider.setCredentials(AuthScope.ANY, ntCredentials);
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider)
				.build();
		HttpResponse response = client.execute(new HttpGet(URL));
		return response;
	}

	public HttpResponse getStudentPersonalDataSimple(String username, String password)
			throws IOException {
		String URL = "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/GetStudentPersonalDataSimpleJson";
		return getResponseFromUrl(URL, username, password);
	}

	public HttpResponse getStudentSchedule(String username, String password, String beginDate,
			String endDate) throws IOException {
		String baseURL = "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/GetStudentScheduleJson?";
		String URL = baseURL + "begin=" + beginDate + "&end=" + endDate;
		return getResponseFromUrl(URL, username, password);
	}

	public Person getPersonFromJson(JSONObject jsonObject) throws JSONException {
		return new Person(jsonObject);
	}

	public ArrayList<Lesson> getScheduleFromJson(JSONArray jsonArray)
			throws JSONException, ParseException {
		ArrayList<Lesson> lessonList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject lessonJSON = jsonArray.getJSONObject(i);
			Lesson lesson = new Lesson(lessonJSON);
			lessonList.add(lesson);
		}
		return lessonList;
	}

}
