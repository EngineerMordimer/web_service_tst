package s12103.pjatk.pl.web_service_tst;

import java.util.ArrayList;

import com.sch.calendar.CalendarView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class CalendarViewActivity extends AppCompatActivity {

	ArrayList<Lesson> lessonList;
	CalendarView calendarView;
	// https://github.com/shichaohui/EasyCalendar?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5714

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_view);
		calendarView = (CalendarView) findViewById(R.id.calendarView);

		lessonList = (ArrayList<Lesson>) getIntent().getSerializableExtra("SCHEDULE");

		for (Lesson lesson : lessonList) {
			Log.i("TEST", lesson.toString());
		}

	}
}
