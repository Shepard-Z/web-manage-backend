package com.pots.common.utils;

import com.pots.common.constant.PropConst;
import com.pots.common.model.ResultType;
import jakarta.servlet.http.HttpServletRequest;

public final class Inspector {
    
    private Inspector() {
    }
    
    public static LangCheckResult languageCheck(HttpServletRequest request) {
        String     langCode = request.getHeader(PropConst.ACCEPT_LANGUAGE);
        ResultType rt;
        if (langCode == null) {
            rt = ResultType.NO_ACCEPT_LANGUAGE;
            langCode = PropConst.DEFAULT_LANGUAGE;
        } else if (!PropConst.ALLOWED_SET.contains(langCode)) {
            rt = ResultType.NO_SUCH_LANG;
            langCode = PropConst.DEFAULT_LANGUAGE;
        } else {
            rt = ResultType.SUCCESS;
        }
        return new LangCheckResult(langCode,rt);
    }
    
}
