package s12103.pjatk.pl.web_service_tst;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

public class LessonDayActivity extends AppCompatActivity {

    TextView tv_date, tv_info;
    ArrayList<Lesson> lessonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_day);

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_info = (TextView) findViewById(R.id.tv_info);
        lessonList = (ArrayList<Lesson>) getIntent().getSerializableExtra("SCHEDULE");

        try {
            tv_date.setText(lessonList.get(0).getDate().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_info.setText(lessonList.toString());
    }
}
