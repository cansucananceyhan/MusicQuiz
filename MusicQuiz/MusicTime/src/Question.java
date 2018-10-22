
public class Question {

	String type;
	String path;
	int difficulty;
	String question; // 1 to 10
	String option1, option2, option3, option4;
	int answer; // 1 to 4

	public Question(String type, String path, int difficulty, String question, String option1, String option2,
			String option3, String option4, int answer) {
		this.type = type;
		this.path = path;
		this.difficulty = difficulty;
		this.question = question;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.answer = answer;
	}

}
