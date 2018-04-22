package com.ali.festec.generators;

import com.ali.latte.annitations.AppRegisterGenerator;
import com.ali.latte.wechat.template.AppRegisterTemplate;

/**
 * Created by 澄鱼 on 2018/4/23.
 */
@AppRegisterGenerator(
        packageName = "com.ali.festec",
        entryTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
