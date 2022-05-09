package emph

import scala.io.StdIn
import scala.util.Random

case class Question(
    question: String,
    options: Vector[String],
    answers: Vector[String]
)

sealed trait QuizMode {
  def offset: Int
  def limit: Int
  def randomize: Boolean
}
case class Limited(offset: Int = 0, limit: Int = Int.MaxValue, randomize: Boolean = false) extends QuizMode
case class LimitedRandom(offset: Int = 0, limit: Int = Int.MaxValue, randomize: Boolean = true) extends QuizMode

class QuizManager(
    questionList: Vector[Question],
    mode: QuizMode
) {

  private val questions: Vector[Question] =
    if (mode.randomize) Random.shuffle(questionList.slice(mode.offset, mode.limit))
    else questionList.slice(mode.offset, mode.limit)

  private var score = 0
  private val total = questions.length

  def start(): Unit = {
    (0 until total).foreach { questionIndex =>
      val currentQuestion = questions(questionIndex)

      askQuestion(questionIndex, currentQuestion)
      val userAnswer = StdIn.readLine()
      checkAnswer(userAnswer.toIntOption.map(_ - 1), currentQuestion)
    }
    displayQuizCompleted()
  }

  private def askQuestion(
      questionIndex: Int,
      currentQuestion: Question
  ): Unit = {
    println(f"""
               |${"=" * 30}
               |Score    : $score%3d/$total%-3d ${score.toDouble / total * 100}%5.2f%%
               |Question : $questionIndex%3d
               |${currentQuestion.question} ?
               |
               |Options:
               |${currentQuestion.options.zipWithIndex
      .map { case (option, index) => s"${index + 1}. $option" }
      .mkString("    ")}
               |""".stripMargin)
  }

  private def displayQuizCompleted(): Unit = {
    println(f"""Quiz completed successfully!
          |Your score is $score%3d/$total%-3d  ${score.toDouble / total * 100}%5.2f%%
          |""".stripMargin)
  }

  private def checkAnswer(
      userAnswer: Option[Int],
      currentQuestion: Question
  ): Unit = {

    val answerOpt = for {
      optionIndex <- userAnswer
      answer <- currentQuestion.options.lift(optionIndex)
    } yield answer

    answerOpt match {
      case Some(answer) if currentQuestion.answers.contains(answer) =>
        score = score + 1
        println(s"""Correct!
             |Your answer was [$answer]""".stripMargin)

      case Some(answer) =>
        println(
          s"""Incorrect!
             |Your answer was [$answer].
             |The correct answer is [${currentQuestion.answers.mkString(
            " or "
          )}]""".stripMargin
        )

      case None =>
        println(s"Could not find option")
    }
  }

}
