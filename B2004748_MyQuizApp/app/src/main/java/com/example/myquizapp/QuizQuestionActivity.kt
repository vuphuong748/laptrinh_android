package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var globalUserName: String? = null
    private var globalCorrect: Int = 0
    private var globalCurrentPosition: Int = 1
    private var globalQuestionsList: ArrayList<Question>? = null
    private var globalSelectedOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        globalUserName = intent.getStringExtra(Constants.USERNAME)
        globalQuestionsList = Constants.getQuestions()
        progressBar = findViewById(R.id.progressBar)

        tvProgress = findViewById(R.id.tvProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivImage = findViewById(R.id.ivImage)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btnSubmit = findViewById(R.id.btnSubmit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        setQuestion()
        defaultOptionsView()
    }

    private fun setQuestion() {
        defaultOptionsView()
        //chi muc
        val question: Question = globalQuestionsList!![globalCurrentPosition - 1]
        progressBar?.progress = globalCurrentPosition
        tvProgress?.text = "$globalCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
        ivImage?.setImageResource(question.image)

        //vi tri hien tai
        if(globalCurrentPosition == globalQuestionsList!!.size) {
            btnSubmit?.text = "DONE"
        }else {
            btnSubmit?.text = "SEND"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>() //ds chua doi tuong textview
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }
        for (option in options) {
            option.setTextColor(Color.parseColor("#808080"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_border
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()

        globalSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#0000FF"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_border
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvOptionOne -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tvOptionTwo -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tvOptionThree -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tvOptionFour -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btnSubmit -> {
                if (globalSelectedOptionPosition == 0) {
                    globalCurrentPosition++
                    when {
                        // Kiem tra cau hoi tiep theo
                        globalCurrentPosition <= globalQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USERNAME, globalUserName)
                            intent.putExtra(Constants.CORRECT,globalCorrect)
                            intent.putExtra(Constants.TOTAL, globalQuestionsList?.size )
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = globalQuestionsList?.get(globalCurrentPosition - 1)
                    // Ktra cau tra loi dung
                    if (question!!.correctAnswer != globalSelectedOptionPosition) {
                        answerView(globalSelectedOptionPosition, R.drawable.wrong_border)
                    }else {
                        globalCorrect++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_border)

                    if (globalCurrentPosition == globalQuestionsList!!.size) {
                        btnSubmit?.text = "DONE"
                    } else {
                        btnSubmit?.text = "GO TO THE NEXT QUESTION"
                    }
                    globalSelectedOptionPosition = 0
                }

            }
        }
    }
    private fun answerView(answer: Int, drawableView: Int) {
        when(answer){
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}