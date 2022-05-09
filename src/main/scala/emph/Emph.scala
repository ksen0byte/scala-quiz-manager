package emph

import scala.io.{Codec, Source, StdIn}

object Emph {
  case class Word(original: String) {

    private val vowels =
      Vector('а', 'о', 'у', 'е', 'и', 'і', 'я', 'є', 'ї', 'ю')
    private def isVowel(ch: Char): Boolean = vowels.contains(ch.toLower)

    def wholeSentenceLowercase: String = original.toLowerCase

    val baseWord: String = original.split(" ")(0)

    private def createWordWithUpperChar(word: String, index: Int) = {
      val newWord = word.toLowerCase.toCharArray
      newWord(index) = newWord(index).toUpper
      new String(newWord)
    }

    val options: Vector[String] = baseWord.toCharArray.zipWithIndex.flatMap { case (ch, i) =>
      if (isVowel(ch)) Some(createWordWithUpperChar(baseWord, i))
      else None
    }.toVector

    val answers: Vector[String] = baseWord.toCharArray.zipWithIndex.flatMap { case (ch, i) =>
      if (isVowel(ch) && ch.isUpper)
        Some(createWordWithUpperChar(baseWord, i))
      else None
    }.toVector
  }

  def resolveQuizMode(args: Array[String]): QuizMode = {
    if (args.length != 4) {
      throw new IllegalArgumentException(s"Expected exactly 4 arguments, but got ${args.length}")
    }
    val (_, offsetArg, limitArg, randomizeArg) = (args(0), args(1), args(2), args(3))
    new QuizMode {
      override def offset: Int        = offsetArg.toInt
      override def limit: Int         = limitArg.toInt
      override def randomize: Boolean = randomizeArg.toBoolean
    }
  }

  def resolveDataSource(args: Array[String]): Seq[String] = {
    val dataSourceFile = args(0)
    val source         = Source.fromFile(dataSourceFile)(Codec.UTF8)
    val lines          = source.getLines.toSeq
    source.close()
    lines
  }

  def showHelp(args: Array[String]): Unit = {
    if (args.length == 0 || args(0).contains("help")) {
      println("Usage  : .\\zno.exe <path\\to\\data.txt> <limit> <offset> <randomize>")
      println("Example: .\\zno.exe .\\app\\data.txt 5 10 true")
      println("Meaning: questions [10-15] in random order")
      val _ = StdIn.readLine()
      System.exit(0)
    }
  }

  def main(args: Array[String]): Unit = {
    showHelp(args)
    val quizMode = resolveQuizMode(args)
    val data     = resolveDataSource(args)

    val questions =
      data
        .map(Word)
        .map(word =>
          Question(
            question = word.wholeSentenceLowercase,
            options = word.options.toSet.toVector,
            answers = word.answers
          )
        )
        .toVector

    new QuizManager(questions, quizMode).start()
  }

}
