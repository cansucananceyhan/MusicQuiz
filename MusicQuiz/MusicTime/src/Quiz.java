import java.util.ArrayList;

public class Quiz {

	ArrayList<Question> questions;
	String type;

	public Quiz(ArrayList<Question> questions, String type) {
		this.questions = questions;
		this.type = type;
	}
}
