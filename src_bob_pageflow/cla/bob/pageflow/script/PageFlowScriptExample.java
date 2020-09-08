package cla.bob.pageflow.script;

import cla.bob.pageflow.spec.PageFlowBobScript;

public class PageFlowScriptExample {
    public PageFlowBobScript getScript() {
        return new PageFlowBobScript()
                .name("page flow")
                .has_config("base_package_name")
                .has_config("project_name")
                .has_config("parent_class_name")
                .has_config("parent_class_package")
                .has_config("bean_name")
                .has_config("change_request")

                .has_spec("request").zh_CN("请求")
                    .in_it()
                        .has_spec("branch").zh_CN("分支")
                        .has_spec("page").zh_CN("页面")
                    .done()
                ;
    }
}
