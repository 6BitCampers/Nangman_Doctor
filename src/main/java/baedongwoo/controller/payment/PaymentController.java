package baedongwoo.controller.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @GetMapping("/payment/success")
    public String paySuccess(
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam String method
    ){
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("paymentKey", paymentKey);
        map.put("method", method);

        return "redirect:/";
    }
}
