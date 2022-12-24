## ExpandableTextView

仿微信朋友圈全文、收起的 TextView，可在 RecyclerView 和 ListView 中使用，不会有错乱和空白问题。  
欢迎各位 star 和提 issues!    

![动画](screenshot/anim.gif)

## 使用方法  
### 1、引入
在你项目 module 的 build.gradle 中加入  

	implementation 'com.github.Blue-Island-X:ExpandableTextView:v1.0.1'  

### 2、使用
在布局中按需声明如下：
```Xml
	<com.zld.expandlayout.ExpandLayout
	    android:id="@+id/expand_layout"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:layout_marginBottom="8dp"
	    android:layout_marginTop="8dp"
	    app:collapseText="@string/foldHint"
	    app:contentTextColor="@color/text_color_normal"
	    app:contentTextSize="18sp"
	    app:ellipsizeText="..."
	    app:expandCollapseTextColor="#39a4d2"
	    app:expandCollapseTextGravity="right"
	    app:expandCollapseTextSize="16sp"
	    app:expandText="@string/expandHint"
	    app:maxCollapsedLines="3"
	    app:middlePadding="5dp" />
```
#### 1、普通使用
```Kotlin
binding.expandLayout.setContent(text)
```

#### 2、RecyclerView 中使用
在 onBindViewHolder 方法中设置 ExpandLayout 的文字内容、展开状态和及展开监听方法中将实体类的布尔型字段取反
```Kotlin
holder.expandLayout.setContent(item.text, item.expand, object : ExpandLayoutListener {
    override fun onToggleStatus() {
        item.expand = !item.expand
    }
})
```

### 3、XML中配置的属性说明

| xml中的属性                   | 含义                                    |
|---------------------------|---------------------------------------|
| maxCollapsedLines         | 收起时内容最多显示的行数，默认是3行                    |
| contentTextSize           | 内容字体大小，默认18sp                         |
| contentTextColor          | 内容字体颜色                                |
| expandText                | 扩展时显示的文字，默认是“全文”                      |
| collapseText              | 隐藏时显示的文字，默认是“收起”                      |
| expandCollapseTextSize    | 提示文字的大小，默认是18sp                       |
| expandCollapseTextColor   | 提示文字的颜色                               |
| expandCollapseTextGravity | 提示文字的位置，有left,right,center三种值，默认为left |
| ellipsizeText             | 超过最大行数时末尾显示的省略文字，默认是“...”             |
| middlePadding             | 提示文字距离内容文字的高度，默认是0dp                  |

