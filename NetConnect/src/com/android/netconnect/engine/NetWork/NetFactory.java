package com.android.netconnect.engine.NetWork;

import android.util.SparseArray;

import com.android.netconnect.NetConstant;
import com.android.netconnect.engine.ConnectMode;
import com.android.netconnect.engine.NetWork.Impl.ClientConnectImpl;
import com.android.netconnect.engine.NetWork.Impl.OKHttpConnectImpl;
import com.android.netconnect.engine.NetWork.Impl.URlConnectImpl;
import com.android.netconnect.http.NetOptions;
import com.android.netconnect.listener.IHttpResult;

/**
 * @author ----zhaoruyang----
 * @data: 2015/4/23
 */
public class NetFactory {

    private static volatile NetFactory instance = null;

    private SparseArray<IRequest> connectModes;

    // private constructor suppresses
    private NetFactory() {
        connectModes = new SparseArray<>();
    }


    public static NetFactory getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (NetFactory.class) {
                if (instance == null) {
                    instance = new NetFactory();
                }
            }
        }

        return instance;
    }


    /*执行连接*/
    public void exeConnect(NetOptions options, IHttpResult result) {
        ConnectMode connectMode = options.getConnectMode();
        IRequest executor = getExecutor(connectMode);
        if (options.getMethod() == RequestMethod.GET) {
            // TODO：　回调直接传入是不是影响了层层传递的规则？？？
            executor.doGet(options.getUrl(), result);
        } else if (options.getMethod() == RequestMethod.POST) {
            executor.doPost(options.getUrl(), options.getParams(), result);
        }

    }


    /*获取执行器*/
    private synchronized IRequest getExecutor(ConnectMode mode) {
        int ordinal = mode.ordinal();
        IRequest iRequest = connectModes.get(ordinal);
        if (iRequest == null) {
            iRequest = createConnectMode(mode);
            connectModes.put(ordinal, iRequest);
        }
        return iRequest;
    }

    /*创造执行器*/
    private IRequest createConnectMode(ConnectMode mode) {
           /*获取HttpClient执行对象*/
        if (mode == ConnectMode.connect_client) {
            return new ClientConnectImpl();
        }
        /*获取URL执行对象*/
        else if (mode == ConnectMode.connect_url) {
            return new URlConnectImpl();
        }

        /*获取URL执行对象*/
        else if (mode == ConnectMode.connect_ok) {
            return new OKHttpConnectImpl();
        }
        return null;
    }
}
