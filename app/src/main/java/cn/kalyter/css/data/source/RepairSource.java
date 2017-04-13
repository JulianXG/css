package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Repair;
import cn.kalyter.css.model.Response;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public interface RepairSource {
    Observable<Response<List<Repair>>> getRepair();

    Observable<Response<List<Repair>>> getMyRepairRecords();
}
