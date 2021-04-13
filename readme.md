## 跳绳火柴人

<img src="./files/people.gif" width="300">

## 使用方法
```kotlin

 val d = PathDrawable(this)
        view.setBackgroundDrawable(d)
        val l: View.OnClickListener = View.OnClickListener { d.startAnimating() }
        view.setOnClickListener(l)

```

        
