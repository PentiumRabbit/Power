package com.android.netconnect.http;

import com.android.base.ConstantValue;
import com.android.base.utils.Logger;

public class Protocol {

    public enum ProtocolType {

        GET_APK_DETAILS {
            @Override
            public String toString() {
                return "/pdetail.php";
            }
        },

        POLLING_SERVICE {
            @Override
            public String toString() {
                return "/notification.php";
            }
        },

    }


    public static String generateUrl(ProtocolType type) {
        if (Logger.isDebug()) {
            return String.format("%s%s", ConstantValue.NTE_DEBUG_HOST, type.toString());
        } else {
            return String.format("%s%s", ConstantValue.NET_DEFAULT_HOST, type.toString());
        }

    }
}
