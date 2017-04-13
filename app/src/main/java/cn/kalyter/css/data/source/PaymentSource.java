package cn.kalyter.css.data.source;

import java.util.List;

import cn.kalyter.css.model.Payment;
import cn.kalyter.css.model.Response;
import rx.Observable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public interface PaymentSource {
    Observable<Response<List<Payment>>> getPayments();

    Observable<Response<List<Payment>>> getMyPaymentRecords();
}
