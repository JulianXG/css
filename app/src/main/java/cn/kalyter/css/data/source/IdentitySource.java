package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Identity;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public interface IdentitySource {
    Observable<List<Identity>> getIdentities();
}
