package com.example.unscrample_app_final.game
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    private val _score = MutableLiveData<Int>(0)
    public val score:LiveData<Int> get() = _score

    private val _currentWordCount = MutableLiveData<Int>(0)
    public val currentWordCount:LiveData<Int> get() = _currentWordCount


    private val _currentScrambledWord = MutableLiveData<String>()
    public val currentScrambledWord:LiveData<String> get() = _currentScrambledWord



    private var wordList = mutableListOf<String>()
    private lateinit var currentWord:String


    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord,false)){
            tempWord.shuffle()
        }
        if(wordList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambledWord.value = String (tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }


    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment","On cleared")

    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }


    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    fun isUserwordCorrect(playerWord:String):Boolean{
        return if(playerWord.equals(currentWord,false)){
            increaseScore()
            true
        }else false
    }

    fun reInit(){
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()

    }

}