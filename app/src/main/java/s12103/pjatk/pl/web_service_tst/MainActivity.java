package s12103.pjatk.pl.web_service_tst;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

	String TAG = "Response";
	TextView textView;
	Button button;
	SoapPrimitive resultString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView);
		button = (Button) findViewById(R.id.button);

		textView.setText("JIO");
		AsyncCallLoadData task = new AsyncCallLoadData();
		task.execute();
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AsyncCallLoadData task = new AsyncCallLoadData();
				task.execute();
			}
		});
	}

	private class AsyncCallLoadData extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
		}

		@Override
		protected Void doInBackground(Void... params) {
			Log.i(TAG, "doInBackground");
			calculate();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
//			textView.setText(resultString.toString());
//			Toast.makeText(MainActivity.this, "Response" + resultString.toString(), Toast.LENGTH_LONG).show();

		}
	}

	public void calculate() {
//		String SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit";
//		String METHOD_NAME = "CelsiusToFahrenheit";
//		String NAMESPACE = "http://www.w3schools.com/webservices/";
//		String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";

		String SOAP_ACTION = "http://tempuri.org/IService/GetStudentFaculty";
		String METHOD_NAME = "GetStudentFaculty";
		String NAMESPACE = "http://tempuri.org/";
		String URL = "https://ws.pjwstk.edu.pl/test/Service.svc?wsdl";
		String username = "xxxx";
		String password = "zzzz";

		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//			request.addProperty("username", username);
//			request.addProperty("password", password);

			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(request);

			HttpTransportSE transport = new HttpTransportSE(URL);

			List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
			headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects
					.base64.Base64.encode((username+":"+password).getBytes())));


			transport.call(SOAP_ACTION, soapEnvelope, headerList);
			resultString = (SoapPrimitive) soapEnvelope.getResponse();

			Log.i(TAG, "Result Celsius: " + resultString);
		} catch (Exception ex) {
			Log.e(TAG, "Error: " + ex.getMessage());
		}
	}
}
