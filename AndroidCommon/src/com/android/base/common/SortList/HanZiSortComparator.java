package com.android.base.common.SortList;

import android.text.TextUtils;

import com.android.base.common.SortList.HanZiSrot.HanziToPinyin;

import java.util.Comparator;

/**
 * 汉字排序排序
 *
 * @author ----zhaoruyang----
 * @data: 2015/6/2
 */
public class HanZiSortComparator implements Comparator<String> {
    private static final String TAG = "HanZiSortComparator";
    private int sankCode;

    /**
     * @param asc
     *         是否正序
     */
    public HanZiSortComparator(boolean asc) {
        sankCode = asc ? -1 : 1;
    }


    @Override
    public int compare(String lhs, String rhs) {
        // 比较的内容为null
        if (lhs == null || rhs == null) {
            return 0;
        }

        if (lhs.length() == 0 || rhs.length() == 0) {
            if (lhs.length() == 0) {
                return -sankCode;
            } else if (rhs.length() == 0) {
                return sankCode;
            } else {
                return 0;
            }
        }

        int lhsCnt = lhs.toLowerCase().charAt(0);
        int rhsCnt = rhs.toLowerCase().charAt(0);

        if (lhsCnt < 256 && rhsCnt >= 256) {
            return -sankCode;
        } else if (lhsCnt >= 256 && rhsCnt < 256) {
            return sankCode;
        }

        HanziToPinyin hanziToPinyin = HanziToPinyin.getInstance();

        if (TextUtils.isEmpty(lhs) && TextUtils.isEmpty(rhs)) {
            return sankCode * hanziToPinyin.get(lhs)
                    .compareTo(hanziToPinyin.get(rhs));
        } else {
            return 0;
        }
    }
}
