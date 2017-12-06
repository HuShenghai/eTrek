package com.bottle.track.ui.dialog

import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView

import com.bottle.track.MyApplication
import com.bottle.track.R
import com.bottle.util.dip2px


/**
 * 简单question对话框
 *
 * @author Shenghai
 */
class SimpleQuestionDialog : DialogFragment(), OnClickListener {

    private var tvTitle: TextView? = null
    private var tipsTxt: TextView? = null
    private var okBtn: TextView? = null
    private var cancelBtn: TextView? = null

    private var title: String? = null
    private var tips: String? = null
    private var okButton: String? = null
    private var cancelButton: String? = null
    private var tipsGravity: Int = 0
    private var listener: IDialogListener? = null

    fun setListener(listener: IDialogListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.isCancelable = false// 设置点击屏幕Dialog不消失
        val argument = arguments
        if (argument != null) {
            this.title = argument.getString(KEY_TITLE)
            this.tips = argument.getString(KEY_TIPS)
            this.okButton = argument.getString(KEY_OK_BUTTON)
            this.cancelButton = argument.getString(KEY_CANCELBUTTON)
            this.tipsGravity = argument.getInt(KEY_TIPS_GRAVITY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))
        val view = inflater.inflate(
                R.layout.dialog_simple_question, container)
        this.tvTitle = view.findViewById<View>(R.id.simple_question_dialog_title) as TextView
        this.tipsTxt = view.findViewById<View>(R.id.simple_question_dialog_info) as TextView
        this.okBtn = view.findViewById<View>(R.id.simple_question_dialog_ok) as TextView
        this.cancelBtn = view.findViewById<View>(R.id.simple_question_dialog_cancel) as TextView
        this.okBtn!!.setOnClickListener(this)
        this.cancelBtn!!.setOnClickListener(this)
        this.tipsTxt!!.text = tips!! + " "
        if (!TextUtils.isEmpty(this.okButton)) {
            this.okBtn!!.text = this.okButton
        }
        if (!TextUtils.isEmpty(this.cancelButton)) {
            this.cancelBtn!!.text = this.cancelButton
        }
        if (!TextUtils.isEmpty(this.title)) {
            this.tvTitle!!.visibility = View.VISIBLE
            this.tvTitle!!.text = title
        } else {
            val padding = dip2px(MyApplication.app, 46f)
            this.tipsTxt!!.setPadding(padding, padding, padding, padding)
            this.tvTitle!!.visibility = View.GONE
        }
        if (this.tipsGravity > 0) {
            setTextLeft(this.tipsGravity)
        }
        return view
    }

    override fun onClick(v: View) {
        if (v.id == R.id.simple_question_dialog_ok) {
            if (this.listener != null) {
                listener!!.onOk()
            }
            this.dismiss()
        } else if (v.id == R.id.simple_question_dialog_cancel) {
            if (this.listener != null) {
                listener!!.onCancle()
            }
            this.dismiss()
        }
    }

    fun setTextLeft(tipsGravity: Int) {
        if (this.tipsTxt != null) {
            if (tipsGravity == GRAVITY_LEFT) {
                this.tipsTxt!!.gravity = Gravity.LEFT
            } else if (tipsGravity == GRAVITY_CENTER) {
                this.tipsTxt!!.gravity = Gravity.CENTER
            }
        }
    }

    companion object {

        val KEY_TITLE = "title"
        val KEY_TIPS = "tips"
        val KEY_OK_BUTTON = "ok"
        val KEY_CANCELBUTTON = "cancel"
        val KEY_TIPS_GRAVITY = "tipsGravity"

        val GRAVITY_LEFT = 1
        val GRAVITY_CENTER = 2

        fun newInstance(tips: String): SimpleQuestionDialog {
            val b = Bundle()
            b.putString(KEY_TIPS, tips)
            val sqd = SimpleQuestionDialog()
            sqd.arguments = b
            return sqd
        }

        fun newInstance(
                okButton: String, cancelButton: String, tips: String): SimpleQuestionDialog {
            val b = Bundle()
            b.putString(KEY_OK_BUTTON, okButton)
            b.putString(KEY_CANCELBUTTON, cancelButton)
            b.putString(KEY_TIPS, tips)
            val sqd = SimpleQuestionDialog()
            sqd.arguments = b
            return sqd
        }

        fun newInstance(title: String,
                        okButton: String, cancelButton: String, tips: String, tipsGravity: Int): SimpleQuestionDialog {
            val b = Bundle()
            b.putString(KEY_TITLE, title)
            b.putString(KEY_OK_BUTTON, okButton)
            b.putString(KEY_CANCELBUTTON, cancelButton)
            b.putString(KEY_TIPS, tips)
            b.putInt(KEY_TIPS_GRAVITY, tipsGravity)
            val sqd = SimpleQuestionDialog()
            sqd.arguments = b
            return sqd
        }
    }
}
