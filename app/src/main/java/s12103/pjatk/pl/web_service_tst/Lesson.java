package s12103.pjatk.pl.web_service_tst;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maciej.chmielinski on 13.05.2017.
 */

public class Lesson implements Serializable {

	private static final long serialVersionUID = 2766184602676920851L;

	private String building;
	private Date beginDate;
	private Date endDate;
	private String code;
	private String name;
	private String classroom;
	private String type;
	private long id;

	public Lesson(String building, Date beginDate, Date endDate, String code, String name,
			String classroom, String type, long id) {
		this.building = building;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.code = code;
		this.name = name;
		this.classroom = classroom;
		this.type = type;
		this.id = id;
	}

	public Lesson(JSONObject jsonObject) throws JSONException, ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		this.building = jsonObject.getString("Budynek");
		this.beginDate = dateFormat.parse(jsonObject.getString("Data_roz"));
		this.endDate = dateFormat.parse(jsonObject.getString("Data_zak"));
		this.code = jsonObject.getString("Kod");
		this.name = jsonObject.getString("Nazwa");
		this.classroom = jsonObject.getString("Nazwa_sali");
		this.type = jsonObject.getString("TypZajec");
		this.id = jsonObject.getInt("idRealizacja_zajec");
	}

	public String getBuilding() {
		return building;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getClassroom() {
		return classroom;
	}

	public String getType() {
		return type;
	}

	public long getId() {
		return id;
	}

	public Date getDate() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.parse(dateFormat.format(this.beginDate));
	}

//	public Date getBeginTime() throws ParseException {
//		DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//		return dateFormat.parse(dateFormat.format(this.beginDate));
//	}
//
//	public Date getEndTime() throws ParseException {
//		DateFormat dateFormat = new SimpleDateFormat("hh:mm");
//		return dateFormat.parse(dateFormat.format(this.endDate));
//	}

	@Override
	public String toString() {
		return "Lesson{" + "building='" + building + '\'' + ", beginDate=" + beginDate
				+ ", endDate=" + endDate + ", code='" + code + '\'' + ", name='" + name + '\''
				+ ", classroom='" + classroom + '\'' + ", type='" + type + '\'' + ", id=" + id
				+ '}';
	}
}
