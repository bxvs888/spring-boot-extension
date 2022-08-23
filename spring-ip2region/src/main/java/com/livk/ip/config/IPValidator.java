package com.livk.ip.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

/**
 * <p>
 * IPValidator
 * </p>
 *
 * @author livk
 * @date 2022/8/22
 */
public class IPValidator implements ConstraintValidator<IP, String> {

    private boolean dns;

    @Override
    public void initialize(IP constraintAnnotation) {
        dns = constraintAnnotation.dns();
    }

    @Override
    public boolean isValid(String ipStr, ConstraintValidatorContext context) {
        if (dns && (ipStr.contains(IP.Constant.HTTPS) || ipStr.contains(IP.Constant.HTTP))) {
            return true;
        }
        if (StringUtils.hasText(ipStr)) {
            if (ipStr.length() >= 7 && ipStr.length() <= 15) {
                String[] ipArray = ipStr.split("\\.");
                if (ipArray.length == 4) {
                    for (String s : ipArray) {
                        try {
                            int number = Integer.parseInt(s);
                            //4.判断每段数字是否都在0-255之间
                            if (number < 0 || number > 255) {
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}