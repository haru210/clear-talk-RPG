package com.example.cleartalkrpg.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleartalkrpg.utils.Gender

@Entity(tableName = "character_sheets")
data class CharacterSheet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    var name: String? = null, // 名前
    var occupation: String? = null, // 職業
    var sprite: Int? = null, // 立ち絵
    var description: String? = null, // 説明
    var gender: Gender? = null, // 性別
    var age: Int? = null, // 年齢
    var hometown: String? = null, // 出身地

    /* STATUS */
    var STR: Int = 0, // 筋力
    var CON: Int = 0, // 体力
    var POW: Int = 0, // 精神力
    var DEX: Int = 0, // 俊敏性
    var APP: Int = 0, // 外見
    var SIZ: Int = 0, // 体格
    var INT: Int = 0, // 知性
    var EDU: Int = 0, // 教育
    var PRO: Int = 0, // 財産

    /* POINT */
    var SAN: Int = 0, // 正気度
    var LUCK: Int = 0, // 幸運
    var IDEA: Int = 0, // アイデア
    var KNOW: Int = 0, // 知識
    var PAT: Int = 0, // 忍耐力
    var MAGP: Int = 0, // マジックポイント
    var OCCP: Int = 0, // 職業技能ポイント
    var HOBP: Int = 0, // 趣味技能ポイント
    var DMGB: Int = 0, // ダメージボーナス
)
