package clariones.tool.builder;

import java.sql.JDBCType;

public interface CONST {
    String NODE = "node";
    String LINK = "link";

    String NODES = "nodes";
    String LINKS = "links";

    String REQUEST = "request";
    String DISPLAY_MODE = "displayMode";
    String CACHE_TIME_IN_SECOND = "cacheInSecond";
    String CAN_REFRESH = "canRefresh";
    String HAS_FOOT_PRINT = "hasFootPrint";
    String RESULT_FROM = "resultFrom";
    String MULTIPLE = "multiple";

    String FORM = "form";
    String BRANCH_BY_DEFAULT = "by default";

    interface PAGE_TYPE {
        String LIST = "list";
        String DETAIL = "detail";
        String OTHER = "other";
    }

    interface NODE_TYPE {
        String PAGE = "page";
        String START_WAY = "startWay";
        String FORM = "form";
        String SPLIT_POINT = "splitPoint";
    }

    /**
     * 暂不支持form接form
     */
    interface LINK_TYPE {
        /** 普通的请求 没有分支, 结果是一个页面 */
        String REQUEST_RESPONSE = "request-response";
        /** 完整请求的 '请求' 部分, toNode 是一个分支点 (SPLIT_POINT) */
        String REQUEST = "request";
        /** 请求的'分支'部分, 来源是一个分支点, 再前面是 request 或者 form-response-branch */
        String RESPONSE_BRANCH = "response-branch";
        /** 提交form的请求. 完整的request的前半段, toNode 是一个 form */
        String FORM_REQUEST = "form-request";
        /** form提交后的响应页面, 没有分支, toNode 是一个page */
        String FORM_RESPONSE = "form-response";
        /** form 提交后, 有分支的情况. from是一个FORM, to是一个SplitPoint */
        String FORM_RESULT = "form-result";
        /** form提交后,有分支. from 是一个 split point, to是Page */
        String FORM_RESPONSE_BRANCH = "form-response-branch";
        /** 来源是多个的分支 */
        String FORM_START_FROM_BRANCH = "form-start-by-branch";
    }
}
