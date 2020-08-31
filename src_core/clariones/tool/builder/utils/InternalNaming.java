package clariones.tool.builder.utils;

import cla.edg.modelbean.BaseModelBean;
import clariones.tool.builder.Utils;

public class InternalNaming {
    public static String makeQueryName(BaseModelBean queryTgt, String listName){
        return makeQueryName(queryTgt.getModelTypeName(), listName);
    }
    public static String makeQueryName(String queryTgtName, String listName){
        return "query" + Utils.NameAsThis(queryTgtName) + "ListOf" + Utils.NameAsThis(listName);
    }
}
