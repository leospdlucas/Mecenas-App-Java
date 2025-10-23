
package app.mecenas.server.payments;
import com.stripe.Stripe; import com.stripe.exception.StripeException; import com.stripe.model.checkout.Session; import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import java.util.Map;
@Service public class StripeService {
  public StripeService(@Value("${stripe.secretKey}") String key){ Stripe.apiKey = key; }
  public String donationCheckout(String success, String cancel, String name, long amount, Map<String,String> md) throws StripeException {
    var params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
    .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
    .setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("brl").setUnitAmount(amount)
    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(name).build()).build()).build())
    .setSuccessUrl(success).setCancelUrl(cancel).putAllMetadata(md).build();
    return Session.create(params).getUrl();
  }
  public String subscriptionCheckout(String success, String cancel, String priceId, Map<String,String> md) throws StripeException {
    var params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.SUBSCRIPTION)
    .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L).setPrice(priceId).build())
    .setSuccessUrl(success).setCancelUrl(cancel).setSubscriptionData(SessionCreateParams.SubscriptionData.builder().putAllMetadata(md).build()).putAllMetadata(md).build();
    return Session.create(params).getUrl();
  }
}
