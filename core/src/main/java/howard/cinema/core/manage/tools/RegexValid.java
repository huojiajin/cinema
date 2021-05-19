package howard.cinema.core.manage.tools;

import howard.cinema.core.manage.common.CommonAbstract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @name: RegexValid
 * @description: 正则校验相关
 * @author: huojiajin
 * @time: 2020/7/14 17:23
 */
public class RegexValid extends CommonAbstract {

    public static final String mobileRegex = "^1[0-9][0-9]\\d{8}$";

    /**
     * @Name validMobile
     * @Author HuoJiaJin
     * @Description 手机号规则校验
     * @Date 2020/7/14 17:26
     * @Param [mobile]
     * @return boolean
     **/
    public static boolean validMobile(String mobile){
        if (!hasText(mobile)) return false;
        if (mobile.length() != 11) return false;
        Pattern p = Pattern.compile(mobileRegex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
