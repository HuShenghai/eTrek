package com.bottle.track.base.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.bottle.track.R

/**
 * @Date 2018/1/12 11:04
 * @Author halfbottle
 * @Version 1.0.0
 * @Description 创建各种对话框
 */

/**
 * 一个带标题，问题，确定，取消按钮的对话框，点击边缘会消失
 * @param context
 * @param title
 * @param question
 * @param onOkClickListener
 * @param onCancelClickListener
 * @param canceledOnTouchOutside 默认点击对话框外部消失
 */
fun createSimpleDialog(context:Context,
                       title: String = "",
                       question: String,
                       onOkClickListener: DialogInterface.OnClickListener? = null,
                       onCancelClickListener: DialogInterface.OnClickListener? = null,
                       canceledOnTouchOutside: Boolean = true): AlertDialog{
    val dialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert).setTitle(title)
             .setMessage(question)
             .setPositiveButton(context.getString(R.string.ok), onOkClickListener)
             .setNegativeButton(R.string.cancel, onCancelClickListener).create()
    dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
    return dialog
}

/**
 * 一个没有标题，只有问题，确定，取消按钮的对话框，点击边缘会消失
 * @param context
 * @param question
 * @param onOk
 * @param onCancel
 * @param canceledOnTouchOutside 默认点击对话框外部消失
 */
fun createSimpleDialog(context:Context,
                       question: String,
                       onOk: DialogInterface.OnClickListener?,
                       onCancel: DialogInterface.OnClickListener?,
                       canceledOnTouchOutside: Boolean = true): AlertDialog{
    val dialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
            .setMessage(question)
            .setPositiveButton(context.getString(R.string.ok), onOk)
            .setNegativeButton(R.string.cancel, onCancel).create()
    dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
    return dialog
}

/**
 * 一个没有标题，只有提示和确定按钮的对话框，点击边缘会消失
 * @param context
 * @param question
 * @param onOk
 * @param canceledOnTouchOutside 默认点击对话框外部消失
 */
fun createSimpleDialog(context:Context,
                       question: String,
                       onOk: DialogInterface.OnClickListener?,
                       canceledOnTouchOutside: Boolean = true): AlertDialog{
    val dialog =  AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert)
            .setMessage(question)
            .setPositiveButton(context.getString(R.string.ok), onOk)
            .create()
    dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
    return dialog
}