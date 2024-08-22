package com.example.cleartalkrpg.resultscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.w3c.dom.Comment

@Composable
fun ResultScreen() {
    Row {
        TotalScoreBoard(totalScore = 88.8, backgroundColor = Color.Cyan)
        CommentBoard(comment = "もう少しゆっくり一言一言大切に話してみましょう！")
    }
    Row {
        PartialScoreBoard(typeName = "音量", score = 28.1, maxScore = 30, backgroundColor = Color.Red)
        PartialScoreBoard(typeName = "明瞭さ", score = 38.2, maxScore = 40, backgroundColor = Color.Blue)
        PartialScoreBoard(typeName = "速さ", score = 22.5, maxScore = 30, backgroundColor = Color.Green)
    }
    Row {
        BackToOtherScreenButton(displayName = "タイトル画面へ")
        BackToOtherScreenButton(displayName = "シナリオ選択へ")
        BackToOtherScreenButton(displayName = "履歴確認画面へ")
    }
}

@Composable
fun TotalScoreBoard(totalScore: Double,backgroundColor: Color) {

}

@Composable
fun CommentBoard(comment: String) {

}

@Composable
fun PartialScoreBoard(typeName: String, score: Double, maxScore: Int, backgroundColor: Color) {

}

@Composable
fun BackToOtherScreenButton(displayName: String) {

}
