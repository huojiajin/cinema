package howard.cinema.core.dao.dict.advice;

/**
 *@name: FileUploadStatus
 *@description: 文件上传状态
 *@author: huojiajin
 *@time: 2021/6/1 10:36
**/
public enum FileUploadStatus {
    IS_HAVE(100, "文件已存在"),
    NO_HAVE(101, "文件未上传"),
    ING_HAVE(102, "文件上传未完整");


    private final int value;

    private final String reasonPhrase;


    FileUploadStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
