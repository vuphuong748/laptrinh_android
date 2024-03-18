package com.example.myquizapp

object Constants {
    const val USERNAME: String = "UserName"
    const val TOTAL: String = "Total"
    const val CORRECT: String = "Correct"
    fun getQuestions(): ArrayList<Question>{
        val questionList = ArrayList<Question>()
        val q1 = Question(
            1,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            "Argentina",
            "Australia",
            "Armenia",
            "Austria",
            1
        )
        questionList.add(q1)

        val q2 = Question(
            2,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            "Brunei",
            "Brazil",
            "Bulgaria",
            "Bolivia",
            2
        )
        questionList.add(q2)
        val q3 = Question(
            3,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            "Bhutan",
            "Benin",
            "Belgium",
            "Belize",
            3
        )
        questionList.add(q3)
        return questionList
    }
}