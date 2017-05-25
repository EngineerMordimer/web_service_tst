package s12103.pjatk.pl.web_service_tst;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	String TAG = "Response";
	TextView textView;
	Button bt_connect, bt_test;
	String username = "";
	String password = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView);
		bt_connect = (Button) findViewById(R.id.bt_connect);
		bt_test = (Button) findViewById(R.id.bt_test);

		textView.setText("JIO");
		bt_connect.setOnClickListener(new View.OnClickListener() {
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
			}
		});
	}

	private class AsyncCallLoadData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");
			try {
				testParser();
			} catch (SoapFault soapFault) {
				soapFault.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			// textView.setText(resultString.toString());
			// Toast.makeText(MainActivity.this, "Response" + resultString.toString(),
			// Toast.LENGTH_LONG).show();

		}
	}

	private class AsyncCallTest extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... voids) {
			try {
				connect();
			} catch (IOException exception) {
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

	public HttpResponse getStudentFaculty(String username, String password) throws IOException {
		String URL = "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/GetStudentFaculty";
		return getResponseFromUrl(URL, username, password);
	}

	public void connect() throws IOException {
		HttpResponse response = getStudentFaculty(username, password);
		Log.i(TAG, "Status Code: " + response.getStatusLine().getStatusCode());
		Log.i(TAG, "Content: " + EntityUtils.toString(response.getEntity()));
	}

	public void testParser() throws SoapFault {
		String tmpMessage = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "    <s:Body>\n"
				+ "        <GetStudentScheduleJsonResponse xmlns=\"http://tempuri.org/\">\n"
				+ "            <GetStudentScheduleJsonResult xmlns:a=\"http://schemas.datacontract.org/2004/07/WServices\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
				+ "                <a:Zajecia>\n" + "                    <a:Budynek>A</a:Budynek>\n"
				+ "                    <a:Data_roz>2017-05-13 08:30</a:Data_roz>\n"
				+ "                    <a:Data_zak>2017-05-13 10:00</a:Data_zak>\n"
				+ "                    <a:Kod>ICK</a:Kod>\n"
				+ "                    <a:Nazwa>Interakcja człowiek-komputer</a:Nazwa>\n"
				+ "                    <a:Nazwa_sali>118</a:Nazwa_sali>\n"
				+ "                    <a:TypZajec>Ćwiczenia</a:TypZajec>\n"
				+ "                    <a:idRealizacja_zajec>86190268</a:idRealizacja_zajec>\n"
				+ "                </a:Zajecia>\n" + "                <a:Zajecia>\n"
				+ "                    <a:Budynek>A</a:Budynek>\n"
				+ "                    <a:Data_roz>2017-05-14 17:45</a:Data_roz>\n"
				+ "                    <a:Data_zak>2017-05-14 19:15</a:Data_zak>\n"
				+ "                    <a:Kod>PRO2h</a:Kod>\n"
				+ "                    <a:Nazwa>Projekt 2 - Sieci urządzeń mobilnych</a:Nazwa>\n"
				+ "                    <a:Nazwa_sali>133</a:Nazwa_sali>\n"
				+ "                    <a:TypZajec>Ćwiczenia</a:TypZajec>\n"
				+ "                    <a:idRealizacja_zajec>86193726</a:idRealizacja_zajec>\n"
				+ "                </a:Zajecia>\n" + "            </GetStudentScheduleJsonResult>\n"
				+ "        </GetStudentScheduleJsonResponse>\n" + "    </s:Body>\n"
				+ "</s:Envelope>";

		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		env.dotNet = true;

		// Set your string as output
		env.setOutputSoapObject(tmpMessage);

		// Get response
		SoapObject so = (SoapObject) env.getResponse();
		SoapParser soapParser = new SoapParser();
		try {
			Vector<Lesson> lessonVector = soapParser.readScheduleSoapResponse(so);
			textView.setText(lessonVector.toString());
			Log.e("TESTOWANie", lessonVector.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
