package com.android.base.common.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ----zhaoruyang----
 */
public class EmojiEditWatcher implements TextWatcher {
	//匹配非表情符号的正则表达式
	private final String reg = "^([a-z]|[A-Z]|[0-9]|[\u2E80-\u9FFF]){3,}|@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?|[wap.]{4}|[www.]{4}|[blog.]{5}|[bbs.]{4}|[.com]{4}|[.cn]{3}|[.net]{4}|[.org]{4}|[http://]{7}|[ftp://]{6}$";

	private Pattern pattern = Pattern.compile(reg);
	//输入表情前的光标位置
	private int      cursorPos;
	//输入表情前EditText中的文本
	private String   tmp;
	//是否重置了EditText的内容
	private boolean  resetText;
	private EditText contentEd;
	private Context  context;

	public EmojiEditWatcher(EditText contentEd, Context context) {
		this.contentEd = contentEd;
		this.context = context;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!resetText) {
			if (count >= 2) {//表情符号的字符长度最小为2
				//提取输入的长度大于2的文本
				int length = s.length();
				if (cursorPos + count > length) {
					return;
				}
				CharSequence input = s.subSequence(cursorPos, cursorPos + count);
				//正则匹配是否是表情符号
				Matcher matcher = pattern.matcher(input.toString());
				if (EmojiFilter.containsEmoji(input)) {
					resetText = true;
					//是表情符号就将文本还原为输入表情符号之前的内容
					contentEd.setText(tmp);
					contentEd.setSelection(tmp.length());
					contentEd.invalidate();
					Toast.makeText(context,
					               "不支持表情输入", Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			resetText = false;
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		if (!resetText) {
			cursorPos = contentEd.getSelectionEnd();
			tmp = s.toString();//这里用s.toString()而不直接用s是因为如果用s，那么，tmp和s在内存中指向的是同一个地址，s改变了，tmp也就改变了，那么表情过滤就失败了
		}

	}

}
