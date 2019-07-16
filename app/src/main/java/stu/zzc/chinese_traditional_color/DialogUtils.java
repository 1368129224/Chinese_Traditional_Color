package stu.zzc.chinese_traditional_color;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import es.dmoral.toasty.Toasty;

public class DialogUtils {

    public static void showDonateDialog(Activity activity) {
        final MaterialDialog dialog = new MaterialDialog(activity);
        dialog.content("请我喝一瓶可乐？").btnText("还是算了", "要加冰的").show();
        dialog.setOnBtnClickL(
                dialog::dismiss,
                () -> {
                    if (RomUtils.checkApkExist(activity, "com.eg.android.AlipayGphone")) {
                        donate(activity);
                    } else {
                        Toasty.warning(activity, "本机未安装支付宝", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
        );
    }

    //跳转到支付宝付款界面
    private static void donate(Context context) {
        String intentFullUrl = "intent://platformapi/startapp?saId=10000007&" +
                "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2Ftsx00754zej1i0vqriwc550%3F_s" +  
                "%3Dweb-other&_t=1472443966571#Intent;" +
                "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
        try {
            Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
