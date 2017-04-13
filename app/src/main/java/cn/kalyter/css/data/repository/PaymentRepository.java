package cn.kalyter.css.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.kalyter.css.R;
import cn.kalyter.css.data.source.PaymentSource;
import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import cn.kalyter.css.util.Config;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public class PaymentRepository implements PaymentSource {
    private Context mContext;

    public PaymentRepository(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Response<List<Payment>>> getPayments() {
        List<Payment> payments = getMockPayments();
        Response<List<Payment>> response = new Response<>();
        response.setData(payments);
        return Observable.just(response);
    }

    @NonNull
    private List<Payment> getMockPayments() {
        String[] names = mContext.getResources().getStringArray(R.array.payment_names);
        int[] types = mContext.getResources().getIntArray(R.array.payment_types);
        String[] deadlines = mContext.getResources().getStringArray(R.array.payment_deadlines);
        String[] amounts = mContext.getResources().getStringArray(R.array.payment_amounts);
        String[] details= mContext.getResources().getStringArray(R.array.payment_detail);
        String[] remarks = mContext.getResources().getStringArray(R.array.payment_remarks);
        int[] status = mContext.getResources().getIntArray(R.array.payment_status);
        List<Payment> payments = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Payment payment = new Payment();
            payment.setName(names[i]);
            payment.setType(types[i]);
            payment.setStatus(status[i]);
            try {
                payment.setDeadline(Config.yyyyMMdd.parse(deadlines[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            payment.setAmount(Double.valueOf(amounts[i].replace("元", "")));
            payment.setDetail(details[i]);
            payment.setRemark(remarks[i]);
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public Observable<Response<List<Payment>>> getMyPaymentRecords() {
        List<Payment> raw = getMockPayments();
        List<Payment> payments = new ArrayList<>();
        for (Payment payment : raw) {
            if (payment.getStatus() == 1) {
                payments.add(payment);
            }
        }
        Response<List<Payment>> response = new Response<>();
        response.setData(payments);
        return Observable.just(response);
    }
}
