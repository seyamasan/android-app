package com.example.seyamaapplication.main.validate

/**
 * 文字検証クラス
 */
class ValidateString() {
    /**
     * 記号が含まれているか確認します。
     *
     * @return 含まれていればtrueを返します。
     */
    fun validateSymbol(str: String): Boolean {
        val regex = Regex("[^\\p{L}\\p{N}\\s\\p{InHiragana}\\p{InKatakana}\\p{InCJKUnifiedIdeographs}]+")

        // 正規表現にマッチする文字列が含まれているかどうかを判定
        return regex.containsMatchIn(str)
    }
}