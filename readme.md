跳绳火柴人
使用方法
val d = PathDrawable(this)
        view.setBackgroundDrawable(d)
        val l: View.OnClickListener = View.OnClickListener { d.startAnimating() }
        view.setOnClickListener(l)
