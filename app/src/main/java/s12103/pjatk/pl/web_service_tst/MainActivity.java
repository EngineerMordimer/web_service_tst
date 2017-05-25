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
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


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
//		AsyncCallLoadData task = new AsyncCallLoadData();
//		task.execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncCallLoadData task = new AsyncCallLoadData();
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
//            calculate();
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
        String username = "";
        String password = "";

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
                    .base64.Base64.encode((username + ":" + password).getBytes())));

            headerList.add(new HeaderProperty("Authorization", "Negotiate TlRMTVNTUAADAAAAGAAYAHQAAABYAVgBjAAAAAAAAABYAAAADAAMAFgAAAAQABAAZAAAABAAEADkAQAAFYKI4goAWikAAAAPTnN5FRVpPjftpetd3/ve8HMAMQAyADEAMAAzAFcATgBGADAAMAAwADEANAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADBP5cX7OX3+VoGnzVkppuGAQEAAAAAAABQBanrF8zSAXxptU7WmJiKAAAAAAIADABQAEoAVwBTAFQASwABAA4ARABCAC0AQQBQAFAAWgAEABoAcABqAHcAcwB0AGsALgBlAGQAdQAuAHAAbAADACoAZABiAC0AYQBwAHAAegAuAHAAagB3AHMAdABrAC4AZQBkAHUALgBwAGwABQAaAHAAagB3AHMAdABrAC4AZQBkAHUALgBwAGwABwAIAFAFqesXzNIBBgAEAAIAAAAIADAAMAAAAAAAAAABAAAAACAAAGBG+fmPZtglJaS4zoPe7YtWxT9GKBc1tQyxrspbQRhmCgAQAB4ahdko+4jlqXxQV3qApxkJADQASABUAFQAUAAvAGQAYgAtAGEAcABwAHoALgBwAGoAdwBzAHQAawAuAGUAZAB1AC4AcABsAAAAAAAAAAAAAAAAABSkl1rzauwhoFvwQTDmGcE="));
            headerList.add(new HeaderProperty("username", username));
//			headerList.add(new HeaderProperty("password", password));

            transport.debug = true;

//			Log.e(TAG, transport.requestDump);
//			Log.e(TAG, transport.responseDump);
            transport.call(SOAP_ACTION, soapEnvelope, headerList);

            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, "Result Celsius: " + resultString);
        } catch (Throwable ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    public void testParser() throws SoapFault {
        String tmpMessage = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <s:Body>\n" +
                "        <GetStudentScheduleJsonResponse xmlns=\"http://tempuri.org/\">\n" +
                "            <GetStudentScheduleJsonResult xmlns:a=\"http://schemas.datacontract.org/2004/07/WServices\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "                <a:Zajecia>\n" +
                "                    <a:Budynek>A</a:Budynek>\n" +
                "                    <a:Data_roz>2017-05-13 08:30</a:Data_roz>\n" +
                "                    <a:Data_zak>2017-05-13 10:00</a:Data_zak>\n" +
                "                    <a:Kod>ICK</a:Kod>\n" +
                "                    <a:Nazwa>Interakcja człowiek-komputer</a:Nazwa>\n" +
                "                    <a:Nazwa_sali>118</a:Nazwa_sali>\n" +
                "                    <a:TypZajec>Ćwiczenia</a:TypZajec>\n" +
                "                    <a:idRealizacja_zajec>86190268</a:idRealizacja_zajec>\n" +
                "                </a:Zajecia>\n" +
                "                <a:Zajecia>\n" +
                "                    <a:Budynek>A</a:Budynek>\n" +
                "                    <a:Data_roz>2017-05-14 17:45</a:Data_roz>\n" +
                "                    <a:Data_zak>2017-05-14 19:15</a:Data_zak>\n" +
                "                    <a:Kod>PRO2h</a:Kod>\n" +
                "                    <a:Nazwa>Projekt 2 - Sieci urządzeń mobilnych</a:Nazwa>\n" +
                "                    <a:Nazwa_sali>133</a:Nazwa_sali>\n" +
                "                    <a:TypZajec>Ćwiczenia</a:TypZajec>\n" +
                "                    <a:idRealizacja_zajec>86193726</a:idRealizacja_zajec>\n" +
                "                </a:Zajecia>\n" +
                "            </GetStudentScheduleJsonResult>\n" +
                "        </GetStudentScheduleJsonResponse>\n" +
                "    </s:Body>\n" +
                "</s:Envelope>";

        SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        env.dotNet = true;

// Set your string as output
        env.setOutputSoapObject(tmpMessage);

// Get response
        SoapObject so = (SoapObject) env.getResponse();
        SoapParser soapParser = new SoapParser();
        try {
            Vector<Lesson> lessonVector = soapParser.readScheduleSoapResponse(so);
            Log.e("TESTOWANie",lessonVector.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
