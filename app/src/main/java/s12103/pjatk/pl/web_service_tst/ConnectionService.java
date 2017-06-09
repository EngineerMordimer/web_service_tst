package s12103.pjatk.pl.web_service_tst;

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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by maciek on 09/06/17.
 */
public abstract class ConnectionService {

	public static HttpResponse getResponseFromUrl(String URL, String username, String password)
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

	public static HttpResponse getStudentPersonalDataSimple(String username, String password)
			throws IOException {
		String URL = "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/GetStudentPersonalDataSimpleJson";
		return getResponseFromUrl(URL, username, password);
	}

	public static HttpResponse getStudentSchedule(String username, String password, String beginDate,
			String endDate) throws IOException {
		String baseURL = "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/GetStudentScheduleJson?";
		String URL = baseURL + "begin=" + beginDate + "&end=" + endDate;
		return getResponseFromUrl(URL, username, password);
	}

	public static Person getPersonFromJson(JSONObject jsonObject) throws JSONException {
		return new Person(jsonObject);
	}

	public static ArrayList<Lesson> getScheduleFromJson(JSONArray jsonArray)
			throws JSONException, ParseException {
		ArrayList<Lesson> lessonList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject lessonJSON = jsonArray.getJSONObject(i);
			Lesson lesson = new Lesson(lessonJSON);
			lessonList.add(lesson);
		}
		return lessonList;
	}

	public static int getTesterStatusCode(String username, String password) throws IOException {
		String URL = "https://ws.pjwstk.edu.pl/test/Service.svc/XMLService/tester";
		HttpResponse response = getResponseFromUrl(URL, username, password);
		return response.getStatusLine().getStatusCode();
	}

	public static Person getPersonData(String username, String password)
			throws IOException, JSONException {
		HttpResponse response = getStudentPersonalDataSimple(username, password);
		JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
		return getPersonFromJson(jsonObject);
	}

	public static ArrayList<Lesson> getScheduleData(String username, String password, String beginDate,
			String endDate) throws JSONException, ParseException, IOException {
		HttpResponse response = getStudentSchedule(username, password, beginDate, endDate);
		JSONArray jsonArray = new JSONArray(EntityUtils.toString(response.getEntity()));
		return getScheduleFromJson(jsonArray);
	}
}
