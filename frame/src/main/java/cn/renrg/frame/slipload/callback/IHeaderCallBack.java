package cn.renrg.frame.slipload.callback;

/**
 * 提供自定义headerview的接口
 *
 * @author huxq17@163.com
 */
public interface IHeaderCallBack {
    /**
     * 正常状态
     */
    void onStateNormal();

    /**
     * 准备刷新
     */
    void onStateReady();

    /**
     * 正在刷新
     */
    void onStateRefreshing();

    /**
     * 刷新成功
     */
    void onStateSuccess();

    /**
     * 刷新失败
     */
    void onStateFail();

}