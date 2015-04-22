package com.android.netconnect.http;

import com.android.base.ConstantValue;

public class Protocol {

	public static enum ProtocolType {
        XIAOBIAN_RECOMMEND {
            @Override
            public String toString() {
                return "/columnTwo.php";
            }
        },
        GET_APK_DETAILS {
            @Override
            public String toString() {
                return "/pdetail.php";
            }
        },
		CHECKUPDATE {
			@Override
			public String toString() {
				return "/vercheck.php";
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
        if (ConstantValue.IS_DEBUG) {
            return String.format("%s%s", ConstantValue.NTE_DEBUG_HOST, type.toString());
        } else {
            return String.format("%s%s", ConstantValue.NET_DEFAULT_HOST, type.toString());
        }

    }
}
