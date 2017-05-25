package s12103.pjatk.pl.web_service_tst;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maciek on 25/05/17.
 */
public class PersonSchedule implements Serializable {

	private static final long serialVersionUID = 5430355971603922651L;

	private Person person;
	private List<Lesson> lessonList;

	public PersonSchedule(Person person, List<Lesson> lessonList) {
		this.person = person;
		this.lessonList = lessonList;
	}

	public Person getPerson() {
		return person;
	}

	public List<Lesson> getLessonList() {
		return lessonList;
	}

	@Override
	public String toString() {
		return "PersonSchedule{" +
				"person=" + person +
				", lessonList=" + lessonList +
				'}';
	}
}
