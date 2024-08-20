import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.voiceapp.R

class ScenarioSelectActivity : AppCompatActivity() {

    private lateinit var scenarioPreview: ImageView
    private lateinit var scenarioDescription: TextView
    private lateinit var scenarioTime: TextView
    private lateinit var scenarioHighScore: TextView
    private lateinit var scenarioButtonLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scenario_select)

        scenarioPreview = findViewById(R.id.scenarioPreview)
        scenarioDescription = findViewById(R.id.scenarioDescription)
        scenarioTime = findViewById(R.id.scenarioTime)
        scenarioHighScore = findViewById(R.id.scenarioHighScore)
        scenarioButtonLayout = findViewById(R.id.scenarioButtonLayout)

        val scenarios = listOf(
            Scenario("Scenario 1", "Description for Scenario 1", R.drawable.scenario1_image, "5 mins", 1500),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            Scenario("Scenario 2", "Description for Scenario 2", R.drawable.scenario2_image, "7 mins", 2000),
            // 他のシナリオを追加
        )

        createScenarioButtons(scenarios)
    }

    private fun createScenarioButtons(scenarios: List<Scenario>) {
        for ((index, scenario) in scenarios.withIndex()) {
            val button = Button(this).apply {
                text = scenario.title
                textSize = 14f // テキストサイズを小さく
                setPadding(8, 8, 8, 8) // パディングを狭く設定
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    val margin = (index * 10) // 階段状の余白
                    width = 200 // ボタンの幅を設定（ピクセル値）
                    height = 100 // ボタンの高さを設定（ピクセル値）
                    setMargins(margin, margin, margin, margin) // ボタン間の余白を設定
                }
                // 中央揃えの配置
                (layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER_HORIZONTAL
                setOnClickListener { showScenarioPreview(scenario) }
            }

            scenarioButtonLayout.addView(button)
        }
    }

    private fun showScenarioPreview(scenario: Scenario) {
        scenarioPreview.setImageResource(scenario.imageRes)
        scenarioPreview.contentDescription = scenario.title
        scenarioDescription.text = scenario.description
        scenarioTime.text = "Time: ${scenario.timeRequired}"
        scenarioHighScore.text = "High Score: ${scenario.highScore}"
    }

    data class Scenario(
        val title: String,
        val description: String,
        val imageRes: Int,
        val timeRequired: String,
        val highScore: Int
    )
}
