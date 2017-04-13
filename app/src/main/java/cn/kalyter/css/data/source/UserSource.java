package cn.kalyter.css.data.source;

import cn.kalyter.css.model.Community;
import cn.kalyter.css.model.User;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface UserSource {
    void saveUser(User user);

    User getUser();

    Community getCommunity();

    void setCommunity(Community community);
}
