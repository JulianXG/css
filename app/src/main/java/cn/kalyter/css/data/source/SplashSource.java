package cn.kalyter.css.data.source;

/**
 * Created by Kalyter on 2017-4-8 0008.
 */

public interface SplashSource {
    void setIsFirstRun(boolean isFirstRun);

    boolean getIsFirstRun();

    boolean getIsLogin();

    void setIsLogin(boolean isLogin);

    boolean getIsLocate();

    void setIsLocate(boolean isLocate);

    int getSplashDelay();

    void setSplashDelay(int splashDelay);

    void setIdentityType(int identityType);

    int getIdentityType();
}
