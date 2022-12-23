package com.zld.expandabletextview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zld.expandabletextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var list = mutableListOf<String>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 0..10) {
            if (i == 2 || i == 4) {
                list.add("我是短文字，不足三行")
            } else {
                list.add("\t\t\t\t\t医患关系，是影响护士职业幸福感的第一大因素。护理部主任万长秀坦言，护士是医疗服务的终端，病人对所有医疗环节的不满，最终发泄口都是护士。去年《中国护士群体发展现状调查报告》显示：41.2%的护士在近一年内，遭受过患者或家属的过激行为；在各种职业伤害类型中，他们的心理创伤比例最高。" +
                        "去年年底，中国社会福利基金会在了解到许晴的情况后，曾与湖北省中医院“护士心理解压站”取得联系，希望通过心理疏导帮小姑娘走出阴影。然而遗憾的是，许晴一直没有主动联系他们。")
            }
        }

        val adapter = MyAdapter(this, list)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
    }
}
