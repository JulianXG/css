package cn.kalyter.css.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.kalyter.css.R;
import cn.kalyter.css.model.Repair;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class RepairRecyclerAdapter extends RecyclerView.Adapter<RepairRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<Repair> mData;

    public RepairRecyclerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_repair, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repair repair = mData.get(position);
        holder.mReporter.setText(String.format("报修人：%s",
                repair.getReporter()));
        holder.mType.setText(String.format("报修类别：%s",
                RepairTypeEnum.parse(repair.getType()).getName()));
        holder.mTel.setText(String.format("报修人手机：%s",
                repair.getReporterTel()));
        holder.mReportTime.setText(String.format("保修时间：%s",
                Config.yyyyMMddHHmmss.format(repair.getReportTime())));
        holder.mExpectTime.setText(String.format("期望时间：%s",
                Config.yyyyMMddHHmmss.format(repair.getExpectHandleTime())));
        holder.mDescription.setText(String.format("情况描述：%s", repair.getDescription()));
        String status = repair.getStatus() == 1 ? "已解决" : "待解决";
        holder.mStatus.setText(status);
    }

    public void setData(List<Repair> data) {
        mData.clear();
        mData.addAll(data);
        notifyItemRangeInserted(0, mData.size());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reporter)
        TextView mReporter;
        @BindView(R.id.type)
        TextView mType;
        @BindView(R.id.tel)
        TextView mTel;
        @BindView(R.id.report_time)
        TextView mReportTime;
        @BindView(R.id.expect_time)
        TextView mExpectTime;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.status)
        TextView mStatus;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
