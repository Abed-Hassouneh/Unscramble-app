package com.example.unscrample_app_final.game
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    val score:LiveData<Int> get() = _score
    val currentWordCount:LiveData<Int> get() = _currentWordCount
    val currentScrambledWord:LiveData<String> get() = _currentScrambledWord
    val maxNoOfWords:LiveData<Int> get() =  _maxNoOfWords

    private val _score = MutableLiveData<Int>()
    private val _currentWordCount = MutableLiveData<Int>()
    private val _currentScrambledWord = MutableLiveData<String>()
    private val _maxNoOfWords = MutableLiveData<Int>()

    private var wordList = mutableListOf<String>()
    private lateinit var currentWord:String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        loadNextWord()
        _score.value = 0
        _currentWordCount.value = 0
        _maxNoOfWords.value = 10
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment","On cleared")
    }

    fun isUserWordCorrect(playerWord:String):Boolean{
        return if(playerWord.equals(currentWord,false)){
            increaseScore()
            true
        }else false
    }

    fun reInit(){
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        loadNextWord()
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            loadNextWord()
            true
        } else false
    }

    private fun loadNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord,false)){
            tempWord.shuffle()
        }
        if(wordList.contains(currentWord)){
            loadNextWord()
        }else{
            _currentScrambledWord.value = String (tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }
}