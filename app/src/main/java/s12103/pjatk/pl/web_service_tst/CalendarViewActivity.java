package s12103.pjatk.pl.web_service_tst;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class CalendarViewActivity extends AppCompatActivity {

	ArrayList<Lesson> lessonList;
	CalendarView calendarView;
	private CaldroidFragment caldroidFragment;

	// https://github.com/shichaohui/EasyCalendar?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5714

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_view);



		lessonList = (ArrayList<Lesson>) getIntent().getSerializableExtra("SCHEDULE");




		CaldroidFragment caldroidFragment = new CaldroidFragment();
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
		args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
		args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY); // Tuesday
		caldroidFragment.setArguments(args);

        ColorDrawable green = new ColorDrawable(Color.GREEN);
        for (Lesson lesson : lessonList) {
            Log.i("TEST", lesson.toString());
            try {
                caldroidFragment.setBackgroundDrawableForDate(green, lesson.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



		CaldroidListener listener = new CaldroidListener() {
			@Override
			public void onSelectDate(Date date, View view) {
				ArrayList<Lesson> lessonsOnDate = new ArrayList<>();
				int counter =0;
				for (Lesson lesson : lessonList){
					try {
						if (lesson.getDate().compareTo(date)==0){
                        	lessonsOnDate.add(lesson);
							counter ++;
                        }
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				Log.i("TEST", lessonsOnDate.toString());
				if (counter > 0){
					Intent intent = new Intent(CalendarViewActivity.this, LessonDayActivity.class);
					intent.putExtra("SCHEDULE", lessonsOnDate);
					startActivity(intent);
				}
			}
		};
		caldroidFragment.setCaldroidListener(listener);
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendarView, caldroidFragment);
		t.commit();
	}
}
