package clariones.tool.builder;

public class BaseScriptElement {
    protected transient String declaredFileName;
    protected transient String declaredClassName;
    protected transient int declaredLine;

    public void recordLocation(){
        recordLocation(0);
    }
    public void recordLocation(int upLevel){
        StackTraceElement t = new Throwable().getStackTrace()[3+upLevel];
        declaredFileName = t.getFileName();
        declaredClassName = t.getClassName();
        declaredLine = t.getLineNumber();
    }

    public String getDeclaredFileName() {
        return declaredFileName;
    }

    public void setDeclaredFileName(String declaredFileName) {
        this.declaredFileName = declaredFileName;
    }

    public String getDeclaredClassName() {
        return declaredClassName;
    }

    public void setDeclaredClassName(String declaredClassName) {
        this.declaredClassName = declaredClassName;
    }

    public int getDeclaredLine() {
        return declaredLine;
    }

    public void setDeclaredLine(int declaredLine) {
        this.declaredLine = declaredLine;
    }

    public String getDeclaredPosition(){
        return declaredFileName+":"+declaredLine;
    }
}
