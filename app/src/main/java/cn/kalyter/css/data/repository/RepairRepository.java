package cn.kalyter.css.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.R;
import cn.kalyter.css.data.source.RepairSource;
import cn.kalyter.css.model.Repair;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.Config;
import cn.kalyter.css.util.RepairTypeEnum;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class RepairRepository implements RepairSource {
    private Context mContext;

    public RepairRepository(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Response<List<Repair>>> getRepair() {
        Response<List<Repair>> response = new Response<>();
        List<Repair> raw = getMockRepairs();
        List<Repair> repairs = new ArrayList<>();
        for (Repair repair : raw) {
            if (repair.getStatus() == 0) {
                repairs.add(repair);
            }
        }
        response.setData(repairs);
        return Observable.just(response);
    }

    @NonNull
    private List<Repair> getMockRepairs() {
        List<Repair> repairs = new ArrayList<>();
        String[] reporters = mContext.getResources().getStringArray(R.array.repair_reporters);
        int[] types = mContext.getResources().getIntArray(R.array.repair_types);
        String[] reporterTels = mContext.getResources().getStringArray(R.array.repair_reporter_tels);
        String[] reportTime = mContext.getResources().getStringArray(R.array.repair_report_time);
        String[] expectHandleTimes = mContext.getResources().getStringArray(R.array.repair_expect_handle_time);
        String[] descriptions = mContext.getResources().getStringArray(R.array.repair_descriptions);
        int[] status = mContext.getResources().getIntArray(R.array.repair_status);
        for (int i = 0; i < reporters.length; i++) {
            Repair repair = new Repair();
            repair.setReporter(reporters[i]);
            repair.setType(types[i]);
            repair.setReporterTel(reporterTels[i].replace("手机", ""));
            try {
                repair.setReportTime(Config.yyyyMMddHHmmss.parse(reportTime[i]));
                repair.setExpectHandleTime(Config.yyyyMMddHHmmss.parse(expectHandleTimes[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            repair.setDescription(descriptions[i]);
            repair.setStatus(status[i]);
            repairs.add(repair);
        }
        return repairs;
    }

    @Override
    public Observable<Response<List<Repair>>> getMyRepairRecords() {
        List<Repair> raw = getMockRepairs();
        List<Repair> repairs = new ArrayList<>();
        for (Repair repair : raw) {
            if (repair.getStatus() == 1) {
                repairs.add(repair);
            }
        }
        Response<List<Repair>> response = new Response<>();
        response.setData(repairs);
        return Observable.just(response);
    }
}
