package emph


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

    val options: Vector[String] = baseWord.toCharArray.zipWithIndex.flatMap {
      case (ch, i) =>
        if (isVowel(ch)) Some(createWordWithUpperChar(baseWord, i))
        else None
    }.toVector

    val answers: Vector[String] = baseWord.toCharArray.zipWithIndex.flatMap {
      case (ch, i) =>
        if (isVowel(ch) && ch.isUpper)
          Some(createWordWithUpperChar(baseWord, i))
        else None
    }.toVector
  }

  def main(args: Array[String]): Unit = {
    val questions =
      "агронОмія\nалфАвІт\nАркушик\nасиметрІя\nбагаторазОвий\nбезпринцИпний\nбЕшкет\nблАговіст\nблизькИй\nболотИстий\nборОдавка\nбосОніж\nбоЯзнь\nбурштинОвий\nбюлетЕнь\nвАги (у множині)\nвантажІвка\nвеснЯнИй\nвИгода (користь)\nвигОда (зручність)\nвидАння\nвизвОльний\nвимОга\nвИпадок\nвирАзний\nвИсіти\nвИтрата\nвишИваний\nвідвезтИ\nвідвестИ\nвІдгомін\nвіднестИ\nвІдомість (список)\nвідОмість (повідомлення, дані, популярність)\nвІрші\nвіршовИй\nвітчИм\nгальмО\nгАльма\nглядАч\nгорошИна\nграблІ\nгуртОжиток\nданИна\nдАно\nдецимЕтр\nдЕщиця\nде-Юре\nджерелО\nдИвлячись\nдичАвіти\nдіалОг\nдобовИй\nдобУток\nдовезтИ\nдовестИ\nдовІдник\nдОгмат\nдонестИ\nдОнька\nдочкА\nдрОва\nекспЕрт\nєретИк\nжалюзІ\nзавдАння\nзавезтИ\nзавестИ\nзАвжди\nзавчасУ\nзАгадка\nзаіржАвілий\nзаіржАвіти\nзакінчИти\nзАкладка (у книзі)\nзАкрутка\nзалишИти\nзамІжня\nзанестИ\nзАпонка\nзаробІток\nзАставка\nзАстібка\nзастОпорити\nзвИсока\nздАлека\nзібрАння\nзобразИти\nзОзла\nзрАння\nзрУчний\nзубОжіння\nіндУстрія\nкАмбала\nкаталОг\nквартАл\nкИшка\nкіломЕтр\nкінчИти\nкОлесо\nкОлія\nкОпчений (дієприкметник)\nкопчЕний (прикметник)\nкорИсний\nкОсий\nкотрИй\nкрицЕвий\nкрОїти\nкропивА\nкулінАрія\nкУрятина\nлАте\nлистопАд\nлітОпис\nлЮстро\nмАбУть\nмагістЕрський\nмАркетинг\nмерЕжа\nметалУргія\nмілімЕтр\nнавчАння\nнанестИ\nнапІй\nнАскрізний\nнАчинка\nненАвидіти\nненАвисний\nненАвисть\nнестИ\nнІздря\nновИй\nобіцЯнка\nобрАння\nобрУч (іменник)\nодинАдцять\nодноразОвий\nознАка\nОлень\nоптОвий\nосетЕр\nотАман\nОцет\nпавИч\nпартЕр\nпЕкарський\nперевезтИ\nперевестИ\nперЕкис\nперелЯк\nперенестИ\nперЕпад\nперЕпис\nпіалА\nпІдданий (дієприкметник)\nпіддАний (іменник, істота)\nпІдлітковий\nпізнАння\nпітнИй\nпіцЕрія\nпОдруга\nпОзначка\nпОмилка\nпомІщик\nпомОвчати\nпонЯття\nпорядкОвий\nпосерЕдині\nпривезтИ\nпривестИ\nпрИморозок\nпринестИ\nпрИчіп\nпрОділ\nпромІжок\nпсевдонІм\nрАзом\nрЕмінь (пояс)\nрЕшето\nрИнковий\nрівнИна\nроздрібнИй\nрОзпірка\nрукОпис\nруслО\nсантимЕтр\nсвЕрдло\nсерЕдина\nсЕча\nсиметрІя\nсільськогосподАрський\nсімдесЯт\nслИна\nсоломИнка\nстАтуя\nстовідсОтковий\nстрибАти\nтекстовИй\nтечіЯ\nтИгровий\nтисОвий\nтім’янИй\nтравестІя\nтризУб\nтУлуб\nукраЇнський\nуподОбання\nурочИстий\nусерЕдині\nфартУх\nфаховИй\nфенОмен\nфОльга\nфОрзац\nхАос (у міфології: стихія)\nхаОс (безлад)\nцАрина\nцемЕнт\nцЕнтнер\nціннИк\nчарівнИй\nчерговИй\nчитАння\nчорнОзем\nчорнОслив\nчотирнАдцять\nшляхопровІд\nшовкОвий\nшофЕр\nщЕлепа\nщИпці\nщодобовИй\nярмаркОвий"
        .split("\n")
        .map(Word)
        .map(word =>
          Question(
            question = word.wholeSentenceLowercase,
            options = word.options.toSet.toVector,
            answers = word.answers
          )
        )
        .toVector

    new QuizManager(questions,
      Limited(limit = 10)
    ).start()
  }

}
