package com.android.netconnect.http;

import com.android.base.ConstantValue;
import com.android.base.utils.Logger;

public class Protocol {

    public enum ProtocolType {

        GET_CLIENT_SETTINGS {
            @Override
            public String toString() {
                return "/client_settings.php?name=theme";
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
